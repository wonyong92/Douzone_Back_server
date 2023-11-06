//package com.example.bootproject.controller.rest.admin;
//
//import com.example.bootproject.service.service2.AdminService2;
//import com.example.bootproject.vo.vo2.request.PagingRequestDto;
//import com.example.bootproject.vo.vo2.response.EmployeeDto;
//import com.example.bootproject.vo.vo3.response.Page;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class AdminController2 {
//
//    private final AdminService2 adminService2;
//
//    public boolean authCheckApi() { //권한 확인 API,
//        // 로그인 확인
//        return true;
//    }
//
//    public boolean validationId(String employeeId) { // Id Validation 체크
//        int idCheck = adminService2.getEmployeeCheck(employeeId); //id값이 실제로 테이블에 존재하면 1 반환
//        /* employeeId가 숫자로 구성 되어 있고,  실제로 테이블에 존재하는지 확인 */
//        return employeeId.matches("^[0-9]+$") && idCheck == 1;
//    }
//
//    public int validationPageNum(String getPageNum) { //요청 받은 페이지에 대한 validation check
//        try {
//            int currentPage = Integer.parseInt(getPageNum); // 쿼리파라미터로 받아온 페이지 번호를 int 형으로 변환
//            return (currentPage > 0 ? currentPage : 1); // 0보다 크면 받아온 데이터 반환, 그렇지 않으면 1 반환
//        } catch (NumberFormatException e) {
//            return 1; // 예외 발생시 1 반환
//        }
//    }
//
//    /* 정렬할 컬럼이 영문자와 '_'로 이루어 져있는지 확인 후 해당 값 반환, 조건 만족하지 않으면 비어있는 값 반환 */
//    public String validationSort(String getSort) {
//        return (getSort.matches("^[a-zA-Z_]+$") ? getSort : "''");
//    }
//
//
//    /* 정렬 방식이 desc 인지 확인 후 정렬 방식 반환, 조건 만족하지 않으면 비어있는 값 반환 */
//    public String validationDesc(String getSortOrder) {
//        return (getSortOrder.matches("^(desc|DESC|)$") ? getSortOrder : "''"); // 정렬할
//    }
//
//
//    // 전체 사원 정보 조회 메서드
//    /* TODO : 추후 권한 확인 추가 */
//    @GetMapping("/admin/employee/information")
//    public ResponseEntity<Page<List<EmployeeDto>>> getEmployeesInformation(@RequestParam(name = "page", defaultValue = "1") String getPageNum, @RequestParam(name = "sort", defaultValue = "") String getSort, @RequestParam(name = "sortOrder", defaultValue = "") String getSortOrder) {
//        if (authCheckApi()) { //권한 확인
//
//            int currentPage = validationPageNum(getPageNum); // 쿼리 파라미터로 받아온 페이지 번호에 대한 validation 체크
//            String sort = validationSort(getSort); // 쿼리 파라미터로 받아온 정렬 기준 컬럼에 대한 validation 체크
//            String sortOrder = validationDesc(getSortOrder); // 쿼리 파라미터로 받아온 정렬 방식에 대한 validation 체크
//
//            PagingRequestDto pagingRequestDto = new PagingRequestDto(currentPage, sort, sortOrder);
//            Page<List<EmployeeDto>> result = adminService2.getEmpInfo(pagingRequestDto); // 전체 사원 정보 반환
//            log.info("getEmpInfo result : {}", result);
//            if (result.getData().isEmpty()) {
//                return ResponseEntity.noContent().build(); // 204 No Content
//            }
//            return ResponseEntity.ok(result); // 200 OK
//
//        }
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 ERROR
//        /*
//         * admin 권한 확인 (+로그인 확인)
//         * 1. admin 권한, 로그인 확인 성공 시
//         * 쿼리 파라미터로 받아온 페이지 번호, sort할 컬럼, sort 방식에 대한 validation 체크
//         *  - validation 만족하지 않으면 기본 값으로 지정
//         * 전체 사원 정보 받아옴
//         *  - 정상적으로 데이터 존재시 200 OK
//         *  - 정상적으로 반환되었으나 데이터가 비어있을 경우 204 No Content
//         * 2. admin 권한 확인 실패 시
//         * 403 에러 반환
//         * */
//
//    }
//
//
//    //특정 사원의 정보 조회 메서드
//    /* TODO : 추후 권한 확인 추가 */
//    @GetMapping("/admin/employee/information/{employee_id}")
//    public ResponseEntity<EmployeeDto> getEmployeeInformationByEmployeeId(@PathVariable(name = "employee_id") String employeeId) {
//        if (authCheckApi()) { //권한 확인 api
//            if (!validationId(employeeId))  //validation 체크
//                return ResponseEntity.badRequest().build(); // 400 Bad Request 반환
//            EmployeeDto result = adminService2.getOneEmpInfo(employeeId); // 특정 사원의 정보 반환 받음
//            log.info("getOneEmpInfo result : {}", result);
//            if (result == null) {
//                return ResponseEntity.noContent().build(); //204 No Content 반환
//            }
//            return ResponseEntity.ok(result); // 200 OK, result 객체를 응답 본문으로 반환
//        }
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 error 반환
//        /*
//         * admin 권한 확인 (+로그인 확인)
//         * 1. admin 권한, 로그인 확인 성공시
//         * employeeId에 validation 체크
//         *  - validation check 실패시 400 Bad Request 반환
//         *  - validation check 성공시
//         *   -- 특정 사원의 정보 반환 받음
//         *     --- 리턴 데이터가 null 일 때 204 No Content
//         *     --- 리턴 데이터가 null이 아닐 때 200 OK
//         * 2. admin 권한 확인 실패 시
//         * 403 에러 반환
//         * */
//    }
//
//
//}
