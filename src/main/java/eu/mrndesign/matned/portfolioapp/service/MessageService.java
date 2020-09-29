package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.PasswordResetDTO;
import eu.mrndesign.matned.portfolioapp.message.Message;
import eu.mrndesign.matned.portfolioapp.model.PasswordReset;
import eu.mrndesign.matned.portfolioapp.repository.ResetPasswordRepository;
import eu.mrndesign.matned.portfolioapp.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Service
public class MessageService {

    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final String webUrl;
    private final JavaMailSender javaMailSender;
    private final String incomingMailBox;
    private final Integer tokenExpMinutes;
    private final Integer tokenLength;

    public MessageService(String webUrl,
                          JavaMailSender javaMailSender,
                          ResetPasswordRepository resetPasswordRepository,
                          UserRepository userRepository,
                          String incomingMailBox,
                          Integer tokenExpMinutes,
                          Integer tokenLength) {
        this.webUrl = webUrl;
        this.javaMailSender = javaMailSender;
        this.resetPasswordRepository = resetPasswordRepository;
        this.userRepository = userRepository;
        this.incomingMailBox = incomingMailBox;
        this.tokenExpMinutes = tokenExpMinutes;
        this.tokenLength = tokenLength;
    }

    public void send(Message message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(message.getEmail());
        simpleMailMessage.setTo(incomingMailBox);
        simpleMailMessage.setSubject(message.getSubject()+" << from: "+message.getEmail());
        simpleMailMessage.setText(message.getMessage());
        simpleMailMessage.setSentDate(new Date());
        javaMailSender.send(simpleMailMessage);

    }

    public void sendPasswordReset(PasswordResetDTO dto) {
        saveResetPasswordToken(dto);
        SimpleMailMessage simpleMailMessage = generateMailMessage(dto);
        javaMailSender.send(simpleMailMessage);
    }





//    generating message

    private SimpleMailMessage generateMailMessage(PasswordResetDTO dto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(incomingMailBox);
        simpleMailMessage.setTo(dto.getLogin());
        simpleMailMessage.setSubject("Password reset token");
        simpleMailMessage.setText(Objects.requireNonNull(generateMessage(dto)));
        simpleMailMessage.setSentDate(new Date());
        return simpleMailMessage;
    }

    private String generateMessage(PasswordResetDTO dto) {
        PasswordReset passwordReset = saveResetPasswordToken(dto);
        StringBuilder message = new StringBuilder();
        String link = "http://"+webUrl+"/token/"+passwordReset.getToken()+"";
        message.append("Hello").append(System.getProperty("line.separator"))
                .append("Here is the password reset token.").append(System.getProperty("line.separator"))
                .append("Click the link below:").append(System.getProperty("line.separator"))
                .append(link).append(System.getProperty("line.separator"))
                .append("Bye").append(System.getProperty("line.separator"));

        return String.valueOf(message);
    }

    private PasswordReset saveResetPasswordToken(PasswordResetDTO dto) {
        PasswordReset passwordReset = new PasswordReset(
                LocalDateTime.now().plusMinutes(tokenExpMinutes),
                generateToken(),
                userRepository.findByLogin(dto.getLogin()).orElseThrow(()->new RuntimeException("No such user"))
        );
        resetPasswordRepository.save(passwordReset);
        return passwordReset;
    }

    private String generateToken(){
        return new RandomString(tokenLength).nextString();
    }
}
