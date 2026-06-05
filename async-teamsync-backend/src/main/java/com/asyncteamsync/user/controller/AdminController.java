package com.asyncteamsync.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/api/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {

        return "Welcome Admin";
    }

    @GetMapping("/api/manager")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String manager() {

        return "Manager Access";
    }

    @GetMapping("/api/employee")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','EMPLOYEE')"
    )
    public String employee() {

        return "Employee Access";
    }
}