package com.uni.questionview.service.exceptions;

public class QuestionAlreadyOnUserList extends RuntimeException {

    public QuestionAlreadyOnUserList(String message) {
        super(message);
    }
}
