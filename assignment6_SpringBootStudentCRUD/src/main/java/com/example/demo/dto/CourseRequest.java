package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {
    @NotBlank
    @Size(min = 2, max = 100)
    private String courseName;

    @NotBlank
    @Size(min = 2, max = 100)
    private String instructor;
}