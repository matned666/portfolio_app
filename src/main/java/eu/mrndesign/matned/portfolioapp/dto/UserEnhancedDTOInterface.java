package eu.mrndesign.matned.portfolioapp.dto;

public interface UserEnhancedDTOInterface<E> extends UserDTOInterface<E>{


    String getLogin();
    String getPassword();
    String getPasswordConfirm();

}
