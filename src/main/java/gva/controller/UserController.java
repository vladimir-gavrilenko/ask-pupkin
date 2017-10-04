package gva.controller;

import gva.exception.EmailExistsException;
import gva.exception.UsernameExistsException;
import gva.model.User;
import gva.model.dto.UserDto;
import gva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder encoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("userDto") UserDto userDto,
                         HttpServletRequest request, BindingResult result) {
        User registeredUser = createUserAccount(userDto, result); // TODO refactor, binding result
        if (registeredUser == null) {
            return "signup";
        }
        authenticateUserAndSetSession(userDto.getName(), userDto.getPassword(), request);
        return "redirect:/";
    }

    private User createUserAccount(UserDto userDto, BindingResult result) { // TODO duplicate code
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(encoder.encode(userDto.getPassword()));
        try {
            userService.create(user);
        } catch (UsernameExistsException e) {
            System.err.println(e.getMessage());
            result.rejectValue("name", "nameError");
            return null;
        } catch (EmailExistsException e) {
            System.err.println(e.getMessage());
            result.rejectValue("email", "emailError");
            return null;
        }
        return user;
    }

    private void authenticateUserAndSetSession(Object username, Object password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    @GetMapping("/settings")
    public String settings(Model model, Principal principal) {
        String name = principal.getName();
        User user = userService.findByName(name);
        UserDto userDto = new UserDto(user);
        System.out.println(userDto);
        model.addAttribute("userDto", userDto);
        return "settings";
    }

    @PostMapping("/settings")
    public String update(Model model, @ModelAttribute("userDto") UserDto userDto,
                         HttpServletRequest request, BindingResult result) {
        User updatedUser = updateUserAccount(userDto, result); // TODO refactor, binding result, return value
        if (updatedUser != null) {
            model.addAttribute("success", true);
            changeUsernameInSecurityContext(userDto.getName(), request);
        }
        return "settings";
    }

    private User updateUserAccount(UserDto userDto, BindingResult result) { // TODO duplicate code
        User user = userService.findById(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        try {
            userService.update(user);
        } catch (UsernameExistsException e) {
            System.err.println(e.getMessage());
            result.rejectValue("name", "nameError");
            return null;
        } catch (EmailExistsException e) {
            System.err.println(e.getMessage());
            result.rejectValue("email", "emailError");
            return null;
        }
        return user;
    }

    private void changeUsernameInSecurityContext(String username, HttpServletRequest request) {
        // stub
    }
}
