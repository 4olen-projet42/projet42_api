package dev.ort.spring.projet42.controllers;


import dev.ort.spring.projet42.dto.UtilisateurDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurControllers {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurControllers.class);


    @Value("${keycloak.auth-server-url}")
    private String keycloakAuthServerUrl;

    @Value("${keycloak.realm}")
    private String realm;


    @GetMapping("/connected")
    public Object getUtilisateurName(Authentication authentication) {

        return authentication.getName();
    }


    @PutMapping("/edit")
    public ResponseEntity<String> updateUser(
            @RequestBody Map<String, Object> updates,
            @AuthenticationPrincipal Jwt jwt
    ) {
        try {
            String url = keycloakAuthServerUrl + "/admin/realms/" + realm + "/users/" + jwt.getClaim("sub");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(jwt.getTokenValue());

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(updates, headers);

            ResponseEntity<Void> response = new RestTemplate().exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur dans Keycloak");
            }

            return ResponseEntity.ok("Utilisateur mis à jour avec succès !");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
       }
    }

    @GetMapping("/infos")
    public UtilisateurDTO getUtilisateurInfos(@AuthenticationPrincipal Jwt jwt) {

        UtilisateurDTO user = new UtilisateurDTO();
        user.setEmail(jwt.getClaim("email"));
        user.setFirstName(jwt.getClaim("given_name"));
        user.setLastName(jwt.getClaim("family_name"));
        user.setPhoto_profil(jwt.getClaim("photo_profil"));

        return user;
    }

}
