package gva.controller;

import gva.exception.EmailExistsException;
import gva.exception.UsernameExistsException;
import gva.model.User;
import gva.model.UserDetailsImpl;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
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
        createUserAccount(userDto, result);
        if (result.hasErrors()) {
            return "signup";
        }
        authenticateUserAndSetSession(userDto.getName(), userDto.getPassword(), request);
        return "redirect:/";
    }

    private void createUserAccount(UserDto userDto, BindingResult result) { // TODO see updateUserAccount
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(encoder.encode(userDto.getPassword()));
        try {
            userService.create(user);
        } catch (UsernameExistsException e) {
            System.err.println(e.getMessage());
            result.rejectValue("name", "nameError");
        } catch (EmailExistsException e) {
            System.err.println(e.getMessage());
            result.rejectValue("email", "emailError");
        }
    }

    private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        request.getSession(); // generate session if one doesn't exist
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
    public String update(Model model, @ModelAttribute("userDto") UserDto userDto, BindingResult result) {
        updateUserAccount(userDto, result);
        if (!result.hasErrors()) {
            model.addAttribute("success", true);
            changeUsernameInSecurityContext(userDto.getName());
        }
        return "settings";
    }

    private void updateUserAccount(UserDto userDto, BindingResult result) { // TODO see createUserAccount
        User user = userService.findById(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        try {
            userService.update(user);
        } catch (UsernameExistsException e) {
            System.err.println(e.getMessage());
            result.rejectValue("name", "nameError");
        } catch (EmailExistsException e) {
            System.err.println(e.getMessage());
            result.rejectValue("email", "emailError");
        }
    }

    private void changeUsernameInSecurityContext(String newName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userDetails.getUser().setName(newName);
    }
}
