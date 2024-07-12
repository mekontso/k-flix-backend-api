package com.kflix.app.entity;

import com.kflix.app.util.classes.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ContentType extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "contentType",fetch = FetchType.LAZY)
    private List<Content> contentList;
}
