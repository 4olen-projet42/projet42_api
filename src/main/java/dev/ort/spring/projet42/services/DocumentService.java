package dev.ort.spring.projet42.services;

import dev.ort.spring.projet42.entities.Document;
import dev.ort.spring.projet42.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class DocumentService {

    @Value("${file.upload-dir}")
    private String basePath;

    @Autowired
    private DocumentRepository documentRepository;

    public String uploadFile(MultipartFile multipartFile, @AuthenticationPrincipal Jwt jwt) throws IOException {


        Path userDir = Path.of(basePath +"/"+ jwt.getClaim("sub").toString());

        // création du dossier de l'utilisateur s'il n'existe pas
        try {
            if (!Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "FAILED";
        }


        File dir = new File(basePath +"/"+ jwt.getClaim("sub").toString() +"/"+ multipartFile.getOriginalFilename());

        if (dir.exists()) {
            return "EXIST";
        }

        Path path = Path.of(basePath +"/"+ jwt.getClaim("sub").toString() +"/"+  multipartFile.getOriginalFilename());

        try {
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Document document = new Document();
            document.setFileName(multipartFile.getOriginalFilename());
            document.setIdUtilisateur(jwt.getClaim("sub").toString());

            documentRepository.save(document);

            return "CREATED";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "FAILED";
    }

    public Resource downloadFile(String fileName, @AuthenticationPrincipal Jwt jwt) {
        File dir = new File(basePath +"/"+ jwt.getClaim("sub").toString() +"/"+ fileName);
        try {
            if (dir.exists()) {
                return new UrlResource(dir.toURI());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public List<Document> getDocumentsByUtilisateur(@AuthenticationPrincipal Jwt jwt) {
        return documentRepository.findByIdUtilisateur(jwt.getClaim("sub").toString());
    }

    public void deleteDocument(String id, @AuthenticationPrincipal Jwt jwt) {

        Document document = documentRepository.findById(id).get();
        String fileName = document.getFileName();
        documentRepository.deleteById(id);

        File dir = new File(basePath+"/"+ jwt.getClaim("sub").toString() +"/"+ fileName);

        if (dir.exists()) {
            dir.delete();
        }
    }


}
