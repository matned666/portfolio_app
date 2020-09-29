package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.UserDTO;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Address {

    private String street;
    private String zipCode;
    private String city;
    @Enumerated(EnumType.STRING)
    private Countries country;

    public Address() {
    }

    public static Address apply(UserDTO registrationDTO){
        Address address = new Address();
        address.setCity(registrationDTO.getCity());
        address.setCountry(Countries.fromSymbol(registrationDTO.getCountry()));
        address.setStreet(registrationDTO.getStreet());
        address.setZipCode(registrationDTO.getZipCode());
        return address;
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

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }
}
