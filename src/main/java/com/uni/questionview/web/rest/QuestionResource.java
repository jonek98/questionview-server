package com.uni.questionview.web.rest;

import com.uni.questionview.security.AuthoritiesConstants;
import com.uni.questionview.service.ActionService;
import com.uni.questionview.service.QuestionService;
import com.uni.questionview.service.dto.ActionDTO;
import com.uni.questionview.service.dto.QuestionDTO;
import com.uni.questionview.service.dto.RatingDTO;
import com.uni.questionview.service.dto.SimplifiedQuestionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/allQuestions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SimplifiedQuestionDTO>> getAllQuestions() {
        log.debug("REST request to get all Questions");

        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @PostMapping("/addQuestion")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionDTO questionDTO) {
        log.debug("REST post to add new Questions");

        return new ResponseEntity<>(questionService.addQuestion(questionDTO, getCurrentUserName()), HttpStatus.CREATED);
    }

    @GetMapping("/getQuestion/{questionId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable long questionId) {
        log.debug("REST get question by id: {}", questionId);

        return new ResponseEntity<>(questionService.getQuestion(questionId), HttpStatus.OK);
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
        log.debug("REST add rating to question with id: {}", ratingDTO.getQuestionId());

        return new ResponseEntity<>(questionService.addRating(ratingDTO), HttpStatus.CREATED);
    }

    //TODO: change this
//    @GetMapping("/userQuestionsList/{userId}")
//    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
//    public ResponseEntity<List<QuestionDTO>> getQuestionsFromUserList(@PathVariable long userId) {
//        log.debug("REST get questions from user list {}", userId);
//
//        return new ResponseEntity<>(questionService.getQuestionsFromUserList(userId), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/deleteFromUserList/{userId}/{questionId}")
//    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
//    public ResponseEntity<Boolean> removeQuestionFromUserList(@PathVariable long userId, @PathVariable long questionId) {
//        log.debug("REST delete question with id {} from user list", questionId);
//
//        return new ResponseEntity<>(questionService.removeQuestionFromUserList(questionId, userId), HttpStatus.OK);
//    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        throw new RuntimeException("User not found");
    }
}
