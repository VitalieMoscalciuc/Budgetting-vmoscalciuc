package com.vmoscalciuc.budget.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {

    private Long id;
    @NotEmpty
    private String name;

    private String expenseDate;

    @NotNull
    @Min(0)@Max(100000000)
    private Double amount;

    private Long userId;
}
