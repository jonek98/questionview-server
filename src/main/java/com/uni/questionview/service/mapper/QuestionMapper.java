package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.Authority;
import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.service.dto.AdminUserDTO;
import com.uni.questionview.service.dto.QuestionDTO;

import java.util.Set;

public class QuestionMapper {

    public static QuestionDTO mapQuestionEntityToQuestionDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else
            return QuestionDTO.of(
                    questionEntity.getAnswerText(),
                    questionEntity.getQuestionText(),
                    questionEntity.getDifficultyLevel(),
                    questionEntity.getStatus(),
                    questionEntity.getStatusChaneReason(),
                    questionEntity.getSummary(),
                    questionEntity.getLanguage(),
                    questionEntity.getTimeEstimate(),
                    questionEntity.getTags(),
                    questionEntity.getActions());
    }

    public static QuestionEntity mapQuestionDTOToQuestionEntity(QuestionDTO questionDTO) {
        if (questionDTO == null)
            throw new NullPointerException("QuestionDTO is null!");
        else {
            QuestionEntity questionEntity = new QuestionEntity();
            questionEntity.setAnswerText(questionDTO.getAnswerText());
            questionEntity.setQuestionText(questionDTO.getQuestionText());
            questionEntity.setDifficultyLevel(questionDTO.getDifficultyLevel());
            questionEntity.setStatus(questionDTO.getStatus());
            questionEntity.setStatusChaneReason(questionDTO.getStatusChaneReason());
            questionEntity.setSummary(questionDTO.getSummary());
            questionEntity.setLanguage(questionDTO.getLanguage());
            questionEntity.setTimeEstimate(questionDTO.getTimeEstimate());
            questionEntity.setTags(questionDTO.getTags());
            questionEntity.setActions(questionDTO.getActions());
            return questionEntity;
        }
    }
}
