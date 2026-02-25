package com.avsarzirh.LibraryManagementSystemAPI.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_borrower")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @CreationTimestamp //@PrePersist kullanarak registrationDate = now yapmakla ayni is
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "borrower")
    private List<Borrow> borrows;
}
