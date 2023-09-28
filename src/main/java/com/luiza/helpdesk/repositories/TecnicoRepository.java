package com.luiza.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luiza.helpdesk.domain.entity.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
