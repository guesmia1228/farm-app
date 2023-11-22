package com.farmdigital.nerddevs.controller;

import com.farmdigital.nerddevs.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/farm_digital/super")
public class EmailController {
    private final JavaMailSenderImpl mailSender;

    @GetMapping("/sendEmail")
    public ResponseEntity<?> validateEmail(@RequestParam("email") String  email){

    String Subject = "Verify your Agri-connect account";
    String  content= "<h4>Hello , </h4>" +
            "<p> thanks for registering your self with agri-connect please verify your email address by clicking the button bellow </p>" +
            "<button>verify Account</button>";
    try{
        EmailService emailService = new EmailService(mailSender);
         emailService.SendEmail(email,Subject,content);
         return  ResponseEntity.status(200).body("email was sent successfully");
    } catch (MessagingException| UnsupportedEncodingException ex){
        System.out.println("failed to send the email "+ ex.getMessage());
        return  ResponseEntity.status(500).body(ex);
    }


}

}
