package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.request.image.MultipartUploadRequestDto;
import com.example.bootproject.vo.vo3.response.image.MultipartUploadResponseDto;
import org.springframework.core.io.Resource;

public interface MultipartService {
    MultipartUploadResponseDto uploadFile(MultipartUploadRequestDto dto);

    Resource download(String employeeId);
}
