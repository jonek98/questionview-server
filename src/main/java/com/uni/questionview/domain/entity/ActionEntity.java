package com.uni.questionview.domain.entity;

import com.uni.questionview.domain.User;
import jakarta.persistence.*;
import com.uni.questionview.domain.ActionType;

@Entity
@Table(name = "action")
public class ActionEntity {

    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String newStatus;

    private String comment;

    private Boolean newUserVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idQuestion", referencedColumnName = "id")
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;
}
