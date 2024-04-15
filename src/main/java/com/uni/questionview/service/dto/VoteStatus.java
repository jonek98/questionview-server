package com.uni.questionview.service.dto;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class VoteStatus {

    @Max(3)
    private int acceptVotesNumber;
    @Max(3)
    private int rejectVotesNumber;
    @Max(1)
    private int statusChangeVotesNumber;
}