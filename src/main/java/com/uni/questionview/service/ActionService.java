package com.uni.questionview.service;

import com.uni.questionview.domain.ActionType;
import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.repository.ActionRepository;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.exceptions.QuestionNotFoundException;
import com.uni.questionview.service.exceptions.UserNotFoundException;
import com.uni.questionview.service.mapper.ActionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;

    private final QuestionRepository questionRepository;

    private final UserService userService;

    public ActionDTO addAction(ActionDTO actionDTO) {
        User currentLoggedUser = getCurrentLoggedUser();

        QuestionEntity question = questionRepository.findById(actionDTO.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found: " + actionDTO.getQuestionId()));

        ActionEntity actionToSave = ActionEntity.builder()
                .actionType(actionDTO.getActionType())
                .question(question)
                .comment(actionDTO.getComment())
                .date(actionDTO.getDate())
                .user(currentLoggedUser)
                .build();

        return ActionMapper.mapToActionDTO(actionRepository.save(actionToSave));
    }

    public ActionEntity createAddQuestionAction(QuestionEntity question) {
        User user = getCurrentLoggedUser();

        return ActionEntity.builder()
                .actionType(ActionType.QUESTION_ADD)
                .date(new Timestamp(System.currentTimeMillis()))
                .comment("New question added")
                .question(question)
                .user(user)
                .build();
    }

    public ActionEntity createEditQuestionAction(QuestionEntity question) {
        User user = getCurrentLoggedUser();

        return ActionEntity.builder()
                .actionType(ActionType.QUESTION_EDIT)
                .date(new Timestamp(System.currentTimeMillis()))
                .comment("User "+ user.getLogin() +" has edited the question")
                .question(question)
                .user(user)
                .build();
    }

    private User getCurrentLoggedUser() {
        return userService.getUserWithAuthorities()
            .orElseThrow(() -> new UserNotFoundException("Current logged user not found"));
    }
}
