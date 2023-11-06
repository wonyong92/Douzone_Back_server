//package com.example.bootproject.controller.rest.admin;
//
//import com.example.bootproject.service.service3.api.AdminService;
//import com.example.bootproject.service.service3.api.MultipartService;
//import com.example.bootproject.vo.vo3.request.image.MultipartUploadRequestDto;
//import com.example.bootproject.vo.vo3.response.image.MultipartUploadResponseDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class AdminController3 {
//    private final MultipartService multipartService;
//    private final AdminService adminService3;
//
//
//    @PutMapping("/admin/manager/{employee_id}")
//    public ResponseEntity<Void> toggleManager(@PathVariable(name = "employee_id") String employeeId, HttpServletRequest req) {
//        if (isAdmin(req)) {
//            boolean result = adminService3.toggleManager(employeeId);
//            if (result) {
//                return ResponseEntity.ok().build();
//            }
//        } else {
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    @GetMapping("/admin/download/{employee_id}")
//    public ResponseEntity<Resource> downloadEmployeePhoto(@PathVariable(name = "employee_id") String employeeId) {
//
//        Resource file = multipartService.download(employeeId);
//        if (file != null) {
//            return ResponseEntity.ok()
//                    .contentType(MediaType.IMAGE_PNG)
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(file.getFilename().split("_|\\.")[0] + '.' + file.getFilename().split("_|\\.")[2], StandardCharsets.UTF_8) + "\"")
//                    .body(file);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping("/admin/upload")
//    public ResponseEntity<Integer> uploadEmployeePhoto(@ModelAttribute MultipartUploadRequestDto dto, HttpServletRequest req) {
//        if (isAdmin(req)) {
//            MultipartUploadResponseDto result = multipartService.uploadFile(dto);
//            return ResponseEntity.ok(result.getFileId());
//        }
//        //TODO : admin 확인 로직을 인터셉터로 공통 부분 추출 필요
//        log.info("admin이 아닌 사용자의 요청 발생");
//        return new ResponseEntity(HttpStatus.FORBIDDEN);
//    }
//
//    private boolean isAdmin(HttpServletRequest req) {
//
//        HttpSession session = req.getSession(false);
//        if (session.getAttribute("loginId") == null) {
//            return false;
//        }
//        return (boolean) session.getAttribute("admin");
//    }
//}
