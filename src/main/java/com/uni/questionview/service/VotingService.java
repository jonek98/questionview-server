package com.uni.questionview.service;

import com.uni.questionview.domain.Status;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.repository.ActionRepository;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.exceptions.QuestionNotFoundException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VotingService {

    private final QuestionRepository questionRepository;
    private final ActionRepository actionRepository;
    private final ActionService actionService;

    private static final int NUMBER_OF_ACCEPTANCE_VOTES = 3;
    private static final int NUMBER_OF_REJECTION_VOTES = 3;
    private static final int NUMBER_OF_CORRECTION_VOTES = 1;



    public int voteForQuestion(ActionDTO action) {
        QuestionEntity questionEntity = questionRepository.findById(action.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Question with id: "+ action.getQuestionId() +" not found!"));

        switch (action.getActionType()) {
            case QUESTION_ACCEPT -> {return voteForQuestionAcceptation(questionEntity);}
            case QUESTION_REJECT -> {return voteForQuestionRejection(questionEntity);}
            case QUESTION_NEEDS_CORRECTION -> {return voteForQuestionCorrections(questionEntity, action.getComment());}
            default -> throw new IllegalArgumentException("Unsupported action type: " + action.getActionType());
        }
    }

    private int voteForQuestionAcceptation(QuestionEntity questionEntity) {
        ActionEntity savedAcceptAction = actionRepository.save(actionService.createAcceptQuestionAction(questionEntity));

        questionEntity.getActions().add(savedAcceptAction);

        if (questionEntity.countNumberOfAcceptVotes() == NUMBER_OF_ACCEPTANCE_VOTES)
            questionRepository.save(questionEntity.withStatus(Status.ACCEPTED));

        return questionEntity.countNumberOfAcceptVotes();
    }

    private int voteForQuestionRejection(QuestionEntity questionEntity) {
        ActionEntity savedRejectAction = actionRepository.save(actionService.createRejectQuestionAction(questionEntity));

        questionEntity.getActions().add(savedRejectAction);

        if (questionEntity.countNumberOfRejectVotes() == NUMBER_OF_REJECTION_VOTES)
            questionRepository.save(questionEntity.withStatus(Status.REJECTED));

        return questionEntity.countNumberOfAcceptVotes();
    }

    private int voteForQuestionCorrections(QuestionEntity questionEntity, String correctionComment) {
        ActionEntity savedRejectAction = actionRepository.save(actionService.createNeedCorrectionQuestionAction(questionEntity, correctionComment));

        questionEntity.getActions().add(savedRejectAction);

        if (questionEntity.countNumberOfRejectVotes() == NUMBER_OF_CORRECTION_VOTES)
            questionRepository.save(questionEntity.withStatus(Status.NEEDS_CORRECTIONS));

        return questionEntity.countNumberOfNeedCorrectionsVotes();
    }
}