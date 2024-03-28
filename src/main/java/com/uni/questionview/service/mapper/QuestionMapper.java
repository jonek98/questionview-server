package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.repository.TagRepository;
import com.uni.questionview.service.dto.QuestionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionMapper {

    private final TagRepository tagRepository;

    @Autowired
    public QuestionMapper(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public static QuestionDTO mapQuestionEntityToQuestionDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else
            return QuestionDTO.of(
                    questionEntity.getId(),
                    questionEntity.getAnswerText(),
                    questionEntity.getQuestionText(),
                    questionEntity.getDifficultyLevel(),
                    questionEntity.getStatus(),
                    questionEntity.getStatusChaneReason(),
                    questionEntity.getSummary(),
                    questionEntity.getLanguage(),
                    questionEntity.getTimeEstimate(),
                    questionEntity.getTagIds());
    }

    public QuestionEntity mapQuestionDTOToQuestionEntity(QuestionDTO questionDTO) {
        List<TagEntity> questionTags = tagRepository.findByIdIn(questionDTO.getTagIds());

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setAnswerText(questionDTO.getAnswerText());
        questionEntity.setQuestionText(questionDTO.getQuestionText());
        questionEntity.setDifficultyLevel(questionDTO.getDifficultyLevel());
        questionEntity.setStatus(questionDTO.getStatus());
        questionEntity.setStatusChaneReason(questionDTO.getStatusChaneReason());
        questionEntity.setSummary(questionDTO.getSummary());
        questionEntity.setLanguage(questionDTO.getLanguage());
        questionEntity.setTimeEstimate(questionDTO.getTimeEstimate());
        questionEntity.setTags(questionTags);

        return questionEntity;

        }
}
