package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper3.vacation.VacationMapper;
import com.example.bootproject.service.service3.api.VacationService;
import com.example.bootproject.vo.vo3.request.vacation.VacationRequestDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VacationServiceImpl implements VacationService {

    private final VacationMapper vacationMapper;

    @Override
    public VacationRequestResponseDto makeVacationRequest(VacationRequestDto dto) {
        // 시작 날짜부터 종료 날짜까지 하루 단위로 튜블 삽입하도록하여 쿼리를 작성하기 편하게
        Integer remain = getVacationRemain();
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
                long generatedKey = dto.getVacationRequestKey();
                VacationRequestResponseDto result = vacationMapper.findByVacationRequestKey(generatedKey);
                return result;
            }
        }
    }

    private Integer getVacationRemain() {
        return 3;
    }

    private boolean checkRequestExist(LocalDate start, LocalDate end, String employeeId) {
        Integer countExist = vacationMapper.checkDataRegion(start, end, employeeId);
        return countExist >= 1;
    }
}
