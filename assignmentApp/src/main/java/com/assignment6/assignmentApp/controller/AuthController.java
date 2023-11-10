package com.assignment6.assignmentApp.controller;

import com.assignment6.assignmentApp.logs.AuthControllerLogger;
import com.assignment6.assignmentApp.service.AuthService;
import com.assignment6.assignmentApp.service.impl.AuthServiceImpl;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;


//    @Autowired
//    public AuthController(AuthService authService, AuthControllerLogger authControllerLogger) {
//        this.restTemplate = new RestTemplate();
//        this.authService = authService;
//        this.authControllerLogger=new AuthControllerLogger();
//    }


    // This method will display the login form.
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Create an HTML template for the login form.
    }


    @GetMapping("/test")
    public void test(){
        System.out.println("Inside Test");
    }
    // This method will handle the form submission for authentication.
    @PostMapping("/login")
    public String authenticateUser(
            @RequestParam String login_id,
            @RequestParam String password) {
    System.out.println("Controller");
        // Check if authentication is successful and obtain the Bearer token
        if (authService.loginAndObtainToken(login_id, password)) {
            return "redirect:/customer/list"; // Redirect to the customer list page.
        } else {
            // Authentication failed. You can handle errors accordingly.
            return "auth/login"; // Redirect back to the login form with an error message.
        }
    }
}
