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

    private final TagMapper tagMapper;

    @Autowired
    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::mapToTagDTO)
                .toList();
    }

    public TagDTO createTag(TagDTO tagDTO) {
        TagEntity savedTag = tagRepository.save(tagMapper.mapToTagEntity(tagDTO));

        return tagMapper.mapToTagDTO(savedTag);
    }

    public boolean removeTag(Long tagId) {
        tagRepository.deleteById(tagId);

        return !tagRepository.existsById(tagId);
    }
}
