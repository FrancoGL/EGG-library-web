package com.alef.library.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "author")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE author SET deleted = True WHERE id = ?")
public class AuthorEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    private String photo;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    @LastModifiedDate
    private LocalDate modificationDate;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<BookEntity> books = new ArrayList<>();
}
