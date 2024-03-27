package com.uni.questionview.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tag")
public class TagEntity {

    @Id
    private long id;

    private String tagLabel;

    private String status;

}
