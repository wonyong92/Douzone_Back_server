package com.example.bootproject.vo.vo1.request.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MultipartUploadRequestDto {
    MultipartFile uploadFile;
    String employeeId;
    Long fileId;
}
