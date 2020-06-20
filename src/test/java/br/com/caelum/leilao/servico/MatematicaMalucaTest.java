package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatematicaMalucaTest {

	@Test
	public void maiorQue30Test() {
		MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
		assertEquals(200, matematicaMaluca.contaMaluca(50));
	}

	@Test
	public void maiorQue10MenorQue30Test() {
		MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
		assertEquals(60, matematicaMaluca.contaMaluca(20));
	}

	@Test
	public void menorQue10Test() {
		MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
		assertEquals(10, matematicaMaluca.contaMaluca(5));

	}

}
