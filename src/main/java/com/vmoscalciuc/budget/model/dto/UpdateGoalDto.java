package com.vmoscalciuc.budget.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGoalDto {

    private Long id;
    @NotEmpty
    private String name;

    private String goalDate;

    @NotNull
    @Min(0)@Max(100000000)
    private Double amount;

    private Long userId;


}

