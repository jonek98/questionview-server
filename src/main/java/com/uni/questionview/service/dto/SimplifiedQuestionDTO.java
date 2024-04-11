package com.uni.questionview.service.dto;

import com.uni.questionview.domain.Language;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
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