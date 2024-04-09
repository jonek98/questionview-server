package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.repository.UserRepository;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.dto.UserDTO;
import com.uni.questionview.service.exceptions.QuestionNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionMapper {

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    @Autowired
    public ActionMapper(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public ActionDTO mapToActionDTO(ActionEntity actionEntity) {
        UserDTO userDTO = UserMapper.userToUserDTO(actionEntity.getUser());

        return ActionDTO.of(
                actionEntity.getActionType(),
                actionEntity.getComment(),
                actionEntity.getDate(),
                actionEntity.getQuestion().getId(),
                userDTO);
    }

    public ActionEntity mapToActionEntity(ActionDTO actionDTO) {
        User user = userRepository.findOneByLogin(actionDTO.getUser().getLogin()).orElseThrow();
        QuestionEntity questionEntity = questionRepository.findById(actionDTO.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found: " + actionDTO.getQuestionId()));

        return ActionEntity.builder()
                .actionType(actionDTO.getActionType())
                .question(questionEntity)
                .comment(actionDTO.getComment())
                .date(actionDTO.getDate())
                .user(user)
                .build();
    }
}