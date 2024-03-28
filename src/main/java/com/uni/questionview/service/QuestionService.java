package com.uni.questionview.service;

import com.uni.questionview.domain.entity.QuestionEntity;
import com.uni.questionview.repository.QuestionRepository;
import com.uni.questionview.service.dto.QuestionDTO;
import com.uni.questionview.service.mapper.QuestionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionMapper::mapQuestionEntityToQuestionDTO)
                .toList();
    }

    public QuestionDTO addQuestion(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = QuestionMapper.mapQuestionDTOToQuestionEntity(questionDTO);

        return QuestionMapper.mapQuestionEntityToQuestionDTO(questionRepository.save(questionEntity));
    }
}
