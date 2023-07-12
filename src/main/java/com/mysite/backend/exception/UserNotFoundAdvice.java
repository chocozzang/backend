package com.mysite.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

// 모든 컨트롤러에 적용시킬 수 있는 어노테이션
@ControllerAdvice
public class UserNotFoundAdvice {

    @ResponseBody // 반환할 때 JSON 형태로 반환할 수 있도록 하는 어노테이션
    @ResponseStatus(HttpStatus.NOT_FOUND) // NOT_FOUND 에러를 보내줌
    @ExceptionHandler(UserNotFoundException.class) // 괄호 안의 예외가 발생하면, 이 메소드에서 그 예외를 처리할 수 있도록 하는 어노테이션
    public Map<String, String> exceptionHandler(UserNotFoundException userNotFoundException) {
        Map<String ,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", userNotFoundException.getMessage());
        return errorMap;
    }
}
