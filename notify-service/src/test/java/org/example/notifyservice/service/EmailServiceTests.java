package org.example.notifyservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EmailServiceTests {

    private final JavaMailSender mailSender = mock(JavaMailSender.class);
    private final EmailService emailService = new EmailService(mailSender, "shop@example.com");

    @Test
    void sendsRentalCreatedEmailToRecipient() {
        emailService.sendRentalCreatedEmail(" customer@example.com ");

        verify(mailSender).send(argThat((SimpleMailMessage message) ->
                "shop@example.com".equals(message.getFrom())
                        && message.getTo() != null
                        && message.getTo().length == 1
                        && "customer@example.com".equals(message.getTo()[0])
                        && "Đơn hàng đã được tạo thành công".equals(message.getSubject())));
    }

    @Test
    void rejectsBlankRecipient() {
        assertThrows(IllegalArgumentException.class,
                () -> emailService.sendRentalCreatedEmail(" "));
    }
}
