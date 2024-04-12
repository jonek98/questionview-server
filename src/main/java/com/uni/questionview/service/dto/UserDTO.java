package com.uni.questionview.service.dto;

import com.uni.questionview.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String login;

    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            "}";
    }
}
