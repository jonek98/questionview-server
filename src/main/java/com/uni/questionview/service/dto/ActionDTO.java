package com.uni.questionview.service.dto;

import com.uni.questionview.domain.ActionType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ActionDTO {

    private ActionType actionType;
    private String comment;
    private long questionId;
    private UserDTO user;
}