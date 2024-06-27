package com.example.forohub.domains.answers;

import jakarta.validation.constraints.NotNull;

public record AnswerDataUpdate(@NotNull Long id, String message) {
}
