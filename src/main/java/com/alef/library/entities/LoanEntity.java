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

@Entity
@Getter
@Setter
@Table(name = "loan")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE loan SET deleted = True WHERE id = ?")
public class LoanEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    @LastModifiedDate
    private LocalDate refundDate;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
