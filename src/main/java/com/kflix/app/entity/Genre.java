package com.kflix.app.entity;

import com.kflix.app.util.classes.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Genre extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
}
