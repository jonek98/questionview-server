package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.dto.UserDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ActionMapper {

    public static ActionDTO mapToActionDTO(ActionEntity actionEntity) {
        UserDTO userDTO = UserMapper.userToUserDTO(actionEntity.getUser());

        return ActionDTO.of(
                actionEntity.getActionType(),
                actionEntity.getComment(),
                actionEntity.getDate(),
                actionEntity.getQuestion().getId(),
                userDTO);
    }
}
