package com.uni.questionview.domain.entity;

import com.uni.questionview.domain.ActionType;
import com.uni.questionview.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "action")
public class ActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "actiontype")
    private ActionType actionType;

    private String comment;

    private Timestamp date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idquestion", referencedColumnName = "id")
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;
}
