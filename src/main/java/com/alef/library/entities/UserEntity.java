package com.alef.library.entities;

import com.alef.library.security.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private Long dni;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    private Integer phoneNumber;

    private String image;

    private Role role;

    private String email;

    private String password;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    @LastModifiedDate
    private LocalDate modificationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanEntity> loans;
}
