package com.raghad.sales_management_system.logging;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.raghad.sales_management_system.repositories.ClientRepository;
import com.raghad.sales_management_system.entities.Client;

@Aspect
@Component
public class ClientUpdateDataExtractor {
    private final ClientRepository clientRepository;

    public ClientUpdateDataExtractor(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Around("@annotation(com.raghad.sales_management_system.annotations.ClientUpdateLogging)")
    public Object extractClientUpdateData(ProceedingJoinPoint joinPoint) throws Throwable {
        Client newClient = (Client) joinPoint.getArgs()[0];
        String clientName = newClient.getClass().getSimpleName();
        Integer clientId = newClient.getId();

        Optional<Client> oldClient = this.clientRepository.findById(clientId);
        Client existingClient;

        List<ResourceUpdatedField> updatedFields = new ArrayList<>();
        UpdatedResourceData resourceData = null;

        if(oldClient.isPresent()) {
            existingClient = oldClient.get();

            if(!(existingClient.getAddress().equals(newClient.getAddress())))
                updatedFields.add(new ResourceUpdatedField("Address", existingClient.getAddress(), newClient.getAddress()));
            if(!(existingClient.getEmailAddress().equals(newClient.getEmailAddress())))
                updatedFields.add(new ResourceUpdatedField("Email Address", existingClient.getEmailAddress(), newClient.getEmailAddress()));
            if(!(existingClient.getMobileNumber().equals(newClient.getMobileNumber())))
                updatedFields.add(new ResourceUpdatedField("Mobile Number", existingClient.getMobileNumber(), newClient.getMobileNumber()));

            resourceData = new UpdatedResourceData(clientName, clientId, updatedFields);
        }

        Object returnedValue = joinPoint.proceed();

        if(updatedFields.size() > 0)
            ResourceUpdateLogger.logResourceUpdate(resourceData);

        return returnedValue;
    }
}
