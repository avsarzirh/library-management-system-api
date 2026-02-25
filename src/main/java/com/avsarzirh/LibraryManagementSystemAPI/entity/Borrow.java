package com.avsarzirh.LibraryManagementSystemAPI.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_borrow")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Book book;

    @ManyToOne
    @JoinColumn //mappedBy, buna bakarak iliskiyi anlayacak.
    private Borrower borrower;

    private LocalDateTime borrowedAt;

    private LocalDateTime expectedReturnAt;

    private LocalDateTime returnedAt;
}
