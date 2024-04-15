package com.uni.questionview.service.exceptions;

public class QuestionAlreadySubmittedException extends RuntimeException {
    public QuestionAlreadySubmittedException(String message) {
        super(message);
    }
}