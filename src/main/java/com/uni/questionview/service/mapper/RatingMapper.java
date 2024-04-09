package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.domain.entity.RatingEntity;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.repository.UserRepository;
import com.uni.questionview.service.dto.RatingDTO;
import com.uni.questionview.service.exceptions.QuestionNotFoundException;
import com.uni.questionview.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingMapper {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Autowired
    public RatingMapper(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public RatingDTO mapToDto(RatingEntity ratingEntity) {
        return RatingDTO.builder()
            .ratingId(ratingEntity.getId())
            .rating(ratingEntity.getRating())
            .questionId(ratingEntity.getQuestion().getId())
            .userId(ratingEntity.getUser().getId())
            .build();
    }

    public RatingEntity mapToEntity(RatingDTO ratingDTO) {
        QuestionEntity questionEntity = questionRepository.findById(ratingDTO.getQuestionId())
            .orElseThrow(() -> new QuestionNotFoundException("Question with id not found: " + ratingDTO.getQuestionId()));

        User user = userRepository.findById(ratingDTO.getUserId())
            .orElseThrow(() -> new UserNotFoundException("User with id not found: " + ratingDTO.getUserId()));

        return RatingEntity.builder()
            .rating(ratingDTO.getRating())
            .question(questionEntity)
            .user(user)
            .build();
    }
}
