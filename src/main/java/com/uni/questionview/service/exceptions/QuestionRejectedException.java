package com.uni.questionview.service.exceptions;

public class QuestionRejectedException extends RuntimeException {
    public QuestionRejectedException(String message) {
        super(message);
    }
}