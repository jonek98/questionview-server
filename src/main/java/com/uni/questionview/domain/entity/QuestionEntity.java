package com.uni.questionview.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uni.questionview.domain.ActionType;
import com.uni.questionview.domain.Language;
import com.uni.questionview.domain.Status;
import com.uni.questionview.domain.User;
import com.uni.questionview.service.dto.VoteStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Builder
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

    @JsonIgnore
    @With
    @Column(name = "status")
    private Status status;


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

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RatingEntity> ratings;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_question", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns =
    @JoinColumn(name = "user_id"))
    private List<User> usersWithQuestionOnList;

    public List<RatingEntity> getRatings() {
        return Optional.ofNullable(ratings)
                .orElse(Collections.emptyList());
    }
    public double calculateRating() {
        return getRatings()
            .stream()
            .map(RatingEntity::getRating)
            .mapToInt(Integer::intValue)
            .average()
            .orElse(Double.NaN);
    }

    public boolean checkIfQuestionIsOnUserList(Long userId) {
        return this.getUsersWithQuestionOnList()
            .stream()
            .map(User::getId)
            .toList()
            .contains(userId);
    }

    public int countNumberOfAcceptVotes() {
        return (int) actions.stream()
                .filter(action -> action.getActionType() == ActionType.QUESTION_ACCEPT)
                .count();
    }

    public int countNumberOfRejectVotes() {
        return (int) actions.stream()
                .filter(action -> action.getActionType() == ActionType.QUESTION_REJECT)
                .count();
    }

    public int countNumberOfNeedCorrectionsVotes() {
        return (int) actions.stream()
                .filter(action -> action.getActionType() == ActionType.QUESTION_NEEDS_CORRECTION)
                .count();
    }

    public VoteStatus getVoteStatus() {
        return VoteStatus.of(
                countNumberOfAcceptVotes(),
                countNumberOfRejectVotes(),
                countNumberOfNeedCorrectionsVotes());
    }
}
