package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.service.dto.TagDTO;

import org.springframework.stereotype.Service;

public class TagMapper {

    private TagMapper() {}

    public static TagDTO mapToTagDTO(TagEntity tagEntity) {
        return TagDTO.builder()
                .id(tagEntity.getId())
                .tagLabel(tagEntity.getTagLabel())
                .build();
    }
}
