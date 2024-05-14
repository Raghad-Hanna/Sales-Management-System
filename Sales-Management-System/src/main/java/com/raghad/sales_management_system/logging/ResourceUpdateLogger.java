package com.raghad.sales_management_system.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;

public class ResourceUpdateLogger {
    private static final Logger logger = LoggerFactory.getLogger(ResourceUpdateLogger.class);

    public static void logResourceUpdate(UpdatedResourceData resourceData) {
        logger.info("\nResource Type: " + resourceData.name() + ",\n"
                + "Resource ID: " + resourceData.id() + ",\n"
                + "Modification Date: " + LocalDate.now() + ",\n"
                + "Updated Fields: " + resourceData.updatedFields());
    }
}
