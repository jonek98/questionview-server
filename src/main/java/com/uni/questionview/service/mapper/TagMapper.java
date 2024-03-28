package com.uni.questionview.service.mapper;

import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.service.dto.TagDTO;

import org.springframework.stereotype.Service;

@Service
public class TagMapper {

    public TagDTO mapTagEntityToTagDTO(TagEntity tagEntity) {
        return TagDTO.builder()
                .id(tagEntity.getId())
                .tagLabel(tagEntity.getTagLabel())
                .status(tagEntity.getStatus())
                .build();
    }

    public TagEntity mapToTagEntity(TagDTO tagDTO) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTagLabel(tagDTO.getTagLabel());
        tagEntity.setStatus(tagDTO.getStatus());

        return tagEntity;
    }
}
