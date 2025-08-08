package adotepet.controller;

import java.util.Map;

import adotepet.dao.MenuPetDAO;

public class MenuPetController {

	private MenuPetDAO dao = new MenuPetDAO();

	public Map<String, String> recuperarMenuPets() {
		try {
			return dao.recuperarMenuInicial();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
