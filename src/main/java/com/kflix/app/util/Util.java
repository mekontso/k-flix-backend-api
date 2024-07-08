package com.kflix.app.util;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public class Util {
    public static HashMap<Object, Object> pageData(List<?> data, Page<?> queryResponse) {
        var pagination = new HashMap<>();
        pagination.put("content", data);
        pagination.put("totalElements", queryResponse.getTotalElements());
        pagination.put("pageNumber", queryResponse.getNumber());
        pagination.put("pageSize", queryResponse.getSize());
        pagination.put("totalPages", queryResponse.getTotalPages());
        pagination.put("hasNext", queryResponse.hasNext());
        pagination.put("hasPrevious", queryResponse.hasPrevious());
        pagination.put("sort", queryResponse.getSort().toString());
        return pagination;
    }
}
