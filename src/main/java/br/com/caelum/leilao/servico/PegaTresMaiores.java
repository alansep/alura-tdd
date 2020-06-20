package br.com.caelum.leilao.servico;

import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class PegaTresMaiores implements ProcessoLeilao{

	@Override
	public void executaProcesso(Avaliador leiloeiro, Leilao leilao) {
		List<Lance> lances = leiloeiro.getOrdenados();
		lances.subList(0, lances.size() > 3 ? 3 : lances.size()).forEach(lance -> leiloeiro.cadastraAosMaiores(lance));
	}

}
