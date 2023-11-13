package com.example.bootproject.controller.rest.admin;

import com.example.bootproject.service.service1.AdminService1;
import com.example.bootproject.service.service3.api.MultipartService;
import com.example.bootproject.system.validator.PageRequestValidator;
import com.example.bootproject.system.validator.PagedLocalDateDtoValidator;
import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.request.EmployeeUpdateRequestDto;
import com.example.bootproject.vo.vo1.request.PageRequest;
import com.example.bootproject.vo.vo1.response.AttendanceApprovalUpdateResponseDto;
import com.example.bootproject.vo.vo1.response.EmployeeResponseDto;
import com.example.bootproject.vo.vo1.request.PagingRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeDto;
import com.example.bootproject.vo.vo1.request.image.MultipartUploadRequestDto;
import com.example.bootproject.vo.vo1.response.Page;
import com.example.bootproject.vo.vo1.response.image.MultipartUploadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.example.bootproject.system.util.ValidationChecker.getLoginIdOrNull;
import static com.example.bootproject.system.util.ValidationChecker.isAdmin;

@RestController

@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService1 adminService1;
    private final MultipartService multipartService;
    private final PageRequestValidator pageRequestValidator;
    private final PagedLocalDateDtoValidator pagedLocalDateDtoValidator;

    @InitBinder("pageRequest")
    protected void pageRequestMessageBinder(WebDataBinder binder) {
        binder.addValidators(pageRequestValidator);
    }

    @InitBinder("pagedLocalDateDto")
    protected void pagedLocalDateDtoMessageBinder(WebDataBinder binder) {
        binder.addValidators(pagedLocalDateDtoValidator);
    }

    //TODO : Employee 인터페이스에 getEmployeeCheck 통합하고 static으로 뽑아내기
    public boolean validationId(String employeeId) { // Id Validation 체크
        int idCheck = adminService1.getEmployeeCheck(employeeId); //id값이 실제로 테이블에 존재하면 1 반환
        /* employeeId가 숫자로 구성 되어 있고,  실제로 테이블에 존재하는지 확인 */
        return employeeId.matches("^[0-9]+$") && idCheck == 1;
    }

    //사원등록
    @PostMapping("/admin/register")
    public ResponseEntity<EmployeeResponseDto> registerEmployee(@ModelAttribute EmployeeInsertRequestDto dto, HttpServletRequest req) {
        if (!isAdmin(req)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        log.info("request{}", dto);

        EmployeeResponseDto responseDto = adminService1.insertEmployee(dto);

        if (responseDto != null) {
            log.info("성공적으로 데이터를 받았습니다: {}", responseDto);
            return ResponseEntity.ok(responseDto);
        } else {
            log.info("안좋은 데이터를 받았습니다: {}", responseDto);
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/admin/update/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable("employeeId") String employeeId, @ModelAttribute EmployeeUpdateRequestDto dto, HttpServletRequest req) {
        if (!isAdmin(req)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        log.info("request{}", dto);

        EmployeeResponseDto responseDto = adminService1.updateEmployee(dto);

        if (responseDto != null) {
            log.info("성공적으로 데이터를 받았습니다 {}", responseDto);
            return ResponseEntity.ok(responseDto);
        } else {
            log.info("안좋은 데이터를 받았습니다: {}", responseDto);
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/admin/information/{employeeId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("employeeId") String employeeId, HttpServletRequest req) {
        if (!isAdmin(req)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        adminService1.deleteEmployee(employeeId);
        return ResponseEntity.ok(1);
    }


    //admin2 end

    // 전체 사원 정보 조회 메서드
    @GetMapping("/admin/employee/information")
    public ResponseEntity<Page<List<EmployeeDto>>> getEmployeesInformation(@Valid PageRequest pageRequest, BindingResult br, HttpServletRequest req) {
        if (br.hasErrors()) {
            AttendanceApprovalUpdateResponseDto body = new AttendanceApprovalUpdateResponseDto();
            body.setMessage(br.getAllErrors().toString());
            log.info("Validation rule violated" + br.getAllErrors());
            if (br.hasFieldErrors("page")) {
                log.info("잘못된 페이지 번호 요청, 기본값인 1로 초기화 수행");
                pageRequest.setPage(1);
            }
            if (br.hasFieldErrors("sort")) {
                log.info("잘못된 정렬 대상 컬럼 이름 요청, 기본값인 1로 초기화 수행");
                pageRequest.setSort("''");
            }
            if (br.hasFieldErrors("desc")) {
                log.info("잘못된 정렬 방식 요청, 기본값인 1로 초기화 수행");
                pageRequest.setDesc("desc");
            }
        }
        if (isAdmin(req)) { //권한 확인

            int currentPage = pageRequest.getPage(); // 쿼리 파라미터로 받아온 페이지 번호에 대한 validation 체크
            String sort = pageRequest.getSort(); // 쿼리 파라미터로 받아온 정렬 기준 컬럼에 대한 validation 체크
            String sortOrder = pageRequest.getDesc(); // 쿼리 파라미터로 받아온 정렬 방식에 대한 validation 체크

            PagingRequestDto pagingRequestDto = new PagingRequestDto(currentPage, sort, sortOrder);
            Page<List<EmployeeDto>> result = adminService1.getEmpInfo(pagingRequestDto); // 전체 사원 정보 반환
            log.info("getEmpInfo result : {}", result);
            if (result.getData().isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(result); // 200 OK

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 ERROR
        /*
         * admin 권한 확인 (+로그인 확인)
         * 1. admin 권한, 로그인 확인 성공 시
         * 쿼리 파라미터로 받아온 페이지 번호, sort할 컬럼, sort 방식에 대한 validation 체크
         *  - validation 만족하지 않으면 기본 값으로 지정
         * 전체 사원 정보 받아옴
         *  - 정상적으로 데이터 존재시 200 OK
         *  - 정상적으로 반환되었으나 데이터가 비어있을 경우 204 No Content
         * 2. admin 권한 확인 실패 시
         * 403 에러 반환
         * */

    }

    //특정 사원의 정보 조회 메서드
    @GetMapping("/admin/employee/information/{employee_id}")
    public ResponseEntity<EmployeeDto> getEmployeeInformationByEmployeeId(@PathVariable(name = "employee_id") String employeeId, HttpServletRequest req) {
        if (isAdmin(req)) { //권한 확인 api
            if (!validationId(employeeId))  //validation 체크
                return ResponseEntity.badRequest().build(); // 400 Bad Request 반환
            EmployeeDto result = adminService1.getOneEmpInfo(employeeId); // 특정 사원의 정보 반환 받음
            log.info("getOneEmpInfo result : {}", result);
            if (result == null) {
                return ResponseEntity.noContent().build(); //204 No Content 반환
            }
            return ResponseEntity.ok(result); // 200 OK, result 객체를 응답 본문으로 반환
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 error 반환
        /*
         * admin 권한 확인 (+로그인 확인)
         * 1. admin 권한, 로그인 확인 성공시
         * employeeId에 validation 체크
         *  - validation check 실패시 400 Bad Request 반환
         *  - validation check 성공시
         *   -- 특정 사원의 정보 반환 받음
         *     --- 리턴 데이터가 null 일 때 204 No Content
         *     --- 리턴 데이터가 null이 아닐 때 200 OK
         * 2. admin 권한 확인 실패 시
         * 403 에러 반환
         * */
    }

    @PutMapping("/admin/manager/{employee_id}")
    public ResponseEntity<Void> toggleManager(@PathVariable(name = "employee_id") String employeeId, HttpServletRequest req) {
        if (isAdmin(req)) {
            boolean result = adminService1.toggleManager(employeeId);
            if (result) {
                return ResponseEntity.ok().build();
            }
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/admin/download/{employee_id}")
    public ResponseEntity<Resource> downloadEmployeePhoto(@PathVariable(name = "employee_id") String employeeId, HttpServletRequest req) {
        String loginEmployeeId = getLoginIdOrNull(req);

        if (loginEmployeeId == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Resource file = multipartService.download(employeeId);
        if (file != null) {
            log.info("지정된 파일이 없으므로 기본 프로필 이미지 전달");
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(file.getFilename().split("_|\\.")[0] + '.' + file.getFilename().split("_|\\.")[1], StandardCharsets.UTF_8) + "\"").body(file);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/admin/upload")
    public ResponseEntity<Integer> uploadEmployeePhoto(@ModelAttribute MultipartUploadRequestDto dto, HttpServletRequest req) {
        if (isAdmin(req)) {
            MultipartUploadResponseDto result = multipartService.uploadFile(dto);
            return ResponseEntity.ok(result.getFileId());
        }
        //TODO : admin 확인 로직을 인터셉터로 공통 부분 추출 필요
        log.info("admin이 아닌 사용자의 요청 발생");
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


    //admin3 end

}