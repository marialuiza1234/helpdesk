package com.luiza.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.luiza.helpdesk.domain.entity.Chamado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luiza.helpdesk.domain.entity.Pessoa;
import com.luiza.helpdesk.domain.entity.Tecnico;
import com.luiza.helpdesk.domain.dtos.TecnicoDTO;
import com.luiza.helpdesk.repositories.PessoaRepository;
import com.luiza.helpdesk.repositories.TecnicoRepository;
import com.luiza.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.luiza.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	public TecnicoRepository repository;
	@Autowired
	public PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encode;
	
	public Tecnico findById (Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encode.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	} 
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);

		if(!objDTO.getSenha().equals(oldObj.getSenha()))
			objDTO.setSenha(encode.encode(objDTO.getSenha()));

		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if (obj.getChamados().size() >0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado");
		}
		repository.deleteById(id);
	}


	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema! ");
		}
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema! ");
		}
	}

}
