package com.ISA.OnlyBunsBackend.controller;


import com.ISA.OnlyBunsBackend.dto.JwtAuthenticationRequest;
import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.dto.UserTokenState;
import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
import com.ISA.OnlyBunsBackend.exception.ResourceConflictException;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.security.auth.LoginAttemptService;
import com.ISA.OnlyBunsBackend.service.EmailService;
import com.ISA.OnlyBunsBackend.service.LastLoginService;
import com.ISA.OnlyBunsBackend.service.UserService;
import com.ISA.OnlyBunsBackend.util.BloomFilter;
import com.ISA.OnlyBunsBackend.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private LastLoginService lastLoginService;

    @Autowired
    private BloomFilter bloomFilter;

    @Autowired
    private LoginAttemptService loginAttemptService;



    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,  // telo zahteva
            HttpServletRequest request) { // HttpServletRequest može ovako, Spring će sam ubaciti

        // Uzmi IP adresu klijenta iz requesta
        String ip = request.getRemoteAddr();

        // Provera da li je IP blokiran zbog previše neuspelih pokušaja
        if (loginAttemptService.isBlocked(ip)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        try {
            // Autentifikacija korisnika
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Provera da li je korisnik aktiviran
            boolean isActive = userService.findByUsername(authenticationRequest.getUsername()).isActivated();

            if (isActive) {
                User user = (User) authentication.getPrincipal();

                String jwt = tokenUtils.generateToken(user.getUsername());
                int expiresIn = tokenUtils.getExpiredIn();

                // Ažuriranje poslednjeg logina za običnog korisnika
                if(user.getRole().getName().equals("ROLE_USER")) {
                    lastLoginService.updateLastLoginInfo(user.getId());
                }

                // Uspešno logovanje - resetuj broj neuspelih pokušaja za IP
                loginAttemptService.loginSucceeded(ip);

                return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
            } else {
                // Korisnik nije aktiviran, nemoj računati kao neuspeh logovanja
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            // Neuspešno logovanje - evidentiraj neuspeh po IP adresi
            loginAttemptService.loginFailed(ip);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserRegistration userRequest, UriComponentsBuilder ucBuilder) {
        // Provera da li BloomFilter misli da korisničko ime već postoji
        if (bloomFilter.has(userRequest.getUsername())) {
            // Ako možda postoji, proveri u bazi
            User existUser = this.userService.findByUsername(userRequest.getUsername());
            if (existUser != null) {
                throw new ResourceConflictException(userRequest.getId(), "Username already exists");
            }
        }

        // Registracija korisnika
        User user = this.userService.save(userRequest);

        // Dodavanje korisničkog imena u BloomFilter nakon uspešne registracije
        bloomFilter.add(user.getUsername());
        try {
            this.emailService.sendNotificaitionSync(userRequest);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Ako želite da zadržite informaciju o prekidu
            // Ili možete logovati grešku ili obraditi je na drugi način
            System.err.println("Failed to send notification: " + e.getMessage());
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = bloomFilter.has(username);
        return ResponseEntity.ok(exists);
    }


    @PutMapping("/activate/{id}")
    public ResponseEntity<Void> activateUser(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setActivated(true);
        userService.updateUser(user); // Ažurirajte korisnika u bazi
        return ResponseEntity.ok().build(); // Vratite OK status bez tela odgovora
    }



}
