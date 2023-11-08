package com.example.bootproject.vo.vo3.response;

import lombok.Data;
import lombok.NoArgsConstructor;

//        페이지네이션을 위한 쿼리 파라미터 목록 : page=int, sort=string, desc=boolean
@Data
@NoArgsConstructor
public class Page<T> {
    public static int PAGE_SIZE = 10;

    //제네릭으로 여러 데이터 타입을 처리
    private T data;
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

    public Page(T data, boolean hasNext, String sort, String desc, int page, int totalElement) {
        this.data = data;
        this.hasNext = hasNext;
        this.sort = sort;
        this.desc = desc;
        this.page = page;
        this.totalElement = totalElement;
    }


}
