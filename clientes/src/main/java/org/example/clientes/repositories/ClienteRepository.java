package org.example.clientes.repositories;

import org.example.clientes.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> { }
