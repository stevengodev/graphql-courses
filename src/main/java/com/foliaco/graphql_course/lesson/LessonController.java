package com.foliaco.graphql_course.lesson;

import java.util.List;

import com.foliaco.graphql_course.graphql.LessonInput;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.foliaco.graphql_course.util.DataPaginator;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class LessonController {

    private LessonService lessonService;

    @QueryMapping(name = "findLessonById")
    public Lesson findLessonById(@Argument(name = "lessonId") Integer id){
        return lessonService.findById(id);
    }

    @MutationMapping(name = "createLesson")
    public Lesson createLesson(@Argument(name = "lessonInput") LessonInput lessonInput){
        return lessonService.createLesson(lessonInput);
    }

    @MutationMapping(name = "updateLesson")
    public Lesson updateLesson(@Argument Integer id,
                           @Argument(name = "lessonInput") LessonInput lessonInput){
        return lessonService.updateLesson(id, lessonInput);
    }

    @MutationMapping(name = "deleteLesson")
    public boolean deleteLesson(@Argument(name = "id") Integer id){
        return lessonService.deleteById(id);
    }

    @QueryMapping(name = "findAllLessonsPaginated")
    public DataPaginator<List<Lesson>> findAllLessonsPaginated(@Argument(name = "page") int page,
                                                           @Argument(name = "size") int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return lessonService.findAll(pageable);
    }

}
