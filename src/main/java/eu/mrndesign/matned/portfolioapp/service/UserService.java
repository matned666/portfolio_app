package eu.mrndesign.matned.portfolioapp.service;


import eu.mrndesign.matned.portfolioapp.dto.RestrictedUserDTO;
import eu.mrndesign.matned.portfolioapp.dto.UserDTO;
import eu.mrndesign.matned.portfolioapp.model.Address;
import eu.mrndesign.matned.portfolioapp.model.Countries;
import eu.mrndesign.matned.portfolioapp.model.User;
import eu.mrndesign.matned.portfolioapp.model.UserRole;
import eu.mrndesign.matned.portfolioapp.repository.UserRepository;
import eu.mrndesign.matned.portfolioapp.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;


@Service
public class UserService {

    private final UserRepository repo;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;
    private String passwordEncoded;

    public UserService(UserRepository repo, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserDTO userDTO){
        passwordEncoded = passwordEncoder.encode(userDTO.getPassword());
        User userToSave = getUser(userDTO);
        return repo.save(userToSave);
    }

    private User getUser(UserDTO userDTO) {
        User userToSave = User.apply(userDTO, passwordEncoded);
        String roleName = "ROLE_" + UserRole.Role.USER.name();
        userToSave.addRole(userRoleRepository.findByRoleName(roleName));
        return userToSave;
    }

    public UserDTO findByLogin(String login) {
        return UserDTO.apply(repo.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User with login: "+login+" cannot be found.")));
    }

    public RestrictedUserDTO findByLoginRestricted(String login) {
        return RestrictedUserDTO.apply(repo.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User with login: "+login+" cannot be found.")));
    }

    public List<User> findAll() {return repo.findAll();}

    public boolean userWithEmailExists(String login) {
        return repo.existsByLogin(login);
    }

    public UserDTO findById(Long id) {
        return UserDTO.apply(repo.findById(id).orElseThrow(() -> new RuntimeException("User with id: "+id+" has not been found")));
    }

    public RestrictedUserDTO findByIdRestricted(Long id) {
        return RestrictedUserDTO.apply(repo.findById(id).orElseThrow(() -> new RuntimeException("User with id: "+id+" has not been found")));
    }

    public void update(Long id, RestrictedUserDTO restrictedUserDTO) {
        User user = repo.findById(id).orElseThrow(() -> new RuntimeException("User of "+id+" id has not been found"));
        updateUser(restrictedUserDTO, user);
    }

    public void updateByLogin(String login, RestrictedUserDTO restrictedUserDTO) {
        User user = repo.findByLogin(login).orElseThrow(() -> new RuntimeException("User with login: "+login+" has not been found"));
        updateUser(restrictedUserDTO, user);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }




    private void updateUser(RestrictedUserDTO restrictedUserDTO, User user) {
        if (user.getAddress() == null) {
            user.setAddress(new Address());
            user.getAddress().setCountry(Countries.POLAND);
        }
        user.setFirstName(restrictedUserDTO.getFirstName());
        user.setLastName(restrictedUserDTO.getLastName());
        if (restrictedUserDTO.getBirthDate() != null)
            if (!restrictedUserDTO.getBirthDate().trim().equals(""))
                user.setBirthDate(LocalDate.parse(restrictedUserDTO.getBirthDate(), DATE_TIME_FORMATTER_ONLY_DATE));
        user.setPhoneNumber(restrictedUserDTO.getPhoneNumber());
        user.setPreferEmails(restrictedUserDTO.isPreferEmails());
        user.getAddress().setStreet(restrictedUserDTO.getStreet());
        user.getAddress().setZipCode(restrictedUserDTO.getZipCode());
        user.getAddress().setCity(restrictedUserDTO.getCity());
        if (restrictedUserDTO.getCountry() != null)
            if (!restrictedUserDTO.getCountry().trim().equals(""))
                user.getAddress().setCountry(Countries.fromSymbol(restrictedUserDTO.getCountry()));
            user.setUpdateDate(LocalDateTime.now());
        repo.save(user);
    }


}
