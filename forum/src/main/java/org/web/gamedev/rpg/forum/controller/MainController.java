package org.web.gamedev.rpg.forum.controller;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage(){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return "secured part of web service: " + a.getName() + a.getAuthorities();
    }

    @GetMapping("/admin")
    public String adminPage(Principal principal){
        //principal.getName()
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return "admin page: " + a.getName() + a.getAuthorities();
    }





}
