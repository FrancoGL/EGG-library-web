package com.alef.library.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "book")
@EntityListeners(AuditingEntityListener.class)
public class BookEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private Long isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column(nullable = false)
    private Integer copies;

    @Column(nullable = false)
    private Integer LentCopies;

    @Column(nullable = false)
    private Integer CurrentCopies;

    private Boolean deleted = Boolean.FALSE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDate modificationDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AuthorEntity author;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private EditorialEntity editorial;
}
