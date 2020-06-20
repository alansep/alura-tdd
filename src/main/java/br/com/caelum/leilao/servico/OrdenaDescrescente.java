package br.com.caelum.leilao.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class OrdenaDescrescente implements ProcessoLeilao {

	@Override
	public void executaProcesso(Avaliador leiloeiro, Leilao leilao) {
		List<Lance> lances = new ArrayList<Lance>(leilao.getLances());
		Collections.sort(lances, new Comparator<Lance>() {
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() > o2.getValor()) {
					return -1;
				}
				if (o1.getValor() < o2.getValor()) {
					return 1;
				}
				return 0;
			}
		});
		leiloeiro.setOrdenados(lances);
	}

}
