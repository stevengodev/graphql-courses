package com.foliaco.graphql_course.exception;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private String message;
    private Instant timestamp;
    private int status;
    private String path;
}
