package com.raota.global.file;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("local")
public class LocalFileUploader implements FileUploader{
    @Override
    public String upload(MultipartFile file, String dirName) {
        return "";
    }

    @Override
    public void delete(String filePath) {

    }
}
