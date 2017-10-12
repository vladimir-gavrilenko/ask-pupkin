package gva.service.impl;

import gva.service.AvatarService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Value("${avatar.path}")
    private String avatarPath;

    @PostConstruct
    private void init() {
        try {
            FileUtils.forceMkdir(new File(avatarPath));
        } catch (IOException e) {
            System.err.println("Failed to create directories for avatars uploading");
        }
    }

    @Override
    public void store(String fileName, byte[] fileContent) throws IOException {
        String fullName = avatarPath + fileName;
        try (FileOutputStream fileOutputStream = new FileOutputStream(fullName)) {
            fileOutputStream.write(fileContent);
        }
    }
}
