package com.uni.questionview.service.exceptions;

public class UserAlreadyRatedQuestionException extends RuntimeException {

    public UserAlreadyRatedQuestionException(String message) {
        super(message);
    }
}
