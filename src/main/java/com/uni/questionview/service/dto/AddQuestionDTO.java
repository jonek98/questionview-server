package com.uni.questionview.service.dto;

import com.uni.questionview.domain.Language;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class AddQuestionDTO {

    private Long id;

    private String answerText;

    private String questionText;

    private int difficultyLevel;

    private String summary;

    private Language language;

    private int timeEstimate;

    private List<Long> tagIds;
}