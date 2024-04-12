package com.uni.questionview.web.rest;

import com.uni.questionview.security.AuthoritiesConstants;
import com.uni.questionview.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ai")
public class AiResource {

    private final OpenAIService openAIService;

    @Autowired
    public AiResource(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping(value = "/cvAnalysis", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public String getAllQuestions(@RequestPart MultipartFile file) {
        return openAIService.cvAnalysis(file);
    }
}
