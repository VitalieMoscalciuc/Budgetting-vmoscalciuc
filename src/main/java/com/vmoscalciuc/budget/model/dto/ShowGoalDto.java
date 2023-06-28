package com.vmoscalciuc.budget.model.dto;

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

    private String doneBy;

    private Long userId;


}
