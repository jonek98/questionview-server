package com.uni.questionview.service;

import com.uni.questionview.domain.ActionType;
import com.uni.questionview.domain.Status;
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
import com.uni.questionview.service.exceptions.QuestionCreatorNotFound;
import com.uni.questionview.service.exceptions.QuestionNotAcceptedException;
import com.uni.questionview.service.exceptions.QuestionNotFoundException;
import com.uni.questionview.service.exceptions.QuestionAlreadySubmittedException;
import com.uni.questionview.service.exceptions.QuestionRejectedException;
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

    private final VotingService votingService;

    public List<SimplifiedQuestionDTO> getSubmittedQuestions() {
        return questionRepository.findAll()
                .stream()
                .filter(question -> question.getStatus() == Status.ACCEPTED)
                .map(questionMapper::mapToSimplifiedQuestionDTO)
                .toList();
    }

    public List<SimplifiedQuestionDTO> getPendingQuestions() {
        return questionRepository.findAll()
                .stream()
                .filter(question -> question.getStatus() == Status.PENDING)
                .map(questionMapper::mapToSimplifiedQuestionDTO)
                .toList();
    }

    public List<SimplifiedQuestionDTO> getUserRejectedQuestions() {
        return questionRepository.findAll()
                .stream()
                .filter(question -> question.getStatus() == Status.REJECTED)
                .filter(this::isQuestionCreatedByCurrentLoggedUser)
                .map(questionMapper::mapToSimplifiedQuestionDTO)
                .toList();
    }

    public List<SimplifiedQuestionDTO> getUserQuestionsToCorrection() {
        return questionRepository.findAll()
                .stream()
                .filter(question -> question.getStatus() == Status.NEEDS_CORRECTIONS)
                .filter(this::isQuestionCreatedByCurrentLoggedUser)
                .map(questionMapper::mapToSimplifiedQuestionDTO)
                .toList();
    }

    public QuestionDTO correctQuestion(AddQuestionDTO addQuestionDTO, String correctionComment) {
        QuestionEntity correctedQuestion = this.createCorrectedQuestion(addQuestionDTO, correctionComment);

        QuestionEntity updatedQuestion = questionRepository.save(correctedQuestion);

        return questionMapper
                .mapToQuestionDTO(updatedQuestion);
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

    public QuestionDetailsDTO getSubmittedQuestionDetails(Long questionId) {
        return questionRepository.findById(questionId)
                .filter(question -> question.getStatus() == Status.ACCEPTED)
                .map(questionMapper::mapToQuestionDetailsDTO)
                .orElseThrow(() -> new QuestionNotAcceptedException("Question with id: " + questionId +
                        " is not accepted or does not exist."));
    }

    public QuestionDetailsDTO getPendingQuestionDetails(Long questionId) {
        return questionRepository.findById(questionId)
                .filter(question -> question.getStatus() == Status.PENDING)
                .map(questionMapper::mapToQuestionDetailsDTO)
                .orElseThrow(() -> new QuestionAlreadySubmittedException("Question with id: " + questionId +
                        " is already submitted or does not exist."));
    }

    public QuestionDetailsDTO getRejectedQuestionDetails(Long questionId) {
        return questionRepository.findById(questionId)
                .filter(question -> question.getStatus() == Status.REJECTED)
                .map(questionMapper::mapToQuestionDetailsDTO)
                .orElseThrow(() -> new QuestionRejectedException("Question with id: " + questionId +
                        " has been rejected or does not exist."));
    }

    public QuestionDetailsDTO getCorrectionQuestionDetails(Long questionId) {
        return questionRepository.findById(questionId)
                .filter(question -> question.getStatus() == Status.NEEDS_CORRECTIONS)
                .map(questionMapper::mapToQuestionDetailsDTO)
                .orElseThrow(() -> new QuestionRejectedException("Question with id: " + questionId +
                        " need corrections or does not exist."));
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

    public VoteStatus voteForQuestion(ActionDTO actionDTO) {
        return votingService.voteForQuestion(actionDTO);
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
                .status(Status.PENDING)
                .build();
    }

    @Transactional
    private QuestionEntity createEditedQuestion(AddQuestionDTO addQuestionDTO) {
        List<TagEntity> tags = tagRepository.findAllById(addQuestionDTO.getTagIds());

        QuestionEntity questionFromDb = questionRepository.findById(addQuestionDTO.getId())
                .orElseThrow(() -> new RuntimeException("Question with id: "+ addQuestionDTO.getId() + " not found"));

        ActionEntity editQuestionAction = actionRepository.save(actionService.createEditQuestionAction(questionFromDb));

        List<ActionEntity> questionActions = Stream.concat(questionFromDb.getActions().stream(), Stream.of(editQuestionAction))
                .toList();

        ratingRepository.deleteAll(questionFromDb.getRatings());
        questionFromDb.getRatings().clear();

        return questionRepository.save(QuestionEntity.builder()
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
                .status(Status.PENDING)
                .build());
    }

    private QuestionEntity createCorrectedQuestion(AddQuestionDTO addQuestionDTO, String correctionComment) {
        List<TagEntity> tags = tagRepository.findAllById(addQuestionDTO.getTagIds());

        QuestionEntity questionFromDb = questionRepository.findById(addQuestionDTO.getId())
                .orElseThrow(() -> new RuntimeException("Question with id: "+ addQuestionDTO.getId() + " not found"));

        ActionEntity correctionQuestionAction = actionRepository.save(actionService.createCorrectionQuestionAction(questionFromDb, correctionComment));

        List<ActionEntity> questionActions = Stream.concat(questionFromDb.getActions().stream(), Stream.of(correctionQuestionAction))
                .toList();

        questionFromDb.getRatings().clear();

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
                .status(Status.PENDING)
                .build();
    }

    private boolean isQuestionCreatedByCurrentLoggedUser(QuestionEntity question) {
        Long questionCreatorUserId = question
                .getActions()
                .stream()
                .filter(action -> action.getActionType() == ActionType.QUESTION_ADD)
                .map(ActionEntity::getUser)
                .map(User::getId)
                .findFirst()
                .orElseThrow(() -> new QuestionCreatorNotFound("Creator of question: "+ question.getId()+ " not found!"));

        return questionCreatorUserId.equals(getCurrentLoggedUser().getId());
    }

    private User getCurrentLoggedUser() {
        return userService.getUserWithAuthorities()
                .orElseThrow(() -> new RuntimeException("Current logged user not found"));
    }
}
