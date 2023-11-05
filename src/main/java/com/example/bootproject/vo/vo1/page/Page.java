package com.example.bootproject.vo.vo1.page;

import com.example.bootproject.vo.vo1.response.AttendanceApprovalUpdateResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class Page <T>{

    public static int PAGE_SIZE = 10;

    //제네릭으로 여러 데이터 타입을 처리
    private T data ;
    //사이즈는 10으로 고정
    private int size = PAGE_SIZE;
    //다음 페이지가 남았는지
    private boolean hasNext;
    //정렬 대상 컬럼 이름
    private String sort;
    //내림차순 적용 유무
    private String desc;
    //현재 페이지 번호
    private int page;
    //전체 요소 개수



    private int totalElement;
    public Page(T data, boolean isLastPage, String sort, String sortOrder, int currentPage, int totalElements) {
        this.data = data;
        this.hasNext = !isLastPage;
        this.sort = sort;
        this.desc = sortOrder;
        this.page = currentPage;
        this.totalElement = totalElements;
    }



}
