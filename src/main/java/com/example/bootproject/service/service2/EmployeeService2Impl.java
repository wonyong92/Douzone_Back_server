//package com.example.bootproject.service.service2;
//
//
//import com.example.bootproject.repository.mapper2.EmployeeMapper2;
//import com.example.bootproject.vo.vo1.request.PagingRequestWithIdStatusDto;
//import com.example.bootproject.vo.vo1.response.VacationRequestDto;
//import com.example.bootproject.vo.vo1.response.Page;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.example.bootproject.vo.vo1.response.Page.PAGE_SIZE;
//
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class EmployeeService2Impl implements EmployeeService2 {
//
//    private final EmployeeMapper2 empMapper2;
//
//    @Override
//    public Page<List<VacationRequestDto>> getHistoryOfVacationOfMine(PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto) {
//        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물 개수
//        int startRow = (pagingRequestWithIdStatusDto.getCurrentPage() - 1) * size; // 가져오기 시작할 row의 번호
//        String orderByCondition = pagingRequestWithIdStatusDto.getSort(); // 정렬할 컬럼 이름
//
//        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
//        List<VacationRequestDto> getData = empMapper2.getHistoryOfVacationOfMine(size, orderByCondition, startRow, pagingRequestWithIdStatusDto.getSortOrder(), pagingRequestWithIdStatusDto.getId(), pagingRequestWithIdStatusDto.getStatus());
//        log.info("empMapper2.getHistoryOfRejectedVacationOfMine()의 getData : {}", getData);
//
//        /* 가져온 데이터가 비어있다면 Page 객체를 새로 생성하고, Page 객체 중 가져온 데이터를 담는 속성에 빈 ArrayList를 생성하여 리턴한다 */
//        if (getData.isEmpty()) {
//            Page<List<VacationRequestDto>> pageObj = new Page();
//            pageObj.setData(new ArrayList<>());
//            return pageObj;
//        }
//
//        int totalRowCount = empMapper2.getHistoryOfVacationOfMineTotalRow(pagingRequestWithIdStatusDto.getId(), pagingRequestWithIdStatusDto.getStatus()); // 전제 행
//        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
//        boolean isLastPage = (pagingRequestWithIdStatusDto.getCurrentPage() < lastPageNumber); // 마지막 페이지 여부
//
//        /* 생성자 이용해 Page 객체 생성, 리턴*/
//        Page<List<VacationRequestDto>> result = new Page<>(getData, isLastPage, pagingRequestWithIdStatusDto.getSort(), pagingRequestWithIdStatusDto.getSortOrder(), pagingRequestWithIdStatusDto.getCurrentPage(), totalRowCount);
//        return result;
//    }
//
//    @Override
//    public int getRemainOfVacationOfMine(String id) {
//        int year = empMapper2.getHireYear(id); //입사 연도 들고옴
//        log.info("getHireYear(employeeId) : {}", year);
//
//        int setting = empMapper2.getDefaultSettingVacationValue(year); //입사 연도에 따른 기본 연차 설정값 들고옴
//        log.info("empMapper2.getDefaultSettingVacationValue(year) : {}", setting);
//
//        /* 조정된 연차 개수를 들고 와서, 기본 연차 설정 값과 더한 결과*/
//        // 만약 조정된 것이 없다면? 0이 리턴되도록
//        int thisYearSettingVacationValue = setting + empMapper2.getVacationAdjustedHistory(id);
//        log.info("조정된 연차 개수 데이터 : {}", empMapper2.getVacationAdjustedHistory(id));
//        log.info("기본 연차 설정 값 + 조정된 연차 개수 데이터 : {}", thisYearSettingVacationValue);
//        //
//
//        /* 연차 신청 결과 승인인 튜플 중 vacation_quantity의 총합 */
//        int approveVacationSum = empMapper2.getApproveVacationQuantity(id);
//        log.info("올해 연차 승인 개수 합 : {}", approveVacationSum);
//
//        return thisYearSettingVacationValue - approveVacationSum; //기본값 + 조정값 - 승인 튜플 수
//    }
//
//
//}
