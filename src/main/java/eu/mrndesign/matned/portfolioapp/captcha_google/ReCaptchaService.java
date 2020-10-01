package eu.mrndesign.matned.portfolioapp.captcha_google;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReCaptchaService {

    @Value("${google.recaptcha.key.secret}")
    private String captchaSecretKey;

    @Value("${google.recaptcha.url}")
    private String recaptchaUrl;

    private final RestTemplate restTemplate;

    public ReCaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public boolean isResponseValid(String response) {
        String url = recaptchaUrl +
                "?secret=" +
                captchaSecretKey +
                "&response=" +
                response;
        ReCaptchaResponse reCaptchaResponse = restTemplate.postForEntity( url, null, ReCaptchaResponse.class).getBody();
        assert reCaptchaResponse != null;
        return reCaptchaResponse.isSuccess();
    }
}
