package com.uni.questionview.service.dto;

import com.uni.questionview.domain.ActionType;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

@Data
@AllArgsConstructor(staticName = "of")
public class ActionDTO {

    private ActionType actionType;
    private String comment;
    private Timestamp date;
    private long questionId;
    @With
    private UserDTO user;
}