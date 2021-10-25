package com.daeuntube.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginFormDTO {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
