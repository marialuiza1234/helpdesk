package com.luiza.helpdesk.domain.enuns;

public enum Status {

	ABERTO(0, "ROLE_ABERTO" ), ANDAMENTO(1, "ROLE_ANDAMENTO" ), ENCERRADO(2, "ROLE_ENCERRADO" );
	
	private Integer codigo;
	private String descricao;
	
	
	private Status(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}


	public Integer getCodigo() {
		return codigo;
	}


	public String getDescricao() {
		return descricao;
	}
	
	public static Status toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for(Status x : Status.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Status Inválido!");
	}
}
