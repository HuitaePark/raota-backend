package com.raota.global.file;

import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;

@Profile("bucket")
public class ImageBucketFileUploader implements FileUploader{
    @Override
    public String upload(MultipartFile file, String dirName) {
        return "";
    }

    @Override
    public void delete(String filePath) {

    }
}
