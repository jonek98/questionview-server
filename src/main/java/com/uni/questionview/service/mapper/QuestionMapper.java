package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.repository.UserRepository;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.dto.QuestionDTO;
import com.uni.questionview.service.dto.QuestionDetailsDTO;
import com.uni.questionview.service.dto.SimplifiedQuestionDTO;
import com.uni.questionview.service.dto.TagDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionMapper {

    private final ActionMapper actionMapper;

    private final TagMapper tagMapper;

    private final UserRepository userRepository;

    @Autowired
    public QuestionMapper(ActionMapper actionMapper, TagMapper tagMapper, UserRepository userRepository) {
        this.actionMapper = actionMapper;
        this.tagMapper = tagMapper;
        this.userRepository = userRepository;
    }

    public QuestionDTO mapToQuestionDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else {
            List<TagDTO> tagDTOS = questionEntity.getTags().stream().map(tagMapper::mapToTagDTO).toList();
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
                    questionEntity.calculateRating(),
                    tagDTOS,
                    actionDTOS);
        }
    }

    public QuestionDetailsDTO mapToQuestionDetailsDTO(QuestionEntity questionEntity, String currentUserName) {
        User user = userRepository.findOneByLogin(currentUserName)
                .orElseThrow(() -> new RuntimeException("User with userName: " + currentUserName +" not found"));

        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else {
            List<TagDTO> tagDTOS = questionEntity.getTags().stream().map(tagMapper::mapToTagDTO).toList();
            List<ActionDTO> actionDTOS = questionEntity.getActions().stream().map(actionMapper::mapToActionDTO).toList();
            return QuestionDetailsDTO.of(
                    questionEntity.getId(),
                    questionEntity.getAnswerText(),
                    questionEntity.getQuestionText(),
                    questionEntity.getDifficultyLevel(),
                    questionEntity.getStatus(),
                    questionEntity.getStatusChaneReason(),
                    questionEntity.getSummary(),
                    questionEntity.getLanguage(),
                    questionEntity.getTimeEstimate(),
                    questionEntity.calculateRating(),
                    tagDTOS,
                    actionDTOS,
                    questionEntity.checkIfQuestionIsOnUserList(user.getId()));
        }
    }

    public SimplifiedQuestionDTO mapToSimplifiedQuestionDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else {
            List<TagDTO> tagDTOS = questionEntity.getTags().stream().map(tagMapper::mapToTagDTO).toList();
            return SimplifiedQuestionDTO.of(
                    questionEntity.getId(),
                    questionEntity.getSummary(),
                    questionEntity.getLanguage(),
                    questionEntity.getDifficultyLevel(),
                    questionEntity.getTimeEstimate(),
                    questionEntity.calculateRating(),
                    tagDTOS);
        }
    }

    public QuestionEntity mapToQuestionEntity(QuestionDTO questionDTO) {
        List<TagEntity> questionTags = questionDTO.getTags()
                .stream()
                .map(tagMapper::mapToTagEntity).toList();

        List<ActionEntity> actionEntities = questionDTO.getActions()
                .stream()
                .map(actionMapper::mapToActionEntity)
                .toList();

        return QuestionEntity.builder()
                .answerText(questionDTO.getAnswerText())
                .questionText(questionDTO.getQuestionText())
                .difficultyLevel(questionDTO.getDifficultyLevel())
                .status(questionDTO.getStatus())
                .statusChaneReason(questionDTO.getStatusChaneReason())
                .summary(questionDTO.getSummary())
                .language(questionDTO.getLanguage())
                .timeEstimate(questionDTO.getTimeEstimate())
                .tags(questionTags)
                .actions(actionEntities)
                .build();
    }
}
