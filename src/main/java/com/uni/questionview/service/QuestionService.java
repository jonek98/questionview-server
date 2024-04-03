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
import com.uni.questionview.service.exceptions.QuestionNotFoundException;
import com.uni.questionview.service.exceptions.UserNotFoundException;
import com.uni.questionview.service.mapper.QuestionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public QuestionDTO addQuestion(QuestionDTO questionDTO, String userName) {
        User user = userRepository.findOneByLogin(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found with login: " + userName));

        QuestionEntity questionEntityToSave = questionMapper.mapToQuestionEntity(questionDTO);
        QuestionEntity savedQuestion = questionRepository.save(questionEntityToSave);

        ActionEntity actionEntity = ActionEntity.builder()
                .question(savedQuestion)
                .user(user)
                .actionType(ActionType.QUESTION_ADD)
                .comment("User " + userName + " has created a question")
                .build();
        actionRepository.save(actionEntity);

        QuestionEntity updatedQuestion = questionRepository.findById(savedQuestion.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found after saving: " + savedQuestion.getId()));

        return questionMapper.mapToQuestionDTO(updatedQuestion);
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
