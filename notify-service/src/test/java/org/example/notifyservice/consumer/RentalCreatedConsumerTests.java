package org.example.notifyservice.consumer;

import org.example.notifyservice.service.EmailService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RentalCreatedConsumerTests {

    @Test
    void delegatesMessageToEmailService() {
        EmailService emailService = mock(EmailService.class);
        RentalCreatedConsumer consumer = new RentalCreatedConsumer(emailService);

        consumer.consume("customer@example.com");

        verify(emailService).sendRentalCreatedEmail("customer@example.com");
    }
}
