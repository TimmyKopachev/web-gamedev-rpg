package org.web.gamedev.rpg.auth.web;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @GetMapping("/")
  public String homePage() {
    return "home";
  }

  @GetMapping("/authenticated")
  public String authenticatedPage() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return String.format(
        "secured part of web service: %s - %s",
        authentication.getName(), authentication.getAuthorities());
  }

  @GetMapping("/admin")
  public String adminPage(Principal principal) {
    Authentication a = SecurityContextHolder.getContext().getAuthentication();
    return "admin page: " + a.getName() + a.getAuthorities();
  }
}
