package com.kflix.app.entity;

import com.kflix.app.util.classes.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Content extends BaseEntity {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private String thumbnail;
    @Column(nullable = false, unique = true)
    private String filename;
    private Long size;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ContentType contentType;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Genre genre;

}
