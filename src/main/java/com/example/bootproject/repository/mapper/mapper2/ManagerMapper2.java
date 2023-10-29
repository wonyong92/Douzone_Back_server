package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.vo.vo2.response.SettingWorkTimeDto;
import com.example.bootproject.vo.vo2.response.VacationQuantitySettingDto;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ManagerMapper2 {


    /* vacation_request 테이블의 vacation_request_time 컬럼의 값을 '%Y-%m-%d'로 변환 하고,
       변환 값이 매개변수 date와 일치하는 튜플만 vacation_request 테이블에서 select 함
    */
    @Select("SELECT* FROM vacation_request WHERE DATE_FORMAT(vacation_request_time,'%Y-%m-%d') =#{date};")
    public List<VacationRequestDto> getAllVacationHistory(String date);

    // 특정 사원번호를 가진 사원의 데이터를 select 함
    @Select("SELECT* FROM vacation_request WHERE employee_id=#{employeeId}")
    public VacationRequestDto getEmpReqVacationHistory(String employeeId);

    //정규 근무시간 조정내역 테이블의 전체 정보를 select 함
    @Select("SELECT* FROM regular_time_adjustment_history;")
    public List<SettingWorkTimeDto> getSettingWorkTime();

    // 연차별 사원 연차 개수 설정 테이블의 전체 정보를 select 함
    @Select("SELECT* FROM vacation_quantity_setting")
    public List<VacationQuantitySettingDto> getVacationSettingHistory();

    //vacation_request 테이블에서 특정 사원의 result 컬럼의 값이 '반려'인 데이터를 select함
    @Select("SELECT * FROM vacation_request WHERE employee_id=#{employeeId} and result='반려';")
    public List<VacationRequestDto> getHistoryOfRejectedVacationOfEmployee(String employeeId);

}
