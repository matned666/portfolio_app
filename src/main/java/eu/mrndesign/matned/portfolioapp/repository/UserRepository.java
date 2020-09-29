package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select case when count(u)> 0 then true else false end from User u where lower(u.login) like lower(?1)")
    boolean existsByLogin(String login);

    @Query("select u from User u where lower(u.login) = lower(?1)")
    Optional<User> findByLogin(String login);

//    @Query("delete from User u where u.id = ?1")
    void deleteById(Long id);

}
