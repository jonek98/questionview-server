package com.uni.questionview.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RatingDTO {
    private long ratingId;
    private int rating;
    private String userName;
    private long questionId;
}
