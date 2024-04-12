package com.uni.questionview.repository;

import com.uni.questionview.domain.entity.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
}
