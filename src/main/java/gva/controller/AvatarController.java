package gva.controller;

import gva.service.AvatarService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AvatarController {
    private final AvatarService avatarService;

    @Autowired
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping("/avatar/{username}")
    @ResponseBody
    public String handleUpload(@PathVariable String username, MultipartFile file) {
        System.out.println(file);
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = username + "." + extension;
        try {
            avatarService.store(fileName, file.getBytes());
            return "ok";
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return "error";
        }
    }
}
