package com.uni.questionview.web.rest;

import com.uni.questionview.security.AuthoritiesConstants;
import com.uni.questionview.service.ActionService;
import com.uni.questionview.service.QuestionService;
import com.uni.questionview.service.VotingService;
import com.uni.questionview.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResource.class);

    private final QuestionService questionService;

    private final ActionService actionService;

    @Autowired
    public QuestionResource(QuestionService questionService, ActionService actionService) {
        this.questionService = questionService;
        this.actionService = actionService;
    }

    @GetMapping("/getSubmittedQuestions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SimplifiedQuestionDTO>> getSubmittedQuestions() {
        log.debug("REST request to get submitted questions");

        return new ResponseEntity<>(questionService.getSubmittedQuestions(), HttpStatus.OK);
    }

    @GetMapping("/getPendingQuestions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SimplifiedQuestionDTO>> getPendingQuestions() {
        log.debug("REST request to get pending questions");

        return new ResponseEntity<>(questionService.getPendingQuestions(), HttpStatus.OK);
    }

    @GetMapping("/getUserRejectedQuestions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SimplifiedQuestionDTO>> getRejectedQuestions() {
        log.debug("REST request to get rejected questions");

        return new ResponseEntity<>(questionService.getUserRejectedQuestions(), HttpStatus.OK);
    }

    @GetMapping("/getUserQuestionsToCorrection")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SimplifiedQuestionDTO>> getUserQuestionsToCorrection() {
        log.debug("REST request to get pending questions");

        return new ResponseEntity<>(questionService.getUserQuestionsToCorrection(), HttpStatus.OK);
    }

    @PostMapping("/correctedQuestion")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDTO> questionCorrection(@RequestBody AddQuestionDTO addQuestionDTO) {
        log.debug("REST post to correct Question");

        return new ResponseEntity<>(questionService.correctQuestion(addQuestionDTO), HttpStatus.CREATED);
    }

    @PostMapping("/addQuestion")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody AddQuestionDTO addQuestionDTO) {
        log.debug("REST post to add new Questions");

        return new ResponseEntity<>(questionService.addQuestion(addQuestionDTO), HttpStatus.CREATED);
    }

    @PostMapping("/editQuestion")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDTO> editQuestion(@RequestBody AddQuestionDTO addQuestionDTO) {
        log.debug("REST post to edit Question");

        return new ResponseEntity<>(questionService.editQuestion(addQuestionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getQuestion/{questionId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable long questionId) {
        log.debug("REST get question by id: {}", questionId);

        return new ResponseEntity<>(questionService.getQuestion(questionId), HttpStatus.OK);
    }

    @GetMapping("/getSubmittedQuestionDetails/{questionId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDetailsDTO> getSubmittedQuestionDetails(@PathVariable long questionId) {
        log.debug("REST get question by id: {}", questionId);

        return new ResponseEntity<>(questionService.getSubmittedQuestionDetails(questionId), HttpStatus.OK);
    }

    @GetMapping("/getPendingQuestionDetails/{questionId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDetailsDTO> getPendingQuestionDetails(@PathVariable long questionId) {
        log.debug("REST get question by id: {}", questionId);

        return new ResponseEntity<>(questionService.getPendingQuestionDetails(questionId), HttpStatus.OK);
    }

    @GetMapping("/getRejectedQuestionDetails/{questionId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDetailsDTO> getRejectedQuestionDetails(@PathVariable long questionId) {
        log.debug("REST get question by id: {}", questionId);

        return new ResponseEntity<>(questionService.getRejectedQuestionDetails(questionId), HttpStatus.OK);
    }

    @GetMapping("/getCorrectionQuestionDetails/{questionId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDetailsDTO> getNeedCorrectionQuestionDetails(@PathVariable long questionId) {
        log.debug("REST get question by id: {}", questionId);

        return new ResponseEntity<>(questionService.getCorrectionQuestionDetails(questionId), HttpStatus.OK);
    }

    @PostMapping("/addAction")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ActionDTO> addAction(@RequestBody ActionDTO actionDTO) {
       log.debug("REST add {} action to question with questionId: {}", actionDTO.getActionType(), actionDTO.getQuestionId());

        return new ResponseEntity<>(actionService.addAction(actionDTO), HttpStatus.CREATED);
    }

    @PostMapping("/addRating")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<RatingDTO> addRating(@RequestBody RatingDTO ratingDTO) {
        log.debug("REST post rating to question with id: {}", ratingDTO.getQuestionId());

        return new ResponseEntity<>(questionService.addRating(ratingDTO), HttpStatus.CREATED);
    }

    @GetMapping("/userQuestionList")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SimplifiedQuestionDTO>> getQuestionsFromUserList() {
        log.debug("REST get current logged user question list");

        return new ResponseEntity<>(questionService.getQuestionsFromUserList(), HttpStatus.OK);
    }

    @PostMapping("/addQuestionToUserList")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SimplifiedQuestionDTO>> addQuestionToUserList(@RequestParam long questionId) {
        log.debug("Add questions to current logged user list");

        return new ResponseEntity<>(questionService.addQuestionToUserList(questionId), HttpStatus.OK);
    }

    @DeleteMapping("/removeQuestionFromUserList")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SimplifiedQuestionDTO>> removeQuestionFromUserList(@RequestParam long questionId) {
        log.debug("REST delete question with id {} from user list", questionId);
        return new ResponseEntity<>(questionService.removeQuestionFromUserList(questionId), HttpStatus.OK);
    }

    @PostMapping("/vote")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<VoteStatus> vote(@RequestBody ActionDTO actionDTO) {
        return new ResponseEntity<>(questionService.voteForQuestion(actionDTO), HttpStatus.OK);
    }
}
