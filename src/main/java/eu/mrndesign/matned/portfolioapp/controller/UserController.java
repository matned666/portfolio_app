package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.captcha_google.ReCaptchaService;
import eu.mrndesign.matned.portfolioapp.dto.RestrictedUserDTO;
import eu.mrndesign.matned.portfolioapp.dto.UserDTO;
import eu.mrndesign.matned.portfolioapp.dto.UserDTOInterface;
import eu.mrndesign.matned.portfolioapp.model.Countries;
import eu.mrndesign.matned.portfolioapp.model.UserRole;
import eu.mrndesign.matned.portfolioapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService service;
    private final ReCaptchaService reCaptchaService;


    public UserController(UserService service,
                          ReCaptchaService reCaptchaService) {
        this.service = service;
        this.reCaptchaService = reCaptchaService;
    }

    @GetMapping("/register")
    public String getRegistration(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("countries", Countries.values());
        model.addAttribute("registrationObject", userDTO);
        return "registration";
    }

    @PostMapping("/register")
    public String postRegistration(@Validated UserDTO userDTO,
                                   BindingResult bindingResult,
                                   Model model,
                                   @RequestParam(name = "g-recaptcha-response") String captchaResponse) {
        boolean validatedNotRobot = reCaptchaService.isResponseValid(captchaResponse);
        if (!validatedNotRobot) model.addAttribute("wrong_recaptcha", 1);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("countries", Countries.values());
            model.addAttribute("registrationObject", userDTO);
            return "registration";
        }
        if (validatedNotRobot)
            service.register(userDTO);
        else return "registration";
        return "redirect:/login";
    }


    @GetMapping("/users-list")
    public String getAllUsersList(Model model, HttpServletRequest request) {
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            model.addAttribute("all_users", service.findAll());
            return "users-list";
        } else return "accessDenied";
    }

    @GetMapping("/account")
    public String accountShow(Model model, Principal principal) {
        RestrictedUserDTO actualUser = service.findByLoginRestricted(principal.getName());
        model.addAttribute("userToSee", actualUser);
        return "user";

    }

    @GetMapping("/account/{id}")
    public String accountShow(@PathVariable Long id,
                              Model model,
                              HttpServletRequest request) {
        if (!request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            return "accessDenied";
        } else {
            UserDTO user = service.findById(id);
            model.addAttribute("userToSee", user);
            return "user";
        }
    }

    @GetMapping("/user/edit/{id}")
    public String accountEditionPage(@PathVariable Long id,
                                     Model model,
                                     HttpServletRequest request) {
        UserDTOInterface<?> user;
        if (!request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            return "accessDenied";
        } else {
            user = service.findById(id);
            model.addAttribute("actionUrl", "/user/edit-user/{"+user.getId()+"}");
            model.addAttribute("countries", Countries.values());
            model.addAttribute("editedUser", user);
            return "edit-user";
        }
    }

    @GetMapping("/user/edit-user")
    public String accountEditionPageForActualCommonUser(Model model,
                                                        Principal principal) {
        RestrictedUserDTO user = RestrictedUserDTO.apply(service.findByLogin(principal.getName()));
        model.addAttribute("actionURL", "/user/edit-user");
        model.addAttribute("countries", Countries.values());
        model.addAttribute("editedUser", user);
        return "edit-user";

    }

    @PostMapping("/user/edit-user")
    public String accountEditionPostPageForActualCommonUser(@Validated RestrictedUserDTO restrictedUserDTO,
                                                            BindingResult bindingResult,
                                                            Model model,
                                                            Principal principal,
                                                            @RequestParam(name = "g-recaptcha-response") String captchaResponse) {
        boolean validatedNotRobot = reCaptchaService.isResponseValid(captchaResponse);
        if (!validatedNotRobot) model.addAttribute("wrong_recaptcha", 1);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("binding", bindingResult);
            model.addAttribute("countries", Countries.values());
            model.addAttribute("registrationObject", restrictedUserDTO);
            return "edit-user";
        }
        if (validatedNotRobot)
            service.updateByLogin(principal.getName(), restrictedUserDTO);
        else return "registration";
        return "redirect:/account";

    }

    @PostMapping("/user/edit/{id}")
    public String editUserProcess(@PathVariable Long id,
                                  @Validated RestrictedUserDTO restrictedUserDTO,
                                  BindingResult bindingResult,
                                  Model model,
                                  HttpServletRequest request) {
        if (!request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            return "accessDenied";
        } else {
            if (bindingResult.hasErrors()) {
                model.addAttribute("error", "error");
                model.addAttribute("binding", bindingResult);
                model.addAttribute("countries", Countries.values());
                model.addAttribute("editedUser", restrictedUserDTO);
                return "edit-user";
            }
            service.update(id, restrictedUserDTO);
            return "redirect:/account/" + id;
        }
    }

    @GetMapping("/user/delete/{id}")
    public String accountDelete(@PathVariable Long id,
                                HttpServletRequest request) {
        if (!request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            return "accessDenied";
        } else {
            service.delete(id);
            return "redirect:/users-list";
        }
    }


}

