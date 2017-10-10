package gva.service;

import java.io.IOException;

public interface AvatarService {

    void store(String fileName, byte[] fileContent) throws IOException;

}
