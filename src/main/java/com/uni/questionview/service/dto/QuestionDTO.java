package com.uni.questionview.service.dto;

import com.uni.questionview.domain.Language;
import com.uni.questionview.domain.Status;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class QuestionDTO {

    private String answerText;

    private String questionText;

    private int difficultyLevel;

    private Status status;

    private String statusChaneReason;

    private String summary;

    private Language language;

    private int timeEstimate;

    private Set<TagEntity> tags;

    private Set<ActionEntity> actions;

}
