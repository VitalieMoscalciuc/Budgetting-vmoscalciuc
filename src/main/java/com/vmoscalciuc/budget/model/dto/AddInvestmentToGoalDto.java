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
    public class AddInvestmentToGoalDto {

        private Long id;

        private Double amount;

    }
