package adotepet.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class MenuPetDAO {
	private static final String CAMINHO = "/home/luan/eclipse-workspace/AdotePet/src/adotepet/database/menupet.txt";

	public Map<String, String> recuperarMenuInicial() {
		Map<String, String> menuInicialPets = new HashMap<>();
		int numeroLinha = 1;
		String linha;

		try (var reader = new BufferedReader(new FileReader(CAMINHO))) {

			while ((linha = reader.readLine()) != null) {

				menuInicialPets.put(String.valueOf(numeroLinha++), linha);

			}

			return menuInicialPets;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
