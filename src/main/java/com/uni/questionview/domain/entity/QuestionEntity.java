package com.uni.questionview.domain.entity;

import com.uni.questionview.domain.Language;
import com.uni.questionview.domain.Status;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private long id;

    private String answerText;

    private String questionText;

    private int difficultyLevel;

    private Status status;

    private String statusChaneReason;

    private String summary;

    private Language language;

    private int timeEstimate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "question_tag", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    private Set<com.uni.questionview.domain.entity.TagEntity> tags = new HashSet<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private Set<com.uni.questionview.domain.entity.ActionEntity> actions;

}
