package com.foliaco.graphql_course.resource;

import com.foliaco.graphql_course.lesson.Lesson;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resource")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    private String url;

    private String description;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
