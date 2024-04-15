package com.uni.questionview.service.dto;

import com.uni.questionview.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class QuestionDetailsDTO {

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
    private boolean isOnUserList;
}
