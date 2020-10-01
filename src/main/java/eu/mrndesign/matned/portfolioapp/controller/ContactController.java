package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.captcha_google.ReCaptchaService;
import eu.mrndesign.matned.portfolioapp.message.Message;
import eu.mrndesign.matned.portfolioapp.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    private final MessageService messageService;
    private final ReCaptchaService reCaptchaService;


    public ContactController(MessageService messageService,
                             ReCaptchaService reCaptchaService) {
        this.messageService = messageService;
        this.reCaptchaService = reCaptchaService;
    }

    @GetMapping("/contact")
    public String contactInfo(Model model) {
        model.addAttribute("message", new Message());
        return "contact";
    }

    @PostMapping("/contact")
    public String contactInfoPost(@Validated Message message, BindingResult bindingResult, Model model, @RequestParam(name = "g-recaptcha-response") String captchaResponse){
        boolean validatedNotRobot = reCaptchaService.isResponseValid(captchaResponse);
        if (!validatedNotRobot) model.addAttribute("wrong_recaptcha", 1);
        if(bindingResult.hasErrors()) {
            model.addAttribute("err", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("message", message);
            return "contact";
        }
        if (validatedNotRobot) {
            messageService.send(message);
            message = new Message();
            model.addAttribute("sent", 1);
        }
        model.addAttribute("message", message);
        return "contact";
    }

}
