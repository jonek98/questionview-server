package com.uni.questionview.repository;

import com.uni.questionview.domain.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity findById(long questionId);

}
