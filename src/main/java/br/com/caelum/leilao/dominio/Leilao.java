package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {
		int total = qtdDeLancesDo(lance.getUsuario());

		if (lances.isEmpty() || (!ultimoLanceDado().getUsuario().equals(lance.getUsuario()) && total < 5)) {
			lances.add(lance);
		}
	}

	private int qtdDeLancesDo(Usuario usuario) {
		int total = 0;
		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario))
				total++;
		}
		return total;
	}

	private Lance ultimoLanceDado() {
		return lances.get(lances.size() - 1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public Double ultimoLanceDo(Usuario usuario) {
		try {
			List<Lance> lancesDoUsuario = lances.stream().filter(lance -> lance.getUsuario().equals(usuario))
					.collect(Collectors.toList());
			return lancesDoUsuario.get(lancesDoUsuario.size() - 1).getValor();
		} catch (RuntimeException e) {
			return null;
		}
	}

	public void dobrarLanceDo(Usuario usuario) {
		Double valorDoUltimoLance = ultimoLanceDo(usuario);
		if(valorDoUltimoLance != null) {
			propoe(new Lance(usuario, valorDoUltimoLance * 2));
		}
	}

}
