package com.example.bootproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultipartServiceImpl implements MultipartService {

    @Value("${multipart.upload.path}")
    private String uploadPath;

    private List<String> uploadFile(List<MultipartFile> files) throws IOException {
        List<String> names = new ArrayList<>();
        // Normalize the file name
        files.forEach(file -> {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Generate a unique file name based on the current timestamp
            String uniqueFileName = generateUniqueFileName(originalFileName);

            // Create the upload directory if it doesn't exist
            Path directoryPath = Paths.get(uploadPath);
            if (!Files.exists(directoryPath)) {
                try {
                    Files.createDirectories(directoryPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Copy the file to the target location with the unique file name
            Path targetLocation = Paths.get(uploadPath).resolve(uniqueFileName);
            try {
                Files.copy(file.getInputStream(), targetLocation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            names.add(uniqueFileName);
        });
        return names;
    }

    private String generateUniqueFileName(String originalFileName) {
        UUID uuid = UUID.randomUUID();

        int lastIndex = originalFileName.lastIndexOf('.');
        String extension = "";
        String fileName;

        if (lastIndex != -1) {
            extension = originalFileName.substring(lastIndex);
            fileName = originalFileName.substring(0, lastIndex);
        } else {
            fileName = originalFileName;
        }

        return fileName + "_" + uuid + extension;
    }


    public Resource loadFileAsResource(Integer postId, int num) {
        String filePath = "uploads/";
        AtomicReference<Resource> result = new AtomicReference<>();
        log.info("postId {} , num {}", postId, num);
//        memberIdRepository.findById(memberId).ifPresent(memberId -> {
//            try {
//                Path filePathPath = Paths.get(filePath + (num == 1 ? memberId.getFile1() : memberId.getFile2())).normalize();
//                log.info("filePath {} ", filePath);
//                Resource resource = new UrlResource(filePathPath.toUri());
//                if (resource.exists()) {
//                    result.set(resource);
//                } else {
//                    result.set(null);
//                }
//            } catch (Exception ex) {
//                result.set(null);
//            }
//        });
        return result.get();
    }
}
