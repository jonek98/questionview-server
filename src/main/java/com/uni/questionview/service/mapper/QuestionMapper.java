package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.service.UserService;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.dto.QuestionDTO;
import com.uni.questionview.service.dto.QuestionDetailsDTO;
import com.uni.questionview.service.dto.SimplifiedQuestionDTO;
import com.uni.questionview.service.dto.TagDTO;
import com.uni.questionview.service.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionMapper {

    private final UserService userService;

    @Autowired
    public QuestionMapper(UserService userService) {
        this.userService = userService;
    }

    public QuestionDTO mapToQuestionDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else {
            List<TagDTO> tagDTOS = questionEntity.getTags().stream().map(TagMapper::mapToTagDTO).toList();
            List<ActionDTO> actionDTOS = questionEntity.getActions().stream().map(ActionMapper::mapToActionDTO).toList();
            return QuestionDTO.of(
                    questionEntity.getId(),
                    questionEntity.getAnswerText(),
                    questionEntity.getQuestionText(),
                    questionEntity.getDifficultyLevel(),
                    questionEntity.getSummary(),
                    questionEntity.getLanguage(),
                    questionEntity.getTimeEstimate(),
                    questionEntity.calculateRating(),
                    tagDTOS,
                    actionDTOS);
        }
    }

    public QuestionDetailsDTO mapToQuestionDetailsDTO(QuestionEntity questionEntity) {
        if (questionEntity == null)
            throw new NullPointerException("QuestionEntity is null!");
        else {
            List<TagDTO> tagDTOS = questionEntity.getTags().stream().map(TagMapper::mapToTagDTO).toList();
            List<ActionDTO> actionDTOS = questionEntity.getActions().stream().map(ActionMapper::mapToActionDTO).toList();
            return QuestionDetailsDTO.of(
                    questionEntity.getId(),
                    questionEntity.getAnswerText(),
                    questionEntity.getQuestionText(),
                    questionEntity.getDifficultyLevel(),
                    questionEntity.getSummary(),
                    questionEntity.getLanguage(),
                    questionEntity.getTimeEstimate(),
                    questionEntity.calculateRating(),
                    tagDTOS,
                    actionDTOS,
                    questionEntity.checkIfQuestionIsOnUserList(getCurrentLoggedUser().getId()));
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
                    questionEntity.calculateRating(),
                    tagDTOS,
                    questionEntity.checkIfQuestionIsOnUserList(getCurrentLoggedUser().getId()));
        }
    }

    private User getCurrentLoggedUser() {
        return userService.getUserWithAuthorities()
                .orElseThrow(() -> new UserNotFoundException("Current logged user not found!"));
    }
}
