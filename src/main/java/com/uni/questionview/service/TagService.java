package com.uni.questionview.service;

import com.uni.questionview.repository.TagRepository;
import com.uni.questionview.service.dto.TagDTO;
import com.uni.questionview.service.mapper.TagMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .map(tagMapper::mapTagEntityToTagDTO)
                .collect(Collectors.toList());
    }

    public TagDTO createTag(TagDTO tagDTO) {
        tagRepository.save(tagMapper.mapToTagEntity(tagDTO));

        return tagDTO;
    }

    public boolean removeTag(Long tagId) {
        tagRepository.deleteById(tagId);

        return !tagRepository.existsById(tagId);
    }
}
