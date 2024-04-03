package com.uni.questionview.domain.entity;

import com.uni.questionview.domain.Language;
import com.uni.questionview.domain.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

    @Column(name = "answertext")
    private String answerText;

    @Column(name = "questiontext")
    private String questionText;

    @Column(name = "difficultylevel")
    private int difficultyLevel;

    private Status status;

    @Column(name = "statuschangereason")
    private String statusChaneReason;

    private String summary;

    private Language language;

    @Column(name = "timeestimate")
    private int timeEstimate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "question_tag", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    private List<TagEntity> tags = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<ActionEntity> actions;

}
