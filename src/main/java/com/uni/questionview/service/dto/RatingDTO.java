package com.uni.questionview.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Builder
@Data
public class RatingDTO {
    private long ratingId;
    private int rating;
    @With
    private String userName;
    private long questionId;
}
