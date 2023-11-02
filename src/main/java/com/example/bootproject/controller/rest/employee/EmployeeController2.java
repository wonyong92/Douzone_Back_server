package com.example.bootproject.controller.rest.employee;

import com.example.bootproject.service.service2.EmployeeService2;
import com.example.bootproject.vo.vo2.response.*;
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
public class EmployeeController2 {

    private final EmployeeService2 empService2;

    public boolean authCheckApi(){ //권한 확인 API
        return true;
    }

    public boolean validationId(String employeeId){ //Id Validation 체크
        return employeeId.matches("^[0-9]*$"); // 숫자로 구성 되어 있는지 확인
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

    public String validationStatus(String getStatus){
        return (getStatus.matches("^(승인|반려|)$")?getStatus:"");
    }


    // 본인의 연차 이력 조회 메서드 (승인, 반려, 전체 필터링 가능)
    /* TODO : 추후 권한 확인 추가 */
    @GetMapping("/employee/vacation/history")
    public ResponseEntity <Page<List<VacationRequestDto>>> getHistoryOfVacationOfMine(@RequestParam(name = "page") String getPageNum, @RequestParam(name="sort", defaultValue = "") String getSort, @RequestParam(name="sortOrder", defaultValue = "") String getSortOrder, @RequestParam(name="status",defaultValue = "") String getStatus) {
        //status validation check 추가 필요

        if(authCheckApi()){
            int currentPage = validationPageNum(getPageNum);
            String sort = validationSort(getSort);
            String sortOrder = validationDesc(getSortOrder);
            String status = validationStatus(getStatus);
            String id = "1234"; // session에서 가져온 id 값

            PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto = new PagingRequestWithIdStatusDto(currentPage,sort,sortOrder,id,status);
            Page<List<VacationRequestDto>> result = empService2.getHistoryOfVacationOfMine(pagingRequestWithIdStatusDto);
            log.info("getHistoryOfRejectedVacationOfMine result : {}", result);
            if (result.getData().isEmpty()) {
                return ResponseEntity.noContent().build(); //204 No Content
            }
            return ResponseEntity.ok(result); //200 OK

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR
        /*
         * 사원 권한 확인
         * 사원 권한 확인 성공 시 ->  자신의 반려된 연차 이력 데이터 반환 받음
         *                         리턴 받은 데이터가 비어 있으면 204 No Content 응답 반환
         *                         리턴 받은 데이터가 비어 있지 않으면 200 OK 응답 반환
         * 사원 권한 인증 실패시 -> 403 ERROR 반환
         * */

    }

    // 타 사원의 연차 사용 이력 조회 메서드
    /* TODO : 추후 권한 확인 추가 */
    @GetMapping("/employee/vacation/use/{employee_id}")
    public ResponseEntity <Page<List<VacationRequestDto>>> getHistoryOfUsedVacationOfEmployee(@PathVariable(name = "employee_id") String id,@RequestParam(name = "page") String getPageNum, @RequestParam(name="sort", defaultValue = "") String getSort, @RequestParam(name="sortOrder", defaultValue = "") String getSortOrder) {
        if(authCheckApi()){
            if(validationId(id)){
                int currentPage = validationPageNum(getPageNum);
                String sort = validationSort(getSort);
                String sortOrder = validationDesc(getSortOrder);
                PagingRequestWithIdDto pagingRequestWithIdDto = new PagingRequestWithIdDto(currentPage,sort,sortOrder,id);

                Page<List<VacationRequestDto>> result = empService2.getHistoryOfUsedVacationOfEmployee(pagingRequestWithIdDto);
                log.info("getHistoryOfUsedVacationOfEmployee result : {}", result);
                if (result.getData().isEmpty()) {
                    return ResponseEntity.noContent().build(); //204 No Content
                }
                return ResponseEntity.ok(result); //200 OK
            }
            log.info("Validation failed for id: {}", id);
            return ResponseEntity.badRequest().build(); //400 bad request

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR

        /*
        * 근태 담당자 권한 확인
        * 권한 확인 성공시 -> id의 validation 체크
        *               -> validation 체크 성공시 타 사원의 연차 사용 이력 데이터 반환 받음
        *                  반환한 데이터가 비어있을 때 : 204 No Content 반환
        *                  반환한 데이터가 비어있지 않을 때
        *  : 200 OK 반환
        *               -> validation 체크 실패 시 400 Bad Request 반환
        * 권한 확인 실패시 -> 403 ERROR 발생
        * */
    }
}
