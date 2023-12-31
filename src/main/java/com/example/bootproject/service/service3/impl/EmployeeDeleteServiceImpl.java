package com.example.bootproject.service.service3.impl;

import com.example.bootproject.controller.rest.admin.EmployeeListMapper;
import com.example.bootproject.controller.rest.admin.EmployeeSearchResponseDto;
import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.repository.mapper3.employee_delete.EmployeeIdDeleteAndBackupMapper;
import com.example.bootproject.service.service3.api.EmployeeDeleteService;
import com.example.bootproject.vo.vo1.request.PageRequest;
import com.example.bootproject.vo.vo1.response.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

import static com.example.bootproject.ServletInitializer.DELETE_PROCESS_LIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeDeleteServiceImpl implements EmployeeDeleteService {
    private final EmployeeMapper1 employeeMapper1;
    private final EmployeeIdDeleteAndBackupMapper employeeIdDeleteAndBackupMapper;
    private final PlatformTransactionManager transactionManager;
    private final EmployeeListMapper employeeListMapper;

    public boolean backUpDataAndDeleteEmployeeInformation(String employeeId) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            int backupResultOfBackupEmployeeTableEntity = employeeIdDeleteAndBackupMapper.backupEmployeeTableEntity(employeeId);
            int backupResultOfBackupAttendanceAppealRequestTableEntity = employeeIdDeleteAndBackupMapper.backupAttendanceAppealRequestTableEntity(employeeId);
            int backupResultOfBackupAttendanceApprovalTableEntity = employeeIdDeleteAndBackupMapper.backupAttendanceApprovalTableEntity(employeeId);
            int backupResultOfBackupAttendanceInfoTableEntity = employeeIdDeleteAndBackupMapper.backupAttendanceInfoTableEntity(employeeId);
            int backupResultOfBackupAuthTableEntity = employeeIdDeleteAndBackupMapper.backupAuthTableEntity(employeeId);
            int backupResultOfBackupNotificationMessageTableEntity = employeeIdDeleteAndBackupMapper.backupNotificationMessageTableEntity(employeeId);
            int backupResultOfBackupRegularTimeAdjustmentHistoryTableEntity = employeeIdDeleteAndBackupMapper.backupRegularTimeAdjustmentHistoryTableEntity(employeeId);
            int backupResultOfBackupVacationAdjustedHistoryTableEntity = employeeIdDeleteAndBackupMapper.backupVacationAdjustedHistoryTableEntity(employeeId);
            int backupResultOfBackupVacationQuantitySettingTableEntity = employeeIdDeleteAndBackupMapper.backupVacationQuantitySettingTableEntity(employeeId);
            int backupResultOfBackupVacationRequestTableEntity = employeeIdDeleteAndBackupMapper.backupVacationRequestTableEntity(employeeId);
            int result = backupResultOfBackupEmployeeTableEntity + backupResultOfBackupAttendanceAppealRequestTableEntity + backupResultOfBackupAttendanceApprovalTableEntity + backupResultOfBackupAttendanceInfoTableEntity + backupResultOfBackupAuthTableEntity + backupResultOfBackupNotificationMessageTableEntity + backupResultOfBackupRegularTimeAdjustmentHistoryTableEntity + backupResultOfBackupVacationAdjustedHistoryTableEntity + backupResultOfBackupVacationQuantitySettingTableEntity + backupResultOfBackupVacationRequestTableEntity;
            log.info("관련 테이블 백업 수행 결과 : "+result);
            employeeIdDeleteAndBackupMapper.deleteEmployeeByEmployeeId(employeeId);
            transactionManager.commit(transactionStatus);
            log.info("백업 및 삭제 수행 성공 - 커밋 수행");
            return true;
        }catch(Exception e){
                transactionManager.rollback(transactionStatus);
                log.info("사원 정보 백업 및 삭제 수행 도중 에러 발생 - 롤백 수행");
                return false;
        }
    }

    @Override
    public Page<List<String>> searchEmployeeNumbers(PageRequest pageRequest, String searchText) {
        int page = pageRequest.getPage();
        int size = Page.PAGE_SIZE;
        List<String> data = new ArrayList<>();
        if(searchText.trim().isEmpty()){
            data = employeeListMapper.getAllEmployeeNumbers(searchText,pageRequest,size);
        }else{
            data = employeeListMapper.searchEmployeeNumbers(searchText,pageRequest,size);
        }
        int totalElements = Integer.parseInt(data.get(data.size()-1));

        boolean hasNext = (page * size) < totalElements;
        String sort = pageRequest.getSort();
        String desc = pageRequest.getDesc();
        Page<List<String>> result = new Page<>(data.subList(0,data.size()-1), hasNext, sort, desc, page, totalElements);

        return result;
    }
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Page<List<String>> searchEmployeeNumbersAndEmployeeName(PageRequest pageRequest, String searchText,boolean isManager) {
        int page = pageRequest.getPage();
        int size = Page.PAGE_SIZE;

        List<String> data = new ArrayList<>();
        if((searchText!=null) && !searchText.trim().isEmpty()){
            if(pageRequest.getSort().trim().isEmpty()){
                pageRequest.setSort("employee_id");
            }
            log.info("특정 유저의 사원 번호 찾기 {}", searchText );
            data = employeeListMapper.findEmployeeNumbersAndEmployeeName(searchText,pageRequest,size,isManager);
            data = data.stream().filter(d -> !DELETE_PROCESS_LIST.contains(d)).toList();
        }else{
            log.info("전체 사원 번호 호출 ");
            if(pageRequest.getSort().trim().isEmpty()){
                pageRequest.setSort("employee_id");
            }
            data = employeeListMapper.getAllEmployeeNumbersAndEmployeeName(searchText,pageRequest,size,isManager);
            data = data.stream().filter(d -> !DELETE_PROCESS_LIST.contains(d)).toList();
        }
        int totalElements = Integer.parseInt(data.get(data.size()-1));

        boolean hasNext = (page * size) < totalElements;
        String sort = pageRequest.getSort();
        String desc = pageRequest.getDesc();
        Page<List<String>> result = new Page<>(data.subList(0,data.size()-1), hasNext, sort, desc, page, totalElements);

        return result;
    }

    @Override
    public Page<List<String>> getEmployeeNumbers(PageRequest pageRequest) {
        int page = pageRequest.getPage();
        int size = Page.PAGE_SIZE;
        List<String> data = employeeListMapper.getEmployeeNumbers(pageRequest,size);
        int totalElements = employeeListMapper.countAllEmployee();

        boolean hasNext = (page * size) < totalElements;
        String sort = pageRequest.getSort();
        String desc = pageRequest.getDesc();
        Page<List<String>> result = new Page<>(data, hasNext, sort, desc, page, totalElements);

        return result;
    }


}
