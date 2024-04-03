package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.dto.QuestionDTO;
import com.uni.questionview.service.dto.SimplifiedQuestionDTO;
import com.uni.questionview.service.dto.TagDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionMapper {

    private final ActionMapper actionMapper;

    @Autowired
    public QuestionMapper(ActionMapper actionMapper) {
        this.actionMapper = actionMapper;
    }

    public QuestionDTO mapToQuestionDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else {
            List<TagDTO> tagDTOS = questionEntity.getTags().stream().map(TagMapper::mapToTagDTO).toList();
            List<ActionDTO> actionDTOS = questionEntity.getActions().stream().map(actionMapper::mapToActionDTO).toList();
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
                    tagDTOS,
                    actionDTOS);
        }
    }

    public SimplifiedQuestionDTO mapToSimplifiedQuestionDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else {
            List<TagDTO> tagDTOS = questionEntity.getTags().stream().map(TagMapper::mapToTagDTO).toList();
            return SimplifiedQuestionDTO.of(
                    questionEntity.getId(),
                    questionEntity.getSummary(),
                    questionEntity.getLanguage(),
                    questionEntity.getDifficultyLevel(),
                    questionEntity.getTimeEstimate(),
                    0, //TODO: dodac obsluge ratingu
                    tagDTOS);
        }
    }

    public QuestionEntity mapToQuestionEntity(QuestionDTO questionDTO) {
        List<TagEntity> questionTags = questionDTO.getTags()
                .stream()
                .map(TagMapper::mapToTagEntity).toList();

        List<ActionEntity> actionEntities = questionDTO.getActions()
                .stream()
                .map(actionMapper::mapToActionEntity)
                .toList();

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
        questionEntity.setActions(actionEntities);

        return questionEntity;
    }
}
