package com.kflix.app.entity;

import com.kflix.app.util.classes.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Genre extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private List<Content> contentList;
}
