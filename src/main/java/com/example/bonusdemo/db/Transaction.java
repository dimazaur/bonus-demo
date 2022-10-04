package com.example.bonusdemo.db;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "transaction",
        indexes = {
                @Index(name = "index_user", columnList = "userId"
                )
        })
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String userId;

    private BigDecimal amount;

    private LocalDateTime timestamp;

}
