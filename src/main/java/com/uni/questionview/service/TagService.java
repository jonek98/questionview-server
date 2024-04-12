package com.uni.questionview.service;

import com.uni.questionview.domain.entity.TagEntity;
import com.uni.questionview.repository.TagRepository;
import com.uni.questionview.service.dto.TagDTO;
import com.uni.questionview.service.mapper.TagMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

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
