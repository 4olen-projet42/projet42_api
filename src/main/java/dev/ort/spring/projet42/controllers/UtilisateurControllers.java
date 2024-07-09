package dev.ort.spring.projet42.controllers;


import dev.ort.spring.projet42.dto.UtilisateurDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurControllers {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurControllers.class);

    @Operation(summary = "Récupération des informations de l'utilisateur connecté")
    @GetMapping("/me")
    public UtilisateurDTO getUtilisateurInfos(@AuthenticationPrincipal Jwt jwt) {

        UtilisateurDTO user = new UtilisateurDTO();
        user.setEmail(jwt.getClaim("email"));
        user.setFirstName(jwt.getClaim("given_name"));
        user.setLastName(jwt.getClaim("family_name"));
        user.setPhoto_profil(jwt.getClaim("photo_profil"));

        return user;
    }

}
