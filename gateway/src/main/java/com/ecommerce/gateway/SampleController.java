package com.ecommerce.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    @GetMapping
    //@PreAuthorize("hasRole(client_user)")
    public String hello() {
        return "Hello from Spring boot & Keycloak";
    }

    @GetMapping("/hello-2")
    //@PreAuthorize("hasRole(client_admin)")
    public String hello2() {
        return "Hello from Spring boot & Keycloak - ADMIN";
    }
}
