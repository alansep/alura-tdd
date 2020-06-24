package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static br.com.caelum.leilao.servico.matchers.LeilaoMatcher.temUmLance;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;


public class LeilaoTest {

	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		assertEquals(0, leilao.getLances().size());
		Lance lance = new Lance(new Usuario("Steve Jobs"), 2000);
		leilao.propoe(lance);
		assertThat(leilao.getLances().size(), equalTo(1));
		assertThat(leilao, temUmLance(lance));
	}

	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
		leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000));

		assertEquals(2, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.0001);
	}

	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");

		leilao.propoe(new Lance(steveJobs, 2000));
		leilao.propoe(new Lance(steveJobs, 3000));

		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(1, leilao.getLances().size());

	}

	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario billGates = new Usuario("Bill Gates");

		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(billGates, 3000.0));

		leilao.propoe(new Lance(steveJobs, 4000.0));
		leilao.propoe(new Lance(billGates, 5000.0));

		leilao.propoe(new Lance(steveJobs, 6000.0));
		leilao.propoe(new Lance(billGates, 7000.0));

		leilao.propoe(new Lance(steveJobs, 8000.0));
		leilao.propoe(new Lance(billGates, 9000.0));

		leilao.propoe(new Lance(steveJobs, 10000.0));
		leilao.propoe(new Lance(billGates, 11000.0));

		leilao.propoe(new Lance(steveJobs, 12000.0));

		assertEquals(10, leilao.getLances().size());
		assertEquals(11000.0, leilao.getLances().get(9).getValor(), 0.00001);

	}

	@Test
	public void deveDobrarUltimoLance() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario billGates = new Usuario("Bill Gates");
		Usuario jeff = new Usuario("Jeff Bezos");

		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(billGates, 2000.0));

		leilao.dobrarLanceDo(steveJobs);

		assertEquals(4000.0, leilao.ultimoLanceDo(steveJobs), 0.00001);
		assertEquals(3, leilao.getLances().size(), 0.00001);
		assertEquals(null, leilao.ultimoLanceDo(jeff));
	}

}
