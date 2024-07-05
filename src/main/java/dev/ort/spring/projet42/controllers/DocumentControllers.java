package dev.ort.spring.projet42.controllers;

import dev.ort.spring.projet42.entities.Document;
import dev.ort.spring.projet42.services.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentControllers {

    @Autowired
    private DocumentService documentService;

    @Operation(summary = "Sauvegarde d'un document")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file, @AuthenticationPrincipal Jwt jwt) throws IOException {
        String status = documentService.uploadFile(file, jwt);
        return "CREATED".equals(status) ? new ResponseEntity<>(HttpStatus.CREATED) : ("EXIST".equals(status) ? new ResponseEntity<>(HttpStatus.NOT_MODIFIED) : new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }


    @Operation(summary = "Recherche de documents par utilisateur")
    @ApiResponse(responseCode = "404", description = "Aucun document trouvé")
    @GetMapping
    public List<Document> getDocumentsByIdUtilisateur(@AuthenticationPrincipal Jwt jwt) {
        return documentService.getDocumentsByUtilisateur(jwt);
    }

    @Operation(summary = "Téléchargement d'un document à partir de son nom")
    @GetMapping(value = "/download/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadFile(@PathVariable(value = "name") String fileName, @AuthenticationPrincipal Jwt jwt) {
        Resource file = documentService.downloadFile(fileName, jwt);
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
        }
    }

    @Operation(summary = "Suppression d'un document à partir de son identifiant")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "id") String id, @AuthenticationPrincipal Jwt jwt) {
        documentService.deleteDocument(id, jwt);
    }

}
