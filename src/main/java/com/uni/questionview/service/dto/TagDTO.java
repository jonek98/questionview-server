package com.uni.questionview.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDTO {

    private long id;
    private String tagLabel;
    private String status;
}
