package com.uni.questionview.web.rest;

import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.security.AuthoritiesConstants;
import com.uni.questionview.service.QuestionService;
import com.uni.questionview.service.dto.AdminUserDTO;
import com.uni.questionview.service.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final QuestionService questionService;

    @Autowired
    public QuestionResource(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/allQuestions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        log.debug("REST request to get all Questions");

        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @PostMapping("/addQuestion")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionDTO questionDTO) {
        log.debug("REST post to add new Questions");

        return new ResponseEntity<>(questionService.addQuestion(questionDTO), HttpStatus.OK);
    }
}
