package com.foliaco.graphql_course.resource;

import java.util.List;

import com.foliaco.graphql_course.exception.NotFoundException;
import com.foliaco.graphql_course.graphql.ResourceInput;
import com.foliaco.graphql_course.lesson.Lesson;
import com.foliaco.graphql_course.lesson.LessonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foliaco.graphql_course.util.DataPaginator;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private ResourceRepository resourceRepository;

    private LessonRepository lessonRepository;

    private ResourceMapper resourceMapper;

    @Transactional(readOnly = true)
    @Override
    public Resource findById(Integer id) {

        return resourceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Resource not found with id: " + id));

    }

    @Transactional(readOnly = true)
    @Override
    public DataPaginator<List<Resource>> findAll(Pageable pageable) {
        Page<Resource> pageResource = resourceRepository.findAll(pageable);
        return new DataPaginator<>(pageResource.getContent(), pageResource.getTotalPages(), pageResource.getNumber());
    }

    @Transactional
    @Override
    public Resource createResource(ResourceInput resourceInput) {

        Lesson lesson = lessonRepository.findById(resourceInput.lessonId()).orElseThrow(
                () -> new NotFoundException("Lesson not found with id: " + resourceInput.lessonId())
        );

        Resource resource = resourceMapper.toResource(resourceInput, lesson);
        return resourceRepository.save(resource);
    }

    @Transactional
    @Override
    public boolean deleteById(Integer id) {
        Resource resource = resourceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Resource not found with id: " + id));

        resourceRepository.delete(resource);
        return true;
    }

    @Transactional
    @Override
    public Resource updateResource(Integer id, ResourceInput resourceInput) {
        Resource resource = resourceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Resource not found with id: " + id)
        );

        Lesson lesson = lessonRepository.findById(resourceInput.lessonId()).orElseThrow(
                () -> new NotFoundException("Lesson not found with id: " + resourceInput.lessonId())
        );

        resource.setType(resourceInput.type());
        resource.setDescription(resourceInput.description());
        resource.setUrl(resourceInput.url());
        resource.setLesson(lesson);
        return resource;
    }

}