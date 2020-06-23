package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AnoBissextoTest {

	@Test
	public void deveValidarAnosTest() {
		AnoBissexto anoBissexto = new AnoBissexto();
		assertEquals(false, anoBissexto.ehBissexto(5));
		assertEquals(true, anoBissexto.ehBissexto(2020));
		assertEquals(false, anoBissexto.ehBissexto(2021));
		assertEquals(true, anoBissexto.ehBissexto(2420));
	}

}
