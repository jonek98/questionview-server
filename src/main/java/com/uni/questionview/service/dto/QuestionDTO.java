package com.uni.questionview.service.dto;

import com.uni.questionview.domain.Language;
import com.uni.questionview.domain.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class QuestionDTO {

    private long id;
    private String answerText;
    private String questionText;
    private int difficultyLevel;
    private String summary;
    private Language language;
    private int timeEstimate;
    private double rating;
    private List<TagDTO> tags;
    private List<ActionDTO> actions;
    private Status status;

}
