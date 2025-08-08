package adotepet.model.enums;

public enum TipoEnum {
	C("Cachorro"), G("Gato");

	private final String tipo;

	TipoEnum(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return tipo;
	}
}
