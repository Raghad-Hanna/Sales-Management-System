package com.raghad.sales_management_system.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.raghad.sales_management_system.annotations.ClientUpdateLogging;
import java.util.List;
import java.util.Optional;

import com.raghad.sales_management_system.repositories.ClientRepository;
import com.raghad.sales_management_system.entities.Client;
import com.raghad.sales_management_system.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return this.clientRepository.findAll();
    }

    @Transactional
    public Client createClient(Client client) {
        return this.clientRepository.save(client);
    }

    @ClientUpdateLogging
    @Transactional
    public Client updateClient(Client updatedClient, Integer id) {
        Optional<Client> originalClient = this.clientRepository.findById(id);
        originalClient.ifPresentOrElse(existingProduct -> this.clientRepository.save(updatedClient),
                () -> { throw new ResourceNotFoundException("Non-existent client with an id of "
                        + id + ". Provide an existing client to be updated"); });

        return updatedClient;
    }

    @Transactional
    public void deleteClient(Integer id) {
        Optional<Client> originalClient = this.clientRepository.findById(id);
        originalClient.ifPresentOrElse(existingClient -> this.clientRepository.deleteById(id),
                () -> { throw new ResourceNotFoundException("Non-existent client with an id of "
                        + id + ". Provide an existing client to be deleted"); });
    }
}
