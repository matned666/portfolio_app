package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.PasswordDTO;
import eu.mrndesign.matned.portfolioapp.model.PasswordReset;
import eu.mrndesign.matned.portfolioapp.model.User;
import eu.mrndesign.matned.portfolioapp.repository.ResetPasswordRepository;
import eu.mrndesign.matned.portfolioapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordService {

    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final PasswordEncoder passwordEncoder;


    public PasswordService(UserRepository userRepository,
                           ResetPasswordRepository resetPasswordRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.resetPasswordRepository = resetPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordDTO findByToken(String token) {
        PasswordReset passwordReset = resetPasswordRepository.findByToken(token).orElse(null);
        if (passwordReset != null) {
            if (passwordReset.getExpDate() != null) {
                if (passwordReset.getExpDate().isAfter(LocalDateTime.now()) && !passwordReset.isUsed())
                    return PasswordDTO.apply(passwordReset);
            }
        }
        throw new RuntimeException("Wrong token");
    }

    public void setPassword(PasswordDTO passwordDTO, String token) {
        PasswordReset passwordReset = resetPasswordRepository.findByToken(token).orElseThrow(() -> new RuntimeException("No token found"));
        User user = passwordReset.getUser();
        String passwordEncoded = passwordEncoder.encode(passwordDTO.getPassword());
        user.setPassword(passwordEncoded);
        userRepository.save(user);
        passwordReset.setUsed(true);
        resetPasswordRepository.save(passwordReset);
    }

}
