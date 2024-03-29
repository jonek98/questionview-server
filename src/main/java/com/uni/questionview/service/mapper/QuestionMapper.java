package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.repository.TagRepository;
import com.uni.questionview.service.dto.QuestionDTO;
import com.uni.questionview.service.dto.TagDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionMapper {


    public static QuestionDTO mapToQuestionDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else {
            List<TagDTO> tagDTOS = questionEntity.getTags().stream().map(TagMapper::mapToTagDTO).toList();
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
                    tagDTOS);

        }
    }

    public QuestionEntity mapQuestionDTOToQuestionEntity(QuestionDTO questionDTO) {
        List<TagEntity> questionTags = questionDTO.getTags()
                .stream()
                .map(TagMapper::mapToTagEntity).toList();

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
