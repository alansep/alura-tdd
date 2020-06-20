package br.com.caelum.leilao.servico;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private double somaValores = 0.0;
	private int quantidadeLances;
	private List<Lance> maiores = new ArrayList<Lance>();
	private List<Lance> ordenados;

	public void avalia(Leilao leilao, List<ProcessoLeilao> processos) {
		for (Lance lance : leilao.getLances()) {
			if (lance.getValor() > maiorDeTodos) {
				maiorDeTodos = lance.getValor();
			}
			if (lance.getValor() < menorDeTodos) {
				menorDeTodos = lance.getValor();
			}
			somaValores += lance.getValor();
			quantidadeLances++;
		}

		if (!processos.isEmpty()) {
			for (ProcessoLeilao processo : processos) {
				processo.executaProcesso(this, leilao);
			}
		}
	}

	public void cadastraAosMaiores(Lance lance) {
		this.maiores.add(lance);
	}

	public List<Lance> getTresMaiores() {
		return maiores;
	}

	public double getMedia() {
		return somaValores / quantidadeLances;
	}

	public double getMaiorLance() {
		return maiorDeTodos;
	}

	public double getMenorLance() {
		return menorDeTodos;
	}

	public void somarValor(double valor) {
		this.somaValores += valor;
	}

	public List<Lance> getOrdenados() {
		return ordenados;
	}

	public void setOrdenados(List<Lance> ordenados) {
		this.ordenados = ordenados;
	}

}
