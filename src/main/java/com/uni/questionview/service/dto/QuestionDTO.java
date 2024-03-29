package com.uni.questionview.service.dto;

import com.uni.questionview.domain.Language;
import com.uni.questionview.domain.Status;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class QuestionDTO {

    private long id;

    private String answerText;

    private String questionText;

    private int difficultyLevel;

    private Status status;

    private String statusChaneReason;

    private String summary;

    private Language language;

    private int timeEstimate;

    private List<TagDTO> tags;

}
