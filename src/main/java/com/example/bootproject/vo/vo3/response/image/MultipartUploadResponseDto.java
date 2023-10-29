package com.example.bootproject.vo.vo3.response.image;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MultipartUploadResponseDto {
    Integer fileId;
    String employeeId;
    String fileName;
    String uuid;
    LocalDateTime uploadDate;
}
