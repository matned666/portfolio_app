package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.Address;
import eu.mrndesign.matned.portfolioapp.model.Countries;
import eu.mrndesign.matned.portfolioapp.model.User;
import eu.mrndesign.matned.portfolioapp.validation.*;
import eu.mrndesign.matned.portfolioapp.validation.PasswordMatches;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;
import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@PasswordMatches
public class UserDTO implements UserEnhancedDTOInterface<UserDTO>{

    private Long id;

    @NotNull(message = "The login cannot be empty")
    @Size(min = 5, message = "The login must be at least {min} signs long")
    @Pattern(
            regexp = ".{1,}@.{1,}[.].{2,3}",
            message = "It should be a valid email address"
    )
    @UniqueEmail
    private String login;

    @NotNull(message = "The password field cannot be empty")
    @Size(min = 3, message = "The password must be at least {min} signs long")
    private String password;

    @NotNull(message = "This field cannot be empty")
    @Size(min = 3, message = "The password must be at least {min} signs long")
    private String passwordConfirm;

    @Pattern(regexp = "[A-z]{0,}", message = "Name should contain only letters")
    private String firstName;

    @Pattern(regexp = "[A-z]{0,}", message = "Surname should contain only letters")
    private String lastName;

    @NoValidation
    private String street;

    @NoValidation
    private String zipCode;

    @NoValidation
    private String city;

    @NoValidation
    private String country;

    @DateMatchesPattern
    private String birthDate;

    @Pattern(regexp = "([+][0-9]{9,})|([0-9]{7,})|", message = "Proper format - only numbers or '+' and numbers")
    private String phoneNumber;

    @NoValidation
    private boolean preferEmails;

    private String creationDateTime;
    private String updateDateTime;

    public UserDTO() {
    }

    private UserDTO(RTDOBuilder builder){
        id = builder.id;
        firstName = builder.firstName;
        lastName = builder.lastName;
        street = builder.street;
        zipCode = builder.zipCode;
        city = builder.city;
        country = builder.country;
        birthDate = builder.birthDate;
        login = builder.login;
        password = builder.password;
        passwordConfirm = builder.passwordConfirm;
        phoneNumber = builder.phoneNumber;
        preferEmails = builder.preferEmails;

    }

    public static UserDTO apply(User user) {
        if (user.getAddress() == null) {
            user.setAddress(new Address());
            user.getAddress().setCountry(Countries.POLAND);
        }
        UserDTO registrationDTO = new RTDOBuilder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .street(user.getAddress().getStreet())
                .zipCode(user.getAddress().getZipCode())
                .city(user.getAddress().getCity())
                .country(user.getAddress().getCountry().name())
                .preferEmails(user.isPreferEmails())
                .build();
        if (user.getCreationDate() != null) registrationDTO.setCreationDateTime(user.getCreationDate().format(DATE_TIME_FORMATTER_TASK));
        if (user.getUpdateDate() != null) registrationDTO.setUpdateDateTime(user.getUpdateDate().format(DATE_TIME_FORMATTER_TASK));
        if (user.getBirthDate() != null) registrationDTO.setBirthDate(user.getBirthDate().format(DATE_TIME_FORMATTER_ONLY_DATE));
        return registrationDTO;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPreferEmails() {
        return preferEmails;
    }

    public void setPreferEmails(boolean preferEmails) {
        this.preferEmails = preferEmails;
    }

    public static class RTDOBuilder {


        private Long id;
        private String firstName;
        private String lastName;
        private String street;
        private String zipCode;
        private String city;
        private String country;
        private String birthDate;
        private String login;
        private String password;
        private String passwordConfirm;
        private String phoneNumber;
        private String updateDateTime;
        private String creationDateTime;
        private boolean preferEmails;

        public RTDOBuilder() {
        }

        public RTDOBuilder id(Long id){
            this.id = id;
            return this;
        }

        public RTDOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public RTDOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public RTDOBuilder creationDateTime(String creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        public RTDOBuilder updateDateTime(String updateDateTime) {
            this.updateDateTime = updateDateTime;
            return this;
        }

        public RTDOBuilder street(String street) {
            this.street = street;
            return this;
        }

        public RTDOBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public RTDOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public RTDOBuilder country(String country) {
            this.country = country;
            return this;
        }

        public RTDOBuilder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public RTDOBuilder login(String login) {
            this.login = login;
            return this;
        }

        public RTDOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RTDOBuilder passwordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
            return this;
        }

        public RTDOBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public RTDOBuilder preferEmails(boolean preferEmails) {
            this.preferEmails = preferEmails;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }

}
