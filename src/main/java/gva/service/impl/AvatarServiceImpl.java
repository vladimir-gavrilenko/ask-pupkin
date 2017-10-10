package gva.service.impl;

import gva.service.AvatarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AvatarServiceImpl implements AvatarService {
    @Value("${avatar.path}")
    private String avatarPath;

    @Override
    public void store(String fileName, byte[] fileContent) throws IOException {
        String fullName = avatarPath + fileName;
        try (FileOutputStream fileOutputStream = new FileOutputStream(fullName)) {
            fileOutputStream.write(fileContent);
        }
    }
}
