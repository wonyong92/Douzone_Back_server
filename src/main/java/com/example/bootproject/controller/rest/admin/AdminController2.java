package com.example.bootproject.controller.rest.admin;
import com.example.bootproject.service.service2.AdminService2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo2.response.Page;
import com.example.bootproject.vo.vo2.response.PagingRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController2 {

    private final AdminService2 adminService;

    public boolean authCheckApi(){ //권한 확인 API,
        // 로그인 확인
        return true;
    }

    public boolean validationId(String employeeId){ // Id Validation 체크
        return employeeId.matches("^[0-9]+$"); // 숫자로 구성 되어 있는지 확인
    }

    public int validationPageNum(String getPageNum){ //요청 받은 페이지에 대한 validation check
        try{
            int currentPage = Integer.parseInt(getPageNum); // 쿼리파라미터로 받아온 페이지 번호를 int 형으로 변환
            return (currentPage>0?currentPage:1);
        }catch (NumberFormatException e){
            return 1;
        }
    }

    public String validationSort(String getSort){
        return (getSort.matches("^[a-zA-Z_]+$")?getSort:"");
    }



    public String validationDesc(String getSortOrder){
        return (getSortOrder.matches("^(desc|DESC|)$")?getSortOrder:"");
    }


    // 전체 사원 정보 조회 메서드
    /* TODO : 추후 권한 확인 추가 */
    @GetMapping("/admin/employee/information")
    public ResponseEntity <Page<List<EmployeeDto>>> getEmployeesInformation(@RequestParam(name = "page", defaultValue = "1") String getPageNum, @RequestParam(name="sort", defaultValue = "") String getSort, @RequestParam(name="sortOrder", defaultValue = "") String getSortOrder) {
        if(authCheckApi()){
            //페이징 코드
                // 페이지 번호, Sort 할 컬럼에 대한 validation check
                int currentPage = validationPageNum(getPageNum);
                String sort = validationSort(getSort);
                String sortOrder = validationDesc(getSortOrder);

                PagingRequestDto pagingRequestDto = new PagingRequestDto(currentPage,sort,sortOrder);
                Page<List<EmployeeDto>> result = adminService.getEmpInfo(pagingRequestDto); // 전체 사원 정보 반환
                log.info("getEmpInfo result : {}",result);
                if(result.getData().isEmpty()){
                    return ResponseEntity.noContent().build(); // 204 No Content
                }
                return ResponseEntity.ok(result); // 200 OK

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 ERROR
        /*
        * admin 권한 확인
        * admin 권한 확인 성공시 ->  전체 사원 정보 반환 받음
        *                          리턴 받은 데이터가 비어있을 때 : 204 No Content 응답 생성
        *                          리턴 받은 데이터가 비어있지 않을 때 : 200 응답 받음
        * admin 권한 확인 실패 ->  403 에러 반환
        * */

    }


    //특정 사원의 정보 조회 메서드
    /* TODO : 추후 권한 확인 추가 */
    @GetMapping("/admin/employee/information/{employee_id}")
    public ResponseEntity<EmployeeDto> getEmployeeInformationByEmployeeId(@PathVariable(name = "employee_id") String employeeId) {
        if(authCheckApi()){ //권한 확인 api
            if(!validationId(employeeId))  //validation 체크
                return ResponseEntity.badRequest().build(); // 400 Bad Request 반환
            EmployeeDto result = adminService.getOneEmpInfo(employeeId);
            log.info("getOneEmpInfo result : {}",result);
            if(result==null){
                return ResponseEntity.noContent().build(); //204 No Content 반환
            }
            return ResponseEntity.ok(result); // 200 OK, result 객체를 응답 본문으로 반환
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 error 반환
        /*
        * admin 권한 확인
        * admin 권한 확인 성공시 -> 매개변수 값이 숫자인지 validation 체크
        *                     ->  validation 체크 성공시 특정 사원의 정보 반환 받음
        *                         리턴 받은 정보가 null이면 204 No Content 반환
        *                         리턴 받은 정보가 null이 아니면 : 200 응답 받음
        *                     ->  validation 체크 실패시 400 Bad Request 반환
        * admin 권한 확인 실패 시 403 에러 반환
        * */
    }






}
