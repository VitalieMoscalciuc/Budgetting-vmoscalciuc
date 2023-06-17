package com.vmoscalciuc.budget.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIncomeDto {
    private Long id;
    @NotEmpty
    private String name;

    private String incomeDate;

    @NotNull
    @Min(0)@Max(100000000)
    private Double amount;

    private Long userId;
}

