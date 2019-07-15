package org.summer.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Result {
    @NotNull
    private final int left;

    @NotNull
    private final int right;

    private final long answer;
}
