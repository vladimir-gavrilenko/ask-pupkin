package gva.controller;

import gva.exception.EmailExistsException;
import gva.exception.UsernameExistsException;
import gva.model.User;
import gva.model.UserDetailsImpl;
import gva.dto.UserDto;
import gva.service.AvatarService;
import gva.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    private final AvatarService avatarService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AvatarService avatarService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.avatarService = avatarService;
        this.authenticationManager = authenticationManager;
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
        User user = updateUserAccount(userDto, result);
        userDto.reload(user); // update displayed model (including avatar path)
        if (!result.hasErrors()) {
            model.addAttribute("success", true);
            changeUsernameInSecurityContext(userDto.getName());
        }
        return "settings";
    }

    @PostMapping("/avatar/{userId}")
    @ResponseBody
    public String handleAvatarUpload(@PathVariable Long userId, MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = userId + "." + extension;
        try {
            avatarService.store(fileName, file.getBytes());
            User user = userService.findById(userId);
            userService.updateAvatarPath(user, fileName);
            return avatarUploadingResult("ok", "/avatars/" + fileName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return avatarUploadingResult("error", null);
        }
    }

    private String avatarUploadingResult(String status, String url) {
        return "{\"status\":\"" + status + "\", \"url\": \"" + url + "\"}";
    }


    private void createUserAccount(UserDto userDto, BindingResult result) { // TODO see updateUserAccount
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(userService.encodePassword(userDto.getPassword()));
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

    private User updateUserAccount(UserDto userDto, BindingResult result) { // TODO see createUserAccount
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
        return user;
    }

    private void changeUsernameInSecurityContext(String newName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userDetails.getUser().setName(newName);
    }
}
