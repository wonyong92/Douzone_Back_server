package com.example.bootproject.vo.vo1.error;

import lombok.Data;

@Data
public class ErrorResponseDto<T extends Throwable> {
    private T exception;
    private String errorMessage;
}
