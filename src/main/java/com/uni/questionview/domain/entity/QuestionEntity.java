package com.uni.questionview.domain.entity;

import com.uni.questionview.domain.Language;
import com.uni.questionview.domain.Status;

import java.util.ArrayList;
import java.util.List;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToMany(mappedBy = "questions")
    private List<RatingEntity> ratings;

    @Column(name = "timeestimate")
    private int timeEstimate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "question_tag", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    private List<TagEntity> tags = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<ActionEntity> actions;
}
