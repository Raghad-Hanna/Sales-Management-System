package com.raghad.sales_management_system.logging;

import java.util.List;

public record UpdatedResourceData(String name, Integer id, List<ResourceUpdatedField> updatedFields) {
}
