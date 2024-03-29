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

    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionMapper::mapToQuestionDTO)
                .toList();
    }

    public QuestionDTO addQuestion(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = questionMapper.mapQuestionDTOToQuestionEntity(questionDTO);

        return QuestionMapper.mapToQuestionDTO(questionRepository.save(questionEntity));
    }

    public QuestionDTO getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .map(QuestionMapper::mapToQuestionDTO)
                .orElseThrow();
    }

    public boolean removeQuestion(long questionId) {
        questionRepository.deleteById(questionId);

        return questionRepository.existsById(questionId);
    }
}
