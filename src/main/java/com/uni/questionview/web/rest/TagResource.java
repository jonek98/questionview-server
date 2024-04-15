package com.uni.questionview.web.rest;

import com.uni.questionview.security.AuthoritiesConstants;
import com.uni.questionview.service.TagService;
import com.uni.questionview.service.dto.TagDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagResource {
    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    private final TagService tagService;

    @Autowired
    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/allTags")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        log.debug("REST request to get all Tags");

        return new ResponseEntity<>(tagService.getAllTags(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteTag")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Boolean> deleteTag(@RequestParam Long tagId) {
        log.debug("REST request to delete Tag with id: {}", tagId);

        return new ResponseEntity<>(tagService.removeTag(tagId), HttpStatus.OK);
    }

    @PostMapping("/addTag")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<TagDTO> addTag(@RequestBody TagDTO tagDTO) {
        log.debug("REST request to add Tag");

        return new ResponseEntity<>(tagService.createTag(tagDTO), HttpStatus.OK);
    }
}
