package com.foliaco.graphql_course.course;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

import com.foliaco.graphql_course.lesson.Lesson;
import com.foliaco.graphql_course.user.User;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
        name = "course_user",
        joinColumns = @JoinColumn(name = "id_course"),
        inverseJoinColumns = @JoinColumn(name = "id_student")
    )
    private Set<User> students;

    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons;
}
