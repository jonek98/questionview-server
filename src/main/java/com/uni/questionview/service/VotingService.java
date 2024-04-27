package com.uni.questionview.service;

import com.uni.questionview.domain.ActionType;
import com.uni.questionview.domain.Status;
import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.repository.ActionRepository;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.dto.VoteStatus;
import com.uni.questionview.service.exceptions.QuestionNotFoundException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.Set;

@Service
@AllArgsConstructor
public class VotingService {

    private final QuestionRepository questionRepository;
    private final ActionRepository actionRepository;
    private final ActionService actionService;
    private final UserService userService;

    private static final int NUMBER_OF_ACCEPTANCE_VOTES = 3;
    private static final int NUMBER_OF_REJECTION_VOTES = 3;
    private static final int NUMBER_OF_CORRECTION_VOTES = 1;



    public VoteStatus voteForQuestion(ActionDTO action) {
        QuestionEntity questionEntity = questionRepository.findById(action.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Question with id: "+ action.getQuestionId() +" not found!"));

//        if (isUserAlreadyVoted(questionEntity))
//            throw new UserAlreadyVotedForQuestionException("User "+ userService.getUserWithAuthorities().get().getLogin() +
//                " already voted for question with id: "+ questionEntity.getId());

        switch (action.getActionType()) {
            case QUESTION_ACCEPT -> {return voteForQuestionAcceptation(questionEntity);}
            case QUESTION_REJECT -> {return voteForQuestionRejection(questionEntity);}
            case QUESTION_NEEDS_CORRECTION -> {return voteForQuestionCorrections(questionEntity, action.getComment());}
            default -> throw new IllegalArgumentException("Unsupported action type: " + action.getActionType());
        }
    }

    private VoteStatus voteForQuestionAcceptation(QuestionEntity questionEntity) {
        ActionEntity savedAcceptAction = actionRepository.save(actionService.createAcceptQuestionAction(questionEntity));

        questionEntity.getActions().add(savedAcceptAction);

        if (questionEntity.countNumberOfAcceptVotes() == NUMBER_OF_ACCEPTANCE_VOTES)
            questionRepository.save(questionEntity.withStatus(Status.ACCEPTED));

        return questionEntity.getVoteStatus();
    }

    private VoteStatus voteForQuestionRejection(QuestionEntity questionEntity) {
        ActionEntity savedRejectAction = actionRepository.save(actionService.createRejectQuestionAction(questionEntity));

        questionEntity.getActions().add(savedRejectAction);

        if (questionEntity.countNumberOfRejectVotes() == NUMBER_OF_REJECTION_VOTES)
            questionRepository.save(questionEntity.withStatus(Status.REJECTED));

        return questionEntity.getVoteStatus();
    }

    private VoteStatus voteForQuestionCorrections(QuestionEntity questionEntity, String correctionComment) {
        ActionEntity savedRejectAction = actionRepository.save(actionService.createNeedCorrectionQuestionAction(questionEntity, correctionComment));

        questionEntity.getActions().add(savedRejectAction);

        if (questionEntity.countNumberOfNeedCorrectionsVotes() == NUMBER_OF_CORRECTION_VOTES)
            questionRepository.save(questionEntity.withStatus(Status.NEEDS_CORRECTIONS));

        return questionEntity.getVoteStatus();
    }

    private boolean isUserAlreadyVoted(QuestionEntity questionEntity) {
        Set<ActionType> voteActions = Set.of(ActionType.QUESTION_ACCEPT, ActionType.QUESTION_REJECT, ActionType.QUESTION_NEEDS_CORRECTION);
        User currentLoggedUser = userService.getUserWithAuthorities().get();

        return questionEntity.getActions()
            .stream()
            .anyMatch(action ->
                voteActions.contains(action.getActionType())
                    && action.getUser().getLogin().equals(currentLoggedUser.getLogin()));
    }
}
