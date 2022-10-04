package com.example.bonusdemo.controller;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class TransactionDto {

    @JsonIgnore
    private Integer id;

    @NotBlank
    private String userId;

    @NotNull
    private BigDecimal amount;

}
