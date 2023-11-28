package com.example.bootproject.system.controlleradvice;


import com.example.bootproject.vo.vo1.error.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTransientConnectionException;

@RestControllerAdvice
@Slf4j
public class RestControllerImpl {
    @ExceptionHandler(SQLTransientConnectionException.class)
    public ResponseEntity<ErrorResponseDto<SQLTransientConnectionException>> DbConnectionFailExceptionHandler(SQLTransientConnectionException ex, HttpServletRequest req) {
        log.info("exception occur {} ", ex.getClass().getName());
        log.info("stackTrace");
        ex.printStackTrace();
        log.info(ex.getClass().getName() + " Exception occur {} ", "데이터베이스가 꺼져있거나 연결이 불가능한 상태이므로 연결 설정을 확인 하세요");
        ErrorResponseDto<SQLTransientConnectionException> responseDto = new ErrorResponseDto<>();
        responseDto.setException(ex);
        responseDto.setErrorMessage("데이터베이스가 꺼져있거나 연결이 불가능한 상태이므로 연결 설정을 확인 하세요");
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<ErrorResponseDto<SQLSyntaxErrorException>> SQLSyntaxErrorExceptionHandler(SQLSyntaxErrorException ex, HttpServletRequest req) {
        log.info("exception occur {} ", ex.getClass().getName());
        log.info("stackTrace");
        ex.printStackTrace();
        log.info(ex.getClass().getName() + " Exception occur {} ", "쿼리 수행 도중 문법 에러 발생, 입력값을 확인하세요");
        ErrorResponseDto<SQLSyntaxErrorException> responseDto = new ErrorResponseDto<>();
        responseDto.setException(ex);
        responseDto.setErrorMessage("쿼리 수행 도중 문법 에러 발생, 입력값을 확인하세요");
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponseDto<SQLException>> sqlExceptionHandler(SQLException ex, HttpServletRequest req) {
        log.info("exception occur {} ", ex.getClass().getName());
        log.info("stackTrace");
        ex.printStackTrace();
        log.info(ex.getClass().getName() + " Exception occur {} ", "쿼리문에 문제가 있거나 입력 데이터에 문제가 있으니 확인 필요");
        ErrorResponseDto<SQLException> responseDto = new ErrorResponseDto<>();
        responseDto.setException(ex);
        responseDto.setErrorMessage("\"쿼리문에 문제가 있거나 입력 데이터에 문제가 있으니 확인 필요\"");
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto<NoHandlerFoundException>> handleNoHandlerFoundExceptionException(NoHandlerFoundException ex, HttpServletRequest req) {
        log.info("exception occur {} ", ex.getClass().getName());
        log.info("stackTrace");
        ex.printStackTrace();
        log.info("not found request uri {} ", req.getRequestURL());
        ErrorResponseDto<NoHandlerFoundException> responseDto = new ErrorResponseDto<>();
        responseDto.setException(ex);
        responseDto.setErrorMessage("HandlerNotFound");
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto<Exception>> GeneralExceptionException(Exception ex, HttpServletRequest req) {
        log.info("exception occur {} ", ex.getClass().getName());
        log.info("stackTrace");
        ex.printStackTrace();
        log.info("request uri {} ", req.getRequestURL());
        ErrorResponseDto<Exception> responseDto = new ErrorResponseDto<>();
        responseDto.setException(ex);
        responseDto.setErrorMessage("unhandled Exception occur");
        return ResponseEntity.internalServerError().build();
    }
}
