package com.uni.questionview.service;

import com.uni.questionview.domain.ActionType;
import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.repository.ActionRepository;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.repository.UserRepository;
import com.uni.questionview.service.dto.QuestionDTO;
import com.uni.questionview.service.dto.SimplifiedQuestionDTO;
import com.uni.questionview.service.mapper.QuestionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final UserRepository userRepository;

    private final ActionRepository actionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper,
            UserRepository userRepository, ActionRepository actionRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
    }

    public List<SimplifiedQuestionDTO> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(questionMapper::mapToSimplifiedQuestionDTO)
                .toList();
    }

    public QuestionDTO addQuestion(QuestionDTO questionDTO, String userName) {
        QuestionEntity questionEntityToSave = questionMapper.mapToQuestionEntity(questionDTO);

        QuestionEntity savedQuestion = questionRepository.save(questionEntityToSave);

        User user = userRepository.findOneByLogin(userName).orElseThrow();

        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setQuestion(savedQuestion);
        actionEntity.setUser(user);
        actionEntity.setActionType(ActionType.QUESTION_ADD);
        actionEntity.setComment("User " + userName + " has created question");

        ActionEntity savedActionEntity = actionRepository.save(actionEntity);

        if(savedActionEntity != null)
            savedQuestion.setActions(List.of(actionEntity));

        return questionMapper.mapToQuestionDTO(savedQuestion);
    }

    public QuestionDTO getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .map(questionMapper::mapToQuestionDTO)
                .orElseThrow();
    }

    public List<QuestionDTO> getQuestionsFromUserList(long userId) {
        return questionRepository.findQuestionsFromUserList(userId)
                .stream()
                .map(questionMapper::mapToQuestionDTO)
                .toList();
    }

    public boolean removeQuestionFromUserList(long questionId, long userId) {
        questionRepository.deleteQuestionFromUserList(questionId, userId);
        return questionRepository.checkQuestionUserAssociationExists(questionId, userId) == 0;
    }

    public boolean removeQuestion(long questionId) {
        questionRepository.deleteById(questionId);

        return questionRepository.existsById(questionId);
    }
}
