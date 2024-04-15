package com.uni.questionview.service;

import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.repository.TagRepository;
import com.uni.questionview.service.dto.TagDTO;
import com.uni.questionview.service.mapper.TagMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagMapper::mapToTagDTO)
                .toList();
    }

    public TagDTO createTag(TagDTO tagDTO) {
        TagEntity tagToSave = TagEntity.builder()
                .tagLabel(tagDTO.getTagLabel())
                .build();

        TagEntity savedTag = tagRepository.save(tagToSave);

        return TagMapper.mapToTagDTO(savedTag);
    }

    public boolean removeTag(Long tagId) {
        tagRepository.deleteById(tagId);

        return !tagRepository.existsById(tagId);
    }
}
