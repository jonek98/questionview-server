package com.uni.questionview.repository;

import com.uni.questionview.domain.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    Boolean existsByUserIdAndQuestionId(Long userId, Long questionId);
}
