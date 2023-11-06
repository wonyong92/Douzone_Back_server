package com.example.bootproject.vo.vo2.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingRequestDto {
    private int currentPage; // 현재 페이지
    private String sort; // 정렬 기준 컬럼
    private String sortOrder; // 내림차순, 오름차순 결정
}