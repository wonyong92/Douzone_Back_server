package com.example.bootproject.system.validator;

import com.example.bootproject.vo.vo1.request.PageRequest;
import com.example.bootproject.vo.vo1.request.PagedLocalDateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class PagedLocalDateDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("supports : {}", PagedLocalDateDto.class.equals(clazz));
        return PagedLocalDateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("validate 수행");
        PagedLocalDateDto myRequestDTO = (PagedLocalDateDto) target;

        // 필드 유효성 검사 규칙 정의
//        if (!myRequestDTO.getDesc().equalsIgnoreCase("ASC") || !myRequestDTO.getDesc().equalsIgnoreCase("DESC")) {
//            errors.rejectValue("desc", "desc.wrong", "desc should be asc(ASC) or desc(DESC)");
//        }
//
//        if (!myRequestDTO.getDesc().equalsIgnoreCase("ASC") || !myRequestDTO.getDesc().equalsIgnoreCase("DESC")) {
//            errors.rejectValue("desc", "desc.wrong", "desc should be asc(ASC) or desc(DESC)");
//        }
//
//
//        if (!myRequestDTO.getDesc().equalsIgnoreCase("ASC") || !myRequestDTO.getDesc().equalsIgnoreCase("DESC")) {
//            errors.rejectValue("desc", "desc.wrong", "desc should be asc(ASC) or desc(DESC)");
//        }

//        // 'page' 파라미터 유효성 검증
//        if (page < 1) {
//            log.error("잘못된 페이지 번호: {}", page);
//            return ResponseEntity.badRequest().build();
//        }
//
//        // 'sort' 파라미터 유효성 검증
//        if ((sort.equals("attendance_approval_date") || sort.equals("anotherField"))) {
//            log.error("잘못된 정렬 필드: {}", sort);
//            return ResponseEntity.badRequest().build();
//        }
//
//        // 'desc' 파라미터 유효성 검증
//        if (!(desc.equalsIgnoreCase("ASC") || desc.equalsIgnoreCase("DESC"))) {
//            log.error("잘못된 정렬 방향: {}", desc);
//            return ResponseEntity.badRequest().build();
//        }

        if (myRequestDTO.getPage() < 1) {
            errors.rejectValue("page", "page.wrong", "page should be above 1");
        }

        if (!myRequestDTO.getDesc().equalsIgnoreCase("ASC") || !myRequestDTO.getDesc().equalsIgnoreCase("DESC")) {
            errors.rejectValue("desc", "desc.wrong", "desc should be asc(ASC) or desc(DESC)");
        }


    }
}