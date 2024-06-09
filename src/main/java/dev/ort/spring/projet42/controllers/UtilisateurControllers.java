package dev.ort.spring.projet42.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurControllers {

    @GetMapping("/connected")
    public String getUtilisateurName(Authentication authentication) {

        return authentication.getName();
    }
}
