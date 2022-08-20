package com.example.art.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileStorageService {

    private final String UPLOAD_PATH = "static"+ File.separator +"brochures";

    public String saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {

        Path path = Paths.get(UPLOAD_PATH);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
//
        Path uploadPath = path.toAbsolutePath().normalize();
//        log.info("uploadPath: "+uploadPath);

//        String path = new ClassPathResource("static/files/").getFile().getAbsolutePath();

        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = Paths.get(new ClassPathResource(UPLOAD_PATH+File.separator+fileName).getFile().getAbsolutePath());
//            Path filePath2 = Paths.get(path+File.separator+fileName);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

        return fileName;
    }

    public Resource getFileAsResource(String fileName) throws IOException {

//        Path dirPath = Paths.get(new ClassPathResource(UPLOAD_PATH).getFile().getAbsolutePath());
        Path filePath = Paths.get(UPLOAD_PATH).resolve(fileName).toAbsolutePath();

        Resource resource = new UrlResource(filePath.toUri());

        if(resource.exists())
            return resource;

        return null;

    }

}
