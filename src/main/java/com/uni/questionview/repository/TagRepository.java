package com.uni.questionview.repository;

import com.uni.questionview.domain.entity.TagEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    List<TagEntity> findByIdIn(Set<Long> ids);

     void deleteById(Long id);
}
