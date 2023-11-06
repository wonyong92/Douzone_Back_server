package com.example.bootproject.controller.rest.employee;

import com.example.bootproject.service.service2.EmployeeService2;
import com.example.bootproject.vo.vo2.request.PagingRequestWithIdStatusDto;
import com.example.bootproject.vo.vo2.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    public int validationPageNum(String getPageNum){ //요청 받은 페이지에 대한 validation check
        try{
            int currentPage = Integer.parseInt(getPageNum); // 쿼리파라미터로 받아온 페이지 번호를 int 형으로 변환
            return (currentPage>0?currentPage:1);// 0보다 크면 받아온 데이터 반환, 그렇지 않으면 1 반환
        }catch (NumberFormatException e){
            return 1; // 예외 발생시 1 반환
        }
    }

    /* 정렬할 컬럼이 영문자와 '_'로 이루어 져있는지 확인 후 해당 값 반환, 조건 만족하지 않으면 비어있는 값 반환 */
    public String validationSort(String getSort){
        return (getSort.matches("^[a-zA-Z_]+$")?getSort:"''");
    }

    /* 정렬 방식이 desc 인지 확인 후 정렬 방식 반환, 조건 만족하지 않으면 비어있는 값 반환 */
    public String validationDesc(String getSortOrder){
        return (getSortOrder.matches("^(desc|DESC|)$")?getSortOrder:"''");
    }

    /* 신청 결과가 승인 혹은 반려가 들어왔으면 해당 값 리턴하고, 이외의 값은 빈값 리턴*/
    public String validationStatus(String getStatus){
        return (getStatus.matches("^(승인|반려|)$")?getStatus:"''");
    }


    // 본인의 연차 이력 조회 메서드 (승인, 반려, 전체 필터링 가능)
    /* TODO : 추후 권한 확인 추가 */
    @GetMapping("/employee/vacation/history")
    public ResponseEntity <Page<List<VacationRequestDto>>> getHistoryOfVacationOfMine(@RequestParam(name = "page", defaultValue = "1") String getPageNum, @RequestParam(name="sort", defaultValue = "") String getSort, @RequestParam(name="sortOrder", defaultValue = "") String getSortOrder, @RequestParam(name="status",defaultValue = "") String getStatus) {
        //status validation check 추가 필요

        if(authCheckApi()){
            int currentPage = validationPageNum(getPageNum); //페이지 번호에 대한 validation 체크
            String sort = validationSort(getSort); // 페이지 정렬 기준 컬럼에 대한 validation 체크
            String sortOrder = validationDesc(getSortOrder); // 정렬 방식에 대한 validation 체크
            String status = validationStatus(getStatus); // 신청 결과에 대한 validation 체크
            String id = "1234"; // session에서 가져온 id 값 (임의 지정)

            PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto = new PagingRequestWithIdStatusDto(currentPage,sort,sortOrder,id,status);
            Page<List<VacationRequestDto>> result = empService2.getHistoryOfVacationOfMine(pagingRequestWithIdStatusDto); // 본인의 연차 이력 데이터 반환 받음
            log.info("getHistoryOfRejectedVacationOfMine result : {}", result);
            if (result.getData().isEmpty()) {
                return ResponseEntity.noContent().build(); //204 No Content
            }
            return ResponseEntity.ok(result); //200 OK

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR
        /*
         * 사원 권한 확인 (+로그인 확인)
         * 1. 사원 권한, 로그인 확인 성공 시
         * 쿼리 파라미터로 받아온 페이지 번호, sort할 컬럼, sort 방식, 신청 결과에 대한 validation 체크
         *   - validation 만족하지 않으면 기본 값으로 지정
         * 본인의 연차 이력 데이터 반환 받음
         *  - 정상적으로 데이터 존재시 200 OK
         *  - 정상적으로 반환되었으나 데이터가 비어있을 경우 204 No Content
         * 2. 사원 권한, 로그인 인증 실패시
         * 403 ERROR 반환
         * */


    }

    /*TODO : 권한 확인 로직 실제 구현 해야함*/
    /*TODO : Session에서 아이디 값 받아오도록 해야함*/

    // 본인의 잔여 연차 개수 확인
    @GetMapping("/employee/vacation/remain")
    public ResponseEntity<Integer> getRemainOfVacationOfMine() {
        if(authCheckApi()){ //권한 확인
            String id="1234"; //임의 지정 데이터
            int setting = empService2.getRemainOfVacationOfMine(id); //본인의 잔여 연차 개수 반환 받음
            log.info("남은 연차 수 : {}",setting);

            return ResponseEntity.ok(setting); // 200 OK

        }
        log.info("권한 에러 발생 (403)");
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Error
    /*
     * 사원 권한 확인 (+로그인 확인)
     * 1. 사원 권한, 로그인 확인 성공 시
     * 본인의 잔여 연차 개수 반환 받음
     *  - 정상적으로 데이터 존재시 200 OK
     * 2. 사원 권한, 로그인 인증 실패시
     * 403 ERROR 반환
     */


    }






}
