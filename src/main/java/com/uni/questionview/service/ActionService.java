package com.uni.questionview.service;

import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.repository.ActionRepository;
import com.uni.questionview.repository.UserRepository;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.dto.UserDTO;
import com.uni.questionview.service.exceptions.UserNotFoundException;
import com.uni.questionview.service.mapper.ActionMapper;
import com.uni.questionview.service.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

    private final ActionRepository actionRepository;

    private final ActionMapper actionMapper;

    private final UserRepository userRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository, ActionMapper actionMapper, UserRepository userRepository) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
        this.userRepository = userRepository;
    }

    public ActionDTO addAction(ActionDTO actionDTO) {
        User user = userRepository.findOneByLogin(actionDTO.getUser().getLogin())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserDTO userDTO = UserMapper.userToUserDTO(user);

        ActionEntity actionToSave = actionMapper.mapToActionEntity(actionDTO.withUser(userDTO));

        return actionMapper.mapToActionDTO(actionRepository.save(actionToSave));
    }
}