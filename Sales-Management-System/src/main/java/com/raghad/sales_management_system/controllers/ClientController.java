package com.raghad.sales_management_system.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;

import com.raghad.sales_management_system.services.ClientService;
import com.raghad.sales_management_system.entities.Client;
import com.raghad.sales_management_system.exceptions.ResourceIdsMismatchException;

@RestController
@RequestMapping(path = "api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients() {
        return this.clientService.getClients();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@Valid @RequestBody Client client) {
        return this.clientService.createClient(client);
    }

    @PutMapping(path = "/{id}")
    public Client updateClient(@Valid @RequestBody Client client, @PathVariable("id") Integer id) {
        if(client.getId() != id)
            throw new ResourceIdsMismatchException("The client ids in the URI and in the request payload must match");
        return this.clientService.updateClient(client, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable("id") Integer id) {
        this.clientService.deleteClient(id);
    }
}
