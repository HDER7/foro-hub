package com.example.forohub.domains.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserData(@NotNull String name,
                       @NotBlank
                       @Email
                       String mail,
                       @NotBlank
                       String password) {
}
