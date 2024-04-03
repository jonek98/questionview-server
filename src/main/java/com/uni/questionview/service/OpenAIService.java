package com.uni.questionview.service;

import com.uni.questionview.domain.AIPrompts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatRequest;
import io.github.sashirestela.openai.domain.chat.message.ChatMsgSystem;
import io.github.sashirestela.openai.domain.chat.message.ChatMsgUser;

@Service
public class OpenAIService {
    private static final String GPT_MODEL = "gpt-4-0125-preview";
    private final PdfService pdfService;

    @Autowired
    public OpenAIService(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    public String cvAnalysis(MultipartFile file) {
        String cvContent = pdfService.readPdf(file);

        var openai = SimpleOpenAI.builder()
                .apiKey(System.getenv("GPT_TOKEN"))
                .build();

        var chatRequest = ChatRequest.builder()
                .model(GPT_MODEL)
                .messages(List.of(
                        new ChatMsgSystem(AIPrompts.RECRUITER_ROLE.getPrompt()),
                        new ChatMsgUser(AIPrompts.CV_ANALYSIS.getPrompt()),
                        new ChatMsgUser(cvContent)))
                .temperature(0.0)
                .maxTokens(1000)
                .build();

        var futureChat = openai.chatCompletions().create(chatRequest);

        var chatResponse = futureChat.join();

        return chatResponse.firstContent();
    }
}