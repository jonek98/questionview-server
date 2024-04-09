package com.uni.questionview.service;

import com.uni.questionview.domain.ActionType;
import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.domain.entity.RatingEntity;
import com.uni.questionview.repository.ActionRepository;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.repository.RatingRepository;
import com.uni.questionview.repository.UserRepository;
import com.uni.questionview.service.dto.QuestionDTO;
import com.uni.questionview.service.dto.RatingDTO;
import com.uni.questionview.service.dto.SimplifiedQuestionDTO;
import com.uni.questionview.service.exceptions.QuestionNotFoundException;
import com.uni.questionview.service.exceptions.UserAlreadyRatedQuestionException;
import com.uni.questionview.service.exceptions.UserNotFoundException;
import com.uni.questionview.service.mapper.QuestionMapper;

import com.uni.questionview.service.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final UserRepository userRepository;

    private final ActionRepository actionRepository;

    private final RatingRepository ratingRepository;

    private final RatingMapper ratingMapper;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper,
                           UserRepository userRepository, ActionRepository actionRepository, RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
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
                .date(LocalDate.now())
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

    public RatingDTO addRating(RatingDTO ratingDTO) {
        RatingEntity ratingEntityToSave = ratingMapper.mapToEntity(ratingDTO);

        if(userAlreadyRatedQuestion(ratingEntityToSave))
            throw new UserAlreadyRatedQuestionException("User " + ratingEntityToSave.getUser().getLogin()
                + " has already rated the question with id: " + ratingEntityToSave.getQuestion().getId());

        return ratingMapper.mapToDto(ratingRepository.save(ratingEntityToSave));
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

    private boolean userAlreadyRatedQuestion(RatingEntity ratingEntity) {
        return ratingRepository
            .existsByUserIdAndQuestionId(ratingEntity.getUser().getId(), ratingEntity.getQuestion().getId());
    }
}
