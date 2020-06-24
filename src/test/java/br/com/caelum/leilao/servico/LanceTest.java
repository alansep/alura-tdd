package br.com.caelum.leilao.servico;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Usuario;

public class LanceTest {

	@Test(expected = RuntimeException.class)
	public void lanceDeveSerMaiorQueZeroTest() {
		Lance lance = new Lance(new Usuario("Gabriel"), 0);
	}
	
	
}
