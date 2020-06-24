package br.com.caelum.leilao.builder;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoBuilder {

	private Leilao leilao;

	public LeilaoBuilder para(String descricao) {
		this.leilao = new Leilao(descricao);
		return this;
	}

	public LeilaoBuilder lance(Usuario joao, double valor) {
		leilao.propoe(new Lance(joao, valor));
		return this;
	}

	public Leilao constroi() {
		return leilao;
	}

}
