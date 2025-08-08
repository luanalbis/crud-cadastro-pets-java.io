package adotepet.model;

import java.util.Map;

public class Pet {
	private Map<String, String> dados;

	public Pet() {

	}

	public Pet(Map<String, String> dados) {
		super();
		this.dados = dados;

	}

	public Map<String, String> getDados() {
		return dados;
	}

	public void setDados(Map<String, String> dados) {
		this.dados = dados;
	}

}
