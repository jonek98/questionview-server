package com.uni.questionview.service;

import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.repository.ActionRepository;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.mapper.ActionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

    private final ActionRepository actionRepository;

    private final ActionMapper actionMapper;

    @Autowired
    public ActionService(ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    public ActionDTO addAction(ActionDTO actionDTO) {
        ActionEntity actionToSave = actionMapper.mapToActionEntity(actionDTO);

        return actionMapper.mapToActionDTO(actionRepository.save(actionToSave));
    }
}