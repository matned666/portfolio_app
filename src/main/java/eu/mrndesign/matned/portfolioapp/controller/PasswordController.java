package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.captcha_google.ReCaptchaService;
import eu.mrndesign.matned.portfolioapp.dto.PasswordDTO;
import eu.mrndesign.matned.portfolioapp.dto.PasswordResetDTO;
import eu.mrndesign.matned.portfolioapp.service.MessageService;
import eu.mrndesign.matned.portfolioapp.service.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

    private final MessageService messageService;
    private final PasswordService passWordService;
    private final ReCaptchaService reCaptchaService;

    public PasswordController(MessageService messageService,
                              PasswordService passWordService,
                              ReCaptchaService reCaptchaService) {
        this.messageService = messageService;
        this.passWordService = passWordService;
        this.reCaptchaService = reCaptchaService;
    }

    @GetMapping("/resetPassword")
    public String resetPasswordGet(Model model){
        model.addAttribute("resetForm", new PasswordResetDTO());
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPasswordPost(@Validated PasswordResetDTO passwordResetDTO,
                                    BindingResult bindingResult, Model model,
                                    @RequestParam(name = "g-recaptcha-response") String captchaResponse){
        boolean validatedNotRobot = reCaptchaService.isResponseValid(captchaResponse);
        if (!validatedNotRobot) model.addAttribute("wrong_recaptcha", 1);
        if (bindingResult.hasErrors()){
            model.addAttribute("err", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("resetForm", passwordResetDTO);
            return "resetPassword";
        }
        if (validatedNotRobot) {
            messageService.sendPasswordReset(passwordResetDTO);
            model.addAttribute("isTokenSent", 1);
            return "loginPage";
        } else {
            model.addAttribute("resetForm", passwordResetDTO);
            return "resetPassword";
        }
    }


    @GetMapping("/token/{token}")
    public String changePasswordGet(@PathVariable String token,
                                    Model model){
        PasswordDTO passwordDTO = passWordService.findByToken(token);
        if(passwordDTO == null) {
            model.addAttribute("tokenError", 1);
            return "loginPage";
        }
        model.addAttribute("passwordDTO",passwordDTO);
        return "change-password";
    }


    @PostMapping("/token/{token}")
    public String changePasswordPost(@PathVariable String token,
                                     @Validated PasswordDTO passwordDTO,
                                     BindingResult bindingResult, Model model,
                                     @RequestParam(name = "g-recaptcha-response") String captchaResponse){
        boolean validatedNotRobot = reCaptchaService.isResponseValid(captchaResponse);
        if (!validatedNotRobot) model.addAttribute("wrong_recaptcha", 1);
        if (bindingResult.hasErrors()){
            model.addAttribute("err", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("passwordDTO", passwordDTO);
            return "change-password";
        }
        if (validatedNotRobot) {
            model.addAttribute("passwordSet", 1);
            passWordService.setPassword(passwordDTO, token);
            return "loginPage";
        }else {
            model.addAttribute("passwordDTO", passwordDTO);
            return "change-password";
        }
    }

}
