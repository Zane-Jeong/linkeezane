package com.zane.linkee_platform.mail.controller;

import com.zane.linkee_platform.mail.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send") // 이메일 발송
    public ResponseEntity<String> sendVerificationEmail(@RequestBody Map<String, String> payload, HttpSession session) {
        String email = payload.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("이메일을 입력해주세요.");
        }

        Random random = new Random();
        int verificationCode = 100000 + random.nextInt(900000); // 6자리 인증번호 생성

        try {
            String subject = "Linkee 회원가입 인증번호입니다.";
            String text = "인증번호는 " + verificationCode + " 입니다.";
            mailService.sendMail(email, subject, text);

            session.setAttribute("verificationCode", verificationCode);
            session.setAttribute("emailForVerification", email);

            return ResponseEntity.ok("인증번호가 발송되었습니다.");
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().body("이메일 발송에 실패했습니다.");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> payload, HttpSession session) {
        String code = payload.get("code");
        Integer sessionCode = (Integer) session.getAttribute("verificationCode");
        String emailForVerification = (String) session.getAttribute("emailForVerification");

        if (sessionCode == null || emailForVerification == null) {
            return ResponseEntity.badRequest().body("인증번호를 먼저 발송해주세요.");
        }
        if (sessionCode.toString().equals(code)) {
            session.setAttribute("emailVerified", true);
            session.removeAttribute("verificationCode"); // 인증 완료 후 세션에서 코드 제거
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");

        } else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않습니다.");
        }
    }
}
