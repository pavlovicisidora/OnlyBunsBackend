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
        //Simulacija duze aktivnosti da bi se uocila razlika
        Thread.sleep(1000);
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        String activationLink = "http://localhost:4200/activation?id=" + registrated.getId();
        mail.setText("Pozdrav " + user.getFirstName() + ",\n\nhvala što pratiš OnlyBuns.\n\nKlikni na sledeći link za aktivaciju naloga:\n" + activationLink);
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

}

