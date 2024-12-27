package com.ISA.OnlyBunsBackend.scheduled;

import com.ISA.OnlyBunsBackend.model.LastLogin;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.PostRepository;
import com.ISA.OnlyBunsBackend.service.LastLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class SevenDaysStatistic {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Autowired
    LastLoginService lastLoginService;

    @Autowired
    PostRepository postRepository;

    @Scheduled(cron = "0 0 8 * * ?")
    public void sevenDaysNotLoggedInMail() throws InterruptedException {

    List<LastLogin> lastLogins = lastLoginService.getAll();
    List<User> users = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();
    long hoursBetween;

    for(LastLogin lastLogin : lastLogins){
        hoursBetween = ChronoUnit.HOURS.between(lastLogin.getLastLoginTime(), now);

        if(hoursBetween >= (7*24) && hoursBetween < (8*24)){
            users.add(lastLogin.getUser());
         }
      }
    for(User user : users){
        sendStatisticNotification(user);

      }
    }

    private void sendStatisticNotification(User user) throws MailException, InterruptedException {
        LastLogin userLastLogin = lastLoginService.findByUserId(user.getId());

        System.out.println("Sync metoda se izvrsava u istom Threadu koji je i prihvatio zahtev. Thread id: " + Thread.currentThread().getId());
        //Simulacija duze aktivnosti da bi se uocila razlika
        Thread.sleep(1000);
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        double followersDiff = user.getFollowersCount() - userLastLogin.getTotalFollowers();
        double likesDiff = postRepository.getUserPostsLikesCount(user.getId()) - userLastLogin.getTotalLikes();
        double commentsDiff = postRepository.getUserPostsCommentsCount(user.getId()) - userLastLogin.getTotalComments();

        mail.setText("Pozdrav " + user.getFirstName() + ",\n \n niste se prijavili na Vaš OnlyBuns nalog već 7 dana. \nNa Vašem profilu se promenio broj lajkova za " + likesDiff + ", broj komentara za " + commentsDiff +", i broj followera za " + followersDiff + "."
                      + " \n \n Nadamo se da ćete se ulogovati u skorije vreme. \n Vaš OnlyBuns!");
        javaMailSender.send(mail);

        System.out.println("Email poslat!");

    }


}
