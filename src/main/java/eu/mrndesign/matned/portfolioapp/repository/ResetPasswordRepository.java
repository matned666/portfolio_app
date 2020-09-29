package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<PasswordReset, Long> {

    @Query("select pr from PasswordReset pr where pr.token = ?1")
    Optional<PasswordReset> findByToken(String token);
}
