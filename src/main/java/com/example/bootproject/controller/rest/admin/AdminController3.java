package com.example.bootproject.controller.rest.admin;

import com.example.bootproject.service.service3.api.MultipartService;
import com.example.bootproject.vo.vo3.request.image.MultipartUploadRequestDto;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class AdminController3 {
    private final MultipartService multipartService;
    @PutMapping("/admin/manager/{employee_id}")
    public String toggleManager(@PathVariable(name = "employee_id") String employeeId) {
        return "toggleManager";
    }

    @GetMapping("/admin/download/{employee_id}")
    public ResponseEntity<Resource> downloadEmployeePhoto(@PathVariable(name = "employee_id") String employeeId) {

        Resource file = multipartService.download(employeeId);
        if (file != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(file.getFilename().split("_|\\.")[0] + '.' + file.getFilename().split("_|\\.")[2], StandardCharsets.UTF_8) + "\"")
                    .body(file);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/admin/upload")
    public String uploadEmployeePhoto(@ModelAttribute MultipartUploadRequestDto dto) {
        /*Todo : 권한 확인 필요*/
        multipartService.uploadFile(dto);
        return "사원의 사진 파일 업로드";
    }
}
