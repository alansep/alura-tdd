package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class TesteAvaliador {

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {

		// parte 1: cenario
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		// parte 2: acao
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao, Arrays.asList());

		// parte 3: validacao
		double maiorEsperado = 400;
		double menorEsperado = 250;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Usuario joao = new Usuario("João");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 1000.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);

	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(joao, 400.0));

		Avaliador leiloeiro = new Avaliador();

		leiloeiro.avalia(leilao, Arrays.asList(new OrdenaDescrescente(), new PegaTresMaiores()));

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(3, maiores.size());
		assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(200.0, maiores.get(2).getValor(), 0.00001);

	}

	@Test
	public void deveCalcularDistanciaLanceUnico() {
		Usuario joao = new Usuario("João");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 200.0));

		Avaliador leiloeiro = new Avaliador();

		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(200.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(200.0, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveCalcularLancesAleatorios() {
		Usuario joao = new Usuario("João");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(joao, 450.0));
		leilao.propoe(new Lance(joao, 120.0));
		leilao.propoe(new Lance(joao, 700.0));
		leilao.propoe(new Lance(joao, 630.0));
		leilao.propoe(new Lance(joao, 230.0));

		Avaliador leiloeiro = new Avaliador();

		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(700.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(120.0, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveTestarOrdemDecrescente() {
		Usuario joao = new Usuario("João");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 400.0));
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(joao, 300.0));

		Avaliador leiloeiro = new Avaliador();

		leiloeiro.avalia(leilao, Arrays.asList(new OrdenaDescrescente()));
		assertEquals(4, leiloeiro.getOrdenados().size());
		assertEquals(400.0, leiloeiro.getOrdenados().get(0).getValor(), 0.00001);
		assertEquals(300.0, leiloeiro.getOrdenados().get(1).getValor(), 0.00001);
		assertEquals(200.0, leiloeiro.getOrdenados().get(2).getValor(), 0.00001);
		assertEquals(100.0, leiloeiro.getOrdenados().get(3).getValor(), 0.00001);
	}

	@Test
	public void deveCalcularMedia() {
		// parte 1: cenario
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(jose, 200.0));

		// parte 2: acao
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(150.0, leiloeiro.getMedia(), 0.000001);
	}
	
	@Test
	public void deveLocalizarDoisUnicosLances() {
		// parte 1: cenario
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(jose, 200.0));

		// parte 2: acao
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao, Arrays.asList());

        assertEquals(2, leilao.getLances().size());
		assertEquals(100.0, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(200.0, leilao.getLances().get(1).getValor(), 0.0001);
	}
	
	
	@Test
	public void deveLocalizarListaVazia() {
		// parte 1: cenario
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		// parte 2: acao
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(0, leilao.getLances().size());
	}

}
