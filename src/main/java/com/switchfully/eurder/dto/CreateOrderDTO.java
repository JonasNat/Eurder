package com.switchfully.eurder.dto;

import java.util.List;

public record CreateOrderDTO(List<CreateOrderLineDTO> orderLines) {
}
