package com.example.forohub.domains.answers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerData(@NotNull Long topicId, @NotBlank String message) {
}
