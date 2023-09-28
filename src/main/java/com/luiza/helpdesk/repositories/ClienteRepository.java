package com.luiza.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luiza.helpdesk.domain.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
