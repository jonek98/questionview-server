package com.uni.questionview.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uni.questionview.domain.ActionType;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

@Data
@AllArgsConstructor(staticName = "of")
public class ActionDTO {

    private ActionType actionType;
    private String comment;
    private LocalDate date;
    private long questionId;
    @With
    private UserDTO user;
}