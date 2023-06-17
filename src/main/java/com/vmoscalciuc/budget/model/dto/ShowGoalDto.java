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
public class ShowGoalDto {

    private Long id;

    private String name;

    private String deadLine;

    private String timeRemains;

    private Double price;

    private Double investment;

    private Double doneBy;

    private Long userId;


}
