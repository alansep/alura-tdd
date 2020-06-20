package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Leilao;

public interface ProcessoLeilao {
	
	public void executaProcesso(Avaliador leiloeiro, Leilao leilao);

}
