package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.RatingEntity;
import com.uni.questionview.service.dto.RatingDTO;

import org.springframework.stereotype.Service;

public class RatingMapper {

    private RatingMapper() {}

    public static RatingDTO mapToDto(RatingEntity ratingEntity) {
        return RatingDTO.builder()
            .ratingId(ratingEntity.getId())
            .rating(ratingEntity.getRating())
            .questionId(ratingEntity.getQuestion().getId())
            .userName(ratingEntity.getUser().getLogin())
            .build();
    }
}
