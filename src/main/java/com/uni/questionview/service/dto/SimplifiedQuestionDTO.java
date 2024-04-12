package com.uni.questionview.service.dto;

import com.uni.questionview.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SimplifiedQuestionDTO {

    private long id;
    private String summary;
    private Language language;
    private int difficulty;
    private int timeEstimate;
    private double rating;
    private List<TagDTO> tags;
    boolean onUserList;
}
