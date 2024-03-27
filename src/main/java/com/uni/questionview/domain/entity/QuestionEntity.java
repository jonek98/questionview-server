package com.uni.questionview.domain.entity;

import com.uni.questionview.domain.Language;
import com.uni.questionview.domain.Status;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="question")
public class QuestionEntity {

    @Id
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
    @JoinTable(name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags = new HashSet<>();
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private Set<ActionEntity> actions;

}
