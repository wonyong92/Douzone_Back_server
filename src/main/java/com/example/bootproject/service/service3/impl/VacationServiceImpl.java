package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.repository.mapper3.vacation.VacationMapper;
import com.example.bootproject.service.service1.EmployeeService;
import com.example.bootproject.service.service1.ManagerService1;
import com.example.bootproject.service.service3.api.VacationService;
import com.example.bootproject.vo.vo1.request.vacation.VacationAdjustRequestDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationProcessRequestDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationRequestDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationAdjustResponseDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.example.bootproject.system.StaticString.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VacationServiceImpl implements VacationService {

    private final VacationMapper vacationMapper;
    private final EmployeeMapper1 employeeMapper;
    private final ManagerService1 managerService2;
    private final EmployeeService employeeService;

    @Override
    public VacationRequestResponseDto makeVacationRequest(VacationRequestDto dto) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 시작 날짜부터 종료 날짜까지 하루 단위로 튜블 삽입하도록하여 쿼리를 작성하기 편하게
        Integer remain = getVacationRemain(dto.getEmployeeId());
        Integer requestQuantity = dto.getVacationQuantity();
        if (remain < requestQuantity) {
            // 잔여 개수를 초과한 요청 : dto에 잘못된 요청임을 담아서 전달
            log.info("잔여 개수 초과 요청 발생 ");
            return null;
        } else {
            LocalDate start = dto.getVacationStartDate();
            LocalDate end = dto.getVacationEndDate();
            boolean checkRequestExist = checkRequestExist(start, end, dto.getEmployeeId());
            if (checkRequestExist) {
                /*신청 내역이 있는 날짜에 대한 요청 발생 시*/
                log.info("날짜 겹칩 요청 발생");
                return null;
            } else {
                // 정상 동작 수행
                // 입력 데이터를 insert
                log.info("정상 연차 생성 요청 처리 진행");
                vacationMapper.addRequest(dto);
                if(dto.getVacationQuantity()>1)
                    for(int i = 1; i<dto.getVacationQuantity() ; i++){
                        dto.setVacationStartDate(dto.getVacationStartDate().plusDays(1));
                        vacationMapper.addAttendanceInfo(dto);
                    }
                else{
                    vacationMapper.addAttendanceInfo(dto);
                }
                long generatedKey = dto.getVacationRequestKey();
                VacationRequestResponseDto result = vacationMapper.findByVacationRequestKey(generatedKey);
                return result;
            }
        }
    }

    @Override
    public VacationRequestResponseDto processVacationRequest(VacationProcessRequestDto dto) {
        //        1. 사유의 데이터가 null인지 확인
//
//            ⇒ 사유의 데이터가 null : 응답 코드 400
//
//            ⇒ 사유의 데이터가 not null : 응답 코드 200
//
//        3. 가져온 데이터를 분석하여 연차 사용 신청 내역 테이블을 업데이트 하거나 생략
        /*결정이 거절인 경우*/
        VacationRequestResponseDto old = vacationMapper.findByVacationRequestKey(dto.getVacationRequestKey());
        if (!old.getVacationRequestStateCategoryKey().equals(VACATION_REQUEST_STATE_REQUESTED)) {
            log.info("이미 처리된 연차 요청은 처리 할 수 없습니다");
            return null;
        }

        log.info("{} {}", old.getEmployeeId(), dto.getEmployeeId());
        if (old.getEmployeeId().equals(dto.getEmployeeId())) {
            log.info("자신의 연차 요청은 처리 할 수 없습니다");
            return null;
        }
        /*요청 번호에 문제가 있는 경우 null 반환*/
        if (old != null) {
            log.info("요청 처리 쓰기 수행 - 기존 데이터 {}", old);
            if (dto.getVacationRequestStateCategoryKey().equals(VACATION_REQUEST_STATE_PERMITTED))
                dto.setReasonForRejection("승인됨");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
            }
            vacationMapper.process(dto);

            if(dto.getVacationRequestStateCategoryKey().equals(VACATION_REQUEST_STATUS_CATEGORY_REJECTED))
            {
                for(int i = 0; i<old.getVacationQuantity() ; i++){
                    old.setVacationStartDate(old.getVacationStartDate().plusDays(1));
                    vacationMapper.removeAttendanceInfo(old);
                }
            }
            VacationRequestResponseDto result = vacationMapper.findByVacationRequestKey(dto.getVacationRequestKey());
            log.info("요청 처리 쓰기 수행 결과 {}", result);
            return result;
        }
        log.info("요청 번호로 요청을 찾을 수 없음");
        return null;
    }

    @Override
    public VacationAdjustResponseDto modifyVacationOfEmployee(VacationAdjustRequestDto dto, String employeeId) {
//                - 분기점 1 : 타겟 employee_id로 사원을 찾을 수 없는 경우
//                - badRequest 응답을 위한 응답
//                - 분기점 1 : 저장 대상 사원을 찾은 경우
//                - vacation_adjusted_history에 데이터 insert
//        - generated key를 이용하여 재조회 후 vacationAdjustResponseDto에 담아서 응답 전달
        if (employeeMapper.checkEmployeeExist(employeeId) < 1) {
            return null;
        }
        vacationMapper.modifyVacationOfEmployee(dto, employeeId);
        VacationAdjustResponseDto result = vacationMapper.getModifyVacationOfEmployee(dto.getGeneratedKey());
        return result;
    }

    private Integer getVacationRemain(String employeeId) {
        int remain = employeeService.getRemainOfVacationOfMineForRequest(employeeId);
        log.info("getVacationRemain {} {} ", employeeId, remain);
        return remain;
    }

    private boolean checkRequestExist(LocalDate start, LocalDate end, String employeeId) {
        Integer countExist = vacationMapper.checkDataRegion(start, end, employeeId);
        return countExist >= 1;
    }
}
