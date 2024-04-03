package com.uni.questionview.repository;

import com.uni.questionview.domain.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import jakarta.transaction.Transactional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query(value = "SELECT * FROM question q WHERE q.id IN (SELECT qu.question_id FROM user_question qu WHERE qu.user_id = :userId)", nativeQuery = true)
    List<QuestionEntity> findQuestionsFromUserList(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_question WHERE question_id = :questionId AND user_id = :userId", nativeQuery = true)
    void deleteQuestionFromUserList(@Param("questionId") Long questionId, @Param("userId") Long userId);

    @Query(value = "SELECT COUNT(*) FROM user_question WHERE question_id = :questionId AND user_id = :userId", nativeQuery = true)
    int checkQuestionUserAssociationExists(@Param("questionId") Long questionId, @Param("userId") Long userId);
}
