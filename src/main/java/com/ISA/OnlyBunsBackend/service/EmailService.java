package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /*
     * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
     */
    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;

    /*
     * Anotacija za oznacavanje asinhronog zadatka
     * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
     */
    public void sendNotificaitionSync(UserRegistration user) throws MailException, InterruptedException {

        User registrated = userRepository.findByUsername(user.getUsername());
        System.out.println("Sync metoda se izvrsava u istom Threadu koji je i prihvatio zahtev. Thread id: " + Thread.currentThread().getId());
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        String activationLink = "http://localhost:4200/activation?id=" + registrated.getId();
        mail.setSubject("Activate Your OnlyBuns Account");
        mail.setText("Hello " + user.getFirstName() + ",\n\nThank you for signing up with us. Please confirm your email address to activate your account and get started.\n\nSimply click the button below:\n" + activationLink +
        "\n\nIf you didn’t create this account, you can safely ignore this email.\n\n\n Best regards,\n" + "The OnlyBuns Team");
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

}

