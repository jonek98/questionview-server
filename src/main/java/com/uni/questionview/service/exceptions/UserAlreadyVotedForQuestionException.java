package com.uni.questionview.service.exceptions;

public class UserAlreadyVotedForQuestionException extends RuntimeException {

    public UserAlreadyVotedForQuestionException(String message) {
        super(message);
    }
}
