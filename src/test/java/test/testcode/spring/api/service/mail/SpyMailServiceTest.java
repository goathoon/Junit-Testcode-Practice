package test.testcode.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import test.testcode.spring.client.mail.MailSendClient;
import test.testcode.spring.domain.history.mail.MailSendHistory;
import test.testcode.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 이거 안붙여서
 * org.mockito.exceptions.misusing.NullInsteadOfMockException:
 * Argument passed to when() is null!
 * 이 오류 뜸!
 */
@ExtendWith(MockitoExtension.class)
public class SpyMailServiceTest {

    /**
     * spy는 객체의 특정 메서드만 stubbing을 하고싶을때 사용 (다른 메서드는 실제 클래스 메서드와 동일하게 작동되는 것을 기대할 때)
     */
    @Spy
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    // mailService의 생성자를 보고 Mock객체를 다 주입시켜줌
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);

        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);
//        mocking한 대상의 함수들은 다기본값을 반한하게 되어있음 객체면 null, 리스트면 empty list라든지. 그래서 아래 코드 안넣어도 무방함
//        when(mailSendHistoryRepository.save(any(MailSendHistory.class)))
//                .thenReturn()

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail2() {
        // given
        // spy객체는 when절로 검증이 불가능함.. 실제 객체를 대상으로 하기 때문이다.
//        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);
        doReturn(true)
                .when(mailSendClient)
                .sendEmail(anyString(),anyString(),anyString(),anyString());

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}