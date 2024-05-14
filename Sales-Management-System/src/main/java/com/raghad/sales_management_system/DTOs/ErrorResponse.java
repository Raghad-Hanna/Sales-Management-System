package com.raghad.sales_management_system.DTOs;

import java.util.List;

public record ErrorResponse(String description,
                            List<ErrorDetail> errorDetails) {
}
