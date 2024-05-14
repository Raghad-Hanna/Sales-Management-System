package com.raghad.sales_management_system.DTOs;

import java.util.List;
import java.util.Map;

public record ProductReport(int productCount,
                            Map<String, List<Integer>> classifiedProductsByCategory,
                            List<Integer> recentlyCreatedProducts) {
}
