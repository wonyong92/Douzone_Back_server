package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper3.appeal.AppealMapper;
import com.example.bootproject.repository.mapper3.attendanceinfo.AttendanceInfoMapper;
import com.example.bootproject.service.service3.api.AppealService;
import com.example.bootproject.vo.vo1.request.appeal.AppealProcessRequestDto;
import com.example.bootproject.vo.vo1.request.appeal.AppealRequestDto;
import com.example.bootproject.vo.vo1.response.appeal.AppealRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.example.bootproject.system.StaticString.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {
    private final AttendanceInfoMapper attendanceInfoMapper;
    private final AppealMapper appealMapper;
    private final HttpSession session;
    /*
     *
     * 1. 로그인 정보 확인 - 로그인 확인 API 혹은 로직 사용
     * 2. post - form을 통해 전달 받은 데이터를 유효성 검증 수행
     * 3. 서비스로 입력 데이터 전달
     * 4. 서비스에서 attendance_info_id 에 대해서 조정 요청 튜플이 있는지 확인
     *     1. 분기점 1 : 기존 조정 요청이 있는 경우
     *         1. 중복된 요청임을 알리는 응답을 담은 응답을 전달
     *     2. 분기점 1 : 기존 조정 요청이 없는 경우
     *         1. 분기점 1 - A : attendance_info_id가 잘못된 경우
     *             - dto에 이미 요청이 있음을 담아서 전달
     *         2. 분기점 1 - A : 정상적인 attendance_info_id 인 경우
     *             - 신청 내역 테이블에 insert  수행
     *   - 최종 응답 : generated key를 이용하여 다시 신청 내역을 조회하여 ResponseDto에 담아서 전달
     */

    @Override
    public AppealRequestResponseDto makeAppealRequest(AppealRequestDto dto) {
        Integer attendanceInfoIdExist = attendanceInfoMapper.countByIdEmployeeId(dto.getAttendanceInfoId(), dto.getEmployeeId());
        if (attendanceInfoIdExist == null) {
            //TODO : 근태기록 자체를 찾지 못한 경우 응답 설정
            log.info("attendanceInfoId를 찾지 못함");
            return null;
        }
        if (appealMapper.checkAppealRequestExist(dto.getAttendanceInfoId()) != null) {
            //TODO : 해당 근태기록에 대해 이미 조정 요청이 있는 경우 처리
            appealMapper.makeRequest(dto);
            AppealRequestResponseDto result = appealMapper.findById(dto.getAttendanceInfoId());
            log.info("해당 근태 정보(id {})의 현재 상태를 조정 요청 중({})으로 변경", dto.getAttendanceInfoId(), APPEAL_REQUEST_STATE_REQUESTED);
            attendanceInfoMapper.updateAttendanceInfoStatus(APPEAL_REQUEST_STATE_REQUESTED, dto.getAttendanceInfoId());
            return result;
        }

        log.info("attendanceInfoId가 2개 이상 발생 - DB 이상 확인 필요");
        return null;
    }

    @Override
    public AppealRequestResponseDto processAppealRequest(AppealProcessRequestDto dto) {
        //        1. 사유의 데이터가 null인지 확인
//
//            ⇒ 사유의 데이터가 null : 응답 코드 400
//
//            ⇒ 사유의 데이터가 not null : 응답 코드 200
//
//        3. 가져온 데이터를 분석하여 연차 사용 신청 내역 테이블을 업데이트 하거나 생략
        /*결정이 거절인 경우*/
        AppealRequestResponseDto old = appealMapper.findById(dto.getAttendanceAppealRequestId());
        if (!old.getStatus().equals(VACATION_REQUEST_STATE_REQUESTED)) {
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
            appealMapper.process(dto);
            if (dto.getStatus().equals(VACATION_REQUEST_STATE_PERMITTED))
                dto.setReasonForRejection("permitted");
            AppealRequestResponseDto result = appealMapper.findById(dto.getAttendanceAppealRequestId());
            log.info("요청 처리 쓰기 수행 결과 {}", result);
            return result;
        }
        log.info("요청 번호로 요청을 찾을 수 없음");
        return null;
    }
}
