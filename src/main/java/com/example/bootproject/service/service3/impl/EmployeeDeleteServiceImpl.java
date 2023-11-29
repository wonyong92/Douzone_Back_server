package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.repository.mapper3.employee_delete.EmployeeIdDeleteAndBackupMapper;
import com.example.bootproject.service.service3.api.EmployeeDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeDeleteServiceImpl implements EmployeeDeleteService {
    private final EmployeeMapper1 employeeMapper1;
    private final EmployeeIdDeleteAndBackupMapper employeeIdDeleteAndBackupMapper;
    private final PlatformTransactionManager transactionManager;

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
}
