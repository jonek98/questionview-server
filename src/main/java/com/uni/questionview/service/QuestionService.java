package com.uni.questionview.service;

import com.uni.questionview.domain.User;
import com.uni.questionview.domain.entity.ActionEntity;
import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.domain.entity.RatingEntity;
import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.repository.ActionRepository;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.repository.RatingRepository;
import com.uni.questionview.repository.TagRepository;
import com.uni.questionview.service.dto.*;
import com.uni.questionview.service.exceptions.QuestionAlreadyOnUserList;
import com.uni.questionview.service.exceptions.QuestionNotFoundException;
import com.uni.questionview.service.exceptions.UserAlreadyRatedQuestionException;
import com.uni.questionview.service.mapper.QuestionMapper;
import com.uni.questionview.service.mapper.RatingMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final ActionRepository actionRepository;

    private final RatingRepository ratingRepository;

    private final TagRepository tagRepository;

    private final UserService userService;

    private final ActionService actionService;

    public List<SimplifiedQuestionDTO> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(questionMapper::mapToSimplifiedQuestionDTO)
                .toList();
    }

    @Transactional
    public QuestionDTO addQuestion(AddQuestionDTO addQuestionDTO) {
        QuestionEntity questionToSave = createQuestion(addQuestionDTO);

        QuestionEntity savedQuestion = questionRepository.save(questionToSave);

        ActionEntity actionToSave = actionService.createAddQuestionAction(savedQuestion);

        actionRepository.save(actionToSave);

        return questionMapper.mapToQuestionDTO(savedQuestion);
    }

    public QuestionDTO editQuestion(AddQuestionDTO addQuestionDTO) {
        QuestionEntity editedQuestion = this.createEditedQuestion(addQuestionDTO);

        QuestionEntity updatedQuestion = questionRepository.save(editedQuestion);

        return questionMapper
                .mapToQuestionDTO(updatedQuestion);
    }

    public QuestionDTO getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .map(questionMapper::mapToQuestionDTO)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id: "+ questionId+ "not found."));
    }

    public QuestionDetailsDTO getQuestionDetails(Long questionId) {
        return questionRepository.findById(questionId)
                .map(questionMapper::mapToQuestionDetailsDTO)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id: "+ questionId+ "not found."));
    }

    public RatingDTO addRating(RatingDTO ratingDTO) {
        QuestionEntity questionEntity = questionRepository.findById(ratingDTO.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Question with id not found: " + ratingDTO.getQuestionId()));

        RatingEntity ratingEntityToSave = RatingEntity.builder()
                .rating(ratingDTO.getRating())
                .question(questionEntity)
                .user(getCurrentLoggedUser())
                .build();

        if (userAlreadyRatedQuestion(ratingEntityToSave))
            throw new UserAlreadyRatedQuestionException("User " + ratingEntityToSave.getUser().getLogin()
                + " has already rated the question with id: " + ratingEntityToSave.getQuestion().getId());

        return RatingMapper.mapToDto(ratingRepository.save(ratingEntityToSave));
    }

    public List<SimplifiedQuestionDTO> getQuestionsFromUserList() {
        return questionRepository.findAll()
            .stream()
            .filter(question -> question.checkIfQuestionIsOnUserList(getCurrentLoggedUser().getId()))
            .map(questionMapper::mapToSimplifiedQuestionDTO)
            .toList();
    }

    public List<SimplifiedQuestionDTO> addQuestionToUserList(long questionId) {
        User currentLoggedUser = getCurrentLoggedUser();

        QuestionEntity questionEntity = questionRepository.findById(questionId)
            .orElseThrow(() -> new RuntimeException("Question with id: "+ questionId + " not found"));

        if (questionEntity.getUsersWithQuestionOnList().contains(currentLoggedUser)) {
            throw new QuestionAlreadyOnUserList("User '" + currentLoggedUser.getLogin()
                    +"' has already on list the question with ID: " + questionId);
        }

        questionEntity.getUsersWithQuestionOnList().add(currentLoggedUser);

        questionRepository.save(questionEntity);

        return getQuestionsFromUserList();
    }

    public List<SimplifiedQuestionDTO> removeQuestionFromUserList(long questionId) {
        User currentLoggedUseruser = getCurrentLoggedUser();

        QuestionEntity questionEntity = questionRepository.findById(questionId)
            .orElseThrow(() -> new RuntimeException("Question with id: "+ questionId + " not found"));

        if (!questionEntity.getUsersWithQuestionOnList().contains(currentLoggedUseruser)) {
            throw new IllegalArgumentException( "User '" + currentLoggedUseruser.getLogin()
                    + "' has not question with ID: " + questionId + " on their list");

        }

        questionEntity.getUsersWithQuestionOnList().remove(currentLoggedUseruser);

        questionRepository.save(questionEntity);

        return getQuestionsFromUserList();
    }

    public boolean removeQuestion(long questionId) {
        questionRepository.deleteById(questionId);

        return questionRepository.existsById(questionId);
    }

    private boolean userAlreadyRatedQuestion(RatingEntity ratingEntity) {
        return ratingRepository
            .existsByUserIdAndQuestionId(ratingEntity.getUser().getId(), ratingEntity.getQuestion().getId());
    }

    private QuestionEntity createQuestion(AddQuestionDTO addQuestionDTO) {
        List<TagEntity> tags = tagRepository.findAllById(addQuestionDTO.getTagIds());

        return  QuestionEntity.builder()
                .questionText(addQuestionDTO.getAnswerText())
                .answerText(addQuestionDTO.getAnswerText())
                .difficultyLevel(addQuestionDTO.getDifficultyLevel())
                .summary(addQuestionDTO.getSummary())
                .language(addQuestionDTO.getLanguage())
                .tags(tags)
                .actions(Collections.emptyList())
                .build();
    }

    private QuestionEntity createEditedQuestion(AddQuestionDTO addQuestionDTO) {
        List<TagEntity> tags = tagRepository.findAllById(addQuestionDTO.getTagIds());

        QuestionEntity questionFromDb = questionRepository.findById(addQuestionDTO.getId())
                .orElseThrow(() -> new RuntimeException("Question with id: "+ addQuestionDTO.getId() + " not found"));

        ActionEntity editQuestionAction = actionRepository.save(actionService.createEditQuestionAction(questionFromDb));

        List<ActionEntity> questionActions = Stream.concat(questionFromDb.getActions().stream(), Stream.of(editQuestionAction))
                .toList();

        return QuestionEntity.builder()
                .id(addQuestionDTO.getId())
                .answerText(addQuestionDTO.getAnswerText())
                .questionText(addQuestionDTO.getQuestionText())
                .difficultyLevel(addQuestionDTO.getDifficultyLevel())
                .summary(addQuestionDTO.getSummary())
                .language(addQuestionDTO.getLanguage())
                .timeEstimate(addQuestionDTO.getTimeEstimate())
                .tags(tags)
                .ratings(questionFromDb.getRatings())
                .actions(questionActions)
                .usersWithQuestionOnList(questionFromDb.getUsersWithQuestionOnList())
                .build();
    }

    private User getCurrentLoggedUser() {
        return userService.getUserWithAuthorities()
                .orElseThrow(() -> new RuntimeException("Current logged user not found"));
    }
}
