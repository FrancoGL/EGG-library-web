package com.alef.library.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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

    private Long isbn;

    private String title;

    private Integer publicationYear;

    private Integer copies;

    private Integer LentCopies;

    private Integer CurrentCopies;

    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AuthorEntity author;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private EditorialEntity editorial;
}
