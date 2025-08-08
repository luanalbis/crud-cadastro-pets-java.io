package adotepet.model.enums;

public enum SexoEnum {
	M("Macho"), F("Fêmea");

	private final String sexo;

	SexoEnum(String sexo) {
		this.sexo = sexo;
	}

	public String getDescricao() {
		return sexo;
	}

}
