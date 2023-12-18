package com.socialmedia.excepiton;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {

    private int code;
    private String message;
    private List<String> fields;

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();
}
