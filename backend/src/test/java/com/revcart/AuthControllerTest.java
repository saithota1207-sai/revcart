package com.revcart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revcart.dto.LoginRequest;
import com.revcart.dto.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testUserRegistration() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("Test User");
        signupRequest.setEmail("testuser@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setPhone("9876543210");
        signupRequest.setAddress("Test Address");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    public void testAdminLogin() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("saithota1207@gmail.com");
        loginRequest.setPassword("admin123");

        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}