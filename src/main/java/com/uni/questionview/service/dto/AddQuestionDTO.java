package com.uni.questionview.service.dto;

import com.uni.questionview.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
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
