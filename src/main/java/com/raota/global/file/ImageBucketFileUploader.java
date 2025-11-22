package com.raota.global.file;

import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;

@Profile("prod")
public class ImageBucketFileUploader implements FileUploader{
    @Override
    public String upload(MultipartFile file, String dirName) {
        return "https://mock.cdn.com/uploaded/702.jpg";
    }

    @Override
    public void delete(String filePath) {

    }
}
