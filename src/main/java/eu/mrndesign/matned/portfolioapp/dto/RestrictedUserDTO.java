package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.User;
import eu.mrndesign.matned.portfolioapp.validation.DateMatchesPattern;
import eu.mrndesign.matned.portfolioapp.validation.NoValidation;

import javax.validation.constraints.Pattern;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;

public class RestrictedUserDTO implements UserDTOInterface<RestrictedUserDTO> {

    private Long id;

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

    public RestrictedUserDTO() {
    }

    private RestrictedUserDTO(RTDOBuilder builder){
        id = builder.id;
        firstName = builder.firstName;
        lastName = builder.lastName;
        street = builder.street;
        zipCode = builder.zipCode;
        city = builder.city;
        country = builder.country;
        birthDate = builder.birthDate;
        phoneNumber = builder.phoneNumber;
        preferEmails = builder.preferEmails;
    }

    public static RestrictedUserDTO apply(User user) {
        RestrictedUserDTO restrictedUserDTO = new RTDOBuilder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .street(user.getAddress().getStreet())
                .zipCode(user.getAddress().getZipCode())
                .city(user.getAddress().getCity())
                .country(user.getAddress().getCountry().name())
                .phoneNumber(user.getPhoneNumber())
                .preferEmails(user.isPreferEmails())
                .build();
        if (user.getBirthDate() != null) restrictedUserDTO.setBirthDate(user.getBirthDate().format(DATE_TIME_FORMATTER_ONLY_DATE));
        return restrictedUserDTO;
    }

    public static RestrictedUserDTO apply(UserDTO user){
        return new RTDOBuilder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .street(user.getStreet())
                .zipCode(user.getZipCode())
                .city(user.getCity())
                .country(user.getCountry())
                .phoneNumber(user.getPhoneNumber())
                .preferEmails(user.isPreferEmails())
                .birthDate(user.getBirthDate())
                .build();
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
        private String phoneNumber;
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

        public RTDOBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public RTDOBuilder preferEmails(boolean preferEmails) {
            this.preferEmails = preferEmails;
            return this;
        }

        public RestrictedUserDTO build() {
            return new RestrictedUserDTO(this);
        }
    }

}
