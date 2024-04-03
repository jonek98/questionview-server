package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.service.dto.TagDTO;

import org.springframework.stereotype.Service;

@Service
public class TagMapper {

    public TagDTO mapToTagDTO(TagEntity tagEntity) {
        return TagDTO.builder()
                .id(tagEntity.getId())
                .tagLabel(tagEntity.getTagLabel())
                .build();
    }

    public TagEntity mapToTagEntity(TagDTO tagDTO) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTagLabel(tagDTO.getTagLabel());

        return tagEntity;
    }
}
