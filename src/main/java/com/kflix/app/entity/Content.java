package com.kflix.app.entity;

import com.kflix.app.util.classes.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Content extends BaseEntity {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ContentType contentType;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Genre genre;

}
