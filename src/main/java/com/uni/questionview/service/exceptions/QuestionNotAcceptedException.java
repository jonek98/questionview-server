package com.uni.questionview.service.exceptions;

public class QuestionNotAcceptedException extends RuntimeException {
    public QuestionNotAcceptedException(String message) {
        super(message);
    }
}