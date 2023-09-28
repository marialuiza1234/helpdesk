package com.luiza.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luiza.helpdesk.domain.entity.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
