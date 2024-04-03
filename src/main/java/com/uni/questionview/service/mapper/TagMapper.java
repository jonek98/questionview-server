package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.service.dto.TagDTO;

public class TagMapper {

    private TagMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static TagDTO mapToTagDTO(TagEntity tagEntity) {
        return TagDTO.builder()
                .id(tagEntity.getId())
                .tagLabel(tagEntity.getTagLabel())
                .build();
    }

    public static TagEntity mapToTagEntity(TagDTO tagDTO) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTagLabel(tagDTO.getTagLabel());

        return tagEntity;
    }
}
