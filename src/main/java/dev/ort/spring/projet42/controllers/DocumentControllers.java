package dev.ort.spring.projet42.controllers;

import dev.ort.spring.projet42.entities.Document;
import dev.ort.spring.projet42.entities.Evenement;
import dev.ort.spring.projet42.entities.Utilisateur;
import dev.ort.spring.projet42.exceptions.FileStorageException;
import dev.ort.spring.projet42.exceptions.MyFileNotFoundException;
import dev.ort.spring.projet42.exceptions.ResourceNotFoundException;
import dev.ort.spring.projet42.exceptions.UtilisateurNotFoundException;
import dev.ort.spring.projet42.repositories.DocumentRepository;
import dev.ort.spring.projet42.repositories.EvenementRepository;
import dev.ort.spring.projet42.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
public class DocumentControllers {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/uploadFile")
    public Document uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) throws FileStorageException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Document document = new Document();
            document.setNom(fileName);
            document.setFileType(file.getContentType());
            document.setData(file.getBytes());


            Utilisateur utilisateur = utilisateurRepository.findById(authentication.getName()).orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur not found with id " + authentication.getName()));

            document.setUtilisateur(utilisateur);


            documentRepository.save(document);

           String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                   .path("/downloadFile/")
                   .path(document.getId())
                   .toUriString();

            return document;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        } catch (UtilisateurNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/uploadMultipleFiles")
    public List<Document> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, Authentication authentication) {
        return Arrays.asList(files)
                .stream()
                .map(file -> {
                    try {
                        return uploadFile(file, authentication);
                    } catch (FileStorageException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/downloadFile/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "id") String id) throws MyFileNotFoundException {
        // Load file from database
        Document document = documentRepository.findById(id).orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getNom() + "\"")
                .body(new ByteArrayResource(document.getData()));
    }

}
