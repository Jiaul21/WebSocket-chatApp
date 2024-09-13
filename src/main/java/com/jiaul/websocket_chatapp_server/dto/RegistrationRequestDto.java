package com.jiaul.websocket_chatapp_server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RegistrationRequestDto {

    @NotNull
    @NotBlank
    @Email
    private String userName;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String role;
    private String firstName;
}
