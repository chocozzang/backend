package com.mysite.backend.exception;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long id) {
        super("id에 맞는 유저가 없습니다. " + id);
    }
}
