package com.uni.questionview.service;

import org.springframework.stereotype.Service;

import io.github.sashirestela.openai.SimpleOpenAI;

@Service
public class OpenAI {



    public void test() {

        var openai = SimpleOpenAI.builder()
                .apiKey(System.getenv("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"))
                .build();
    }

}
