package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.builder.LeilaoBuilder;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void setUp() {
		this.leiloeiro = new Avaliador();
		System.out.println("cria avaliador");
		this.joao = new Usuario("João");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
	}

	@After
	public void finaliza() {
		System.out.println("fim");
	}

	@BeforeClass
	public static void testandoBeforeClass() {
		System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
		System.out.println("after class");
	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeilaoSemNenhumLaanceDado() {
		Leilao leilao = new LeilaoBuilder()
				.para("Playstation 3 Novo")
				.constroi();

		leiloeiro.avalia(leilao, new ArrayList<ProcessoLeilao>());
	}

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {

		// parte 1: cenario
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
				.lance(joao, 250.0)
				.lance(jose, 300.0)
				.lance(maria, 400.0)
				.constroi();

		// parte 2: acao
		leiloeiro.avalia(leilao, Arrays.asList());

		// parte 3: validacao
		double maiorEsperado = 400;
		double menorEsperado = 250;

		assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
		assertThat(leiloeiro.getMenorLance(), equalTo(250.0));

		// assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		// assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new LeilaoBuilder()
				.para("Playstation 3 Novo")
				.lance(joao, 1000.0)
				.constroi();

		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);

	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {

		Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo").lance(joao, 100.0).lance(maria, 200.0)
				.lance(joao, 300.0).lance(maria, 400.0).constroi();

		leiloeiro.avalia(leilao, Arrays.asList(new OrdenaDescrescente(), new PegaTresMaiores()));

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(3, maiores.size());

		assertThat(maiores, hasItems(
				new Lance(maria, 400),
				new Lance(joao, 300),
				new Lance(maria, 200)));

	}

	@Test
	public void deveCalcularDistanciaLanceUnico() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo").lance(joao, 200.0).constroi();

		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(200.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(200.0, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveCalcularLancesAleatorios() {

		Leilao leilao = new LeilaoBuilder()
				.para("Playstation 3 Novo")
				.lance(joao, 200.0)
				.lance(maria, 450.0)
				.lance(joao, 120.0)
				.lance(maria, 700.0)
				.lance(joao, 630.0)
				.lance(maria, 230.0)
				.constroi();

		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(700.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(120.0, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveTestarOrdemDecrescente() {
		Leilao leilao = new LeilaoBuilder()
				.para("Playstation 3 Novo")
				.lance(joao, 400.0)
				.lance(new Usuario("Pedro"), 200.0)
				.lance(joao, 100.0)
				.lance(new Usuario("Pedro"), 300.0)
				.constroi();

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
		Leilao leilao = new LeilaoBuilder()
				.para("Playstation 3 Novo")
				.lance(joao, 100.0)
				.lance(jose, 200.0)
				.constroi();

		// parte 2: acao
		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(150.0, leiloeiro.getMedia(), 0.000001);
	}

	@Test
	public void deveLocalizarDoisUnicosLances() {
		// parte 1: cenario
		Leilao leilao = new LeilaoBuilder()
				.para("Playstation 3 Novo")
				.lance(joao, 100.0)
				.lance(jose, 200.0)
				.constroi();

		// parte 2: acao
		leiloeiro.avalia(leilao, Arrays.asList());

		assertEquals(2, leilao.getLances().size());
		assertEquals(100.0, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(200.0, leilao.getLances().get(1).getValor(), 0.0001);
	}

	@Test
	public void deveLocalizarListaVazia() {
		// parte 1: cenario
		try {
			Leilao leilao = new LeilaoBuilder()
					.para("Playstation 3 Novo")
					.constroi();

			// parte 2: acao
			leiloeiro.avalia(leilao, Arrays.asList());

			Assert.fail();
		} catch (RuntimeException e) {

		}
		// assertEquals(0, leilao.getLances().size());
	}

}
