package eu.mrndesign.matned.portfolioapp.cookie;

import com.vaadin.flow.component.cookieconsent.CookieConsent;
import com.vaadin.flow.component.html.Div;
import org.springframework.stereotype.Component;

@Component
public class CookiePopup extends Div {

    public CookiePopup() {
//        final String message = "We are using cookies to make your visit here awesome!";
//        final String dismissLabel = "Cool!";
//        final String learnMoreLabel = "Why?";
//        final String learnMoreLink = "https://vaadin.com/terms-of-service";
//        final CookieConsent.Position position = CookieConsent.Position.TOP;
//        final CookieConsent consent = new CookieConsent(message, dismissLabel, learnMoreLabel, learnMoreLink, position);
//        add(consent);
        add(new CookieConsent());

    }

}