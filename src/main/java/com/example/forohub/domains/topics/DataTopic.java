package com.example.forohub.domains.topics;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DataTopic(@NotNull String title,
                        @NotBlank
                        String message,
                        @NotNull
                        Long courseId) {
}
