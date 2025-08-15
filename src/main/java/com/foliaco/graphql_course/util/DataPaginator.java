package com.foliaco.graphql_course.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataPaginator<T> {

    private T data;
    private int totalPages;
    private int currentPage;

}
