package test.testcode.spring.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.testcode.spring.client.mail.MailSendClient;
import test.testcode.spring.domain.history.mail.MailSendHistory;
import test.testcode.spring.domain.history.mail.MailSendHistoryRepository;

@RequiredArgsConstructor
@Service
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(toEmail)
                    .content(content)
                    .subject(subject)
                    .build()
            );
            mailSendClient.a();
            mailSendClient.b();
            mailSendClient.c();

            return true;
        }
        return false;
    }
}
