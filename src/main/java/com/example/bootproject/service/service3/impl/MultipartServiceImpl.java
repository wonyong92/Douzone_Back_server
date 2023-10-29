package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper3.multipart.MultipartMapper;
import com.example.bootproject.service.service3.api.MultipartService;
import com.example.bootproject.vo.vo3.request.image.MultipartUploadRequestDto;
import com.example.bootproject.vo.vo3.response.image.MultipartUploadResponseDto;
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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class MultipartServiceImpl implements MultipartService {
    private final MultipartMapper multipartMapper;
    @Value("${multipart.upload.path}")
    private String uploadPath;

    public MultipartUploadResponseDto uploadFile(MultipartUploadRequestDto dto) {
        MultipartFile file = dto.getUploadFile();
        String employeeId = dto.getEmployeeId();

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        String uniqueFileName = generateUniqueFileName(originalFileName);

        Path directoryPath = Paths.get(uploadPath);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path targetLocation = Paths.get(uploadPath).resolve(uniqueFileName);
        try {
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Integer generatedFileId = multipartMapper.upload(employeeId, uniqueFileName);
        MultipartUploadResponseDto result = multipartMapper.findByFileId(generatedFileId);
        return result;
    }

    @Override
    public Resource download(String employeeId) {

        String filePath = "uploads/";
        AtomicReference<Resource> result = new AtomicReference<>();
        Integer findFileNum = multipartMapper.findByEmployeeId(employeeId);
        MultipartUploadResponseDto fileData = multipartMapper.findByFileId(findFileNum);
        if (fileData == null) {
            fileData.setFileName("default.png");
        }
        try {
            Path filePathPath = Paths.get(filePath + fileData.getFileName()).normalize();
            log.info("find Path {}", filePathPath);
            Resource resource = new UrlResource(filePathPath.toUri());
            if (resource.exists()) {
                result.set(resource);
            } else {
                result.set(null);
            }
        } catch (Exception ex) {
            result.set(null);
        }
        return result.get();

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

        return uuid + "_" + fileName + extension;
    }
}
