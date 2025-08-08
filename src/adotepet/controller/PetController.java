package adotepet.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import adotepet.app.AdotePetApp;
import adotepet.dao.PetDAO;
import adotepet.model.Pet;

public class PetController {
	private Scanner scanner = AdotePetApp.scanner;
	private PetDAO petDAO = new PetDAO();

	public boolean cadastraPet(Pet pet) {
		try {
			petDAO.cadastrarPet(pet);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String coletaSimOuNao(String pergunta, String respostaInvalida) {
		System.out.println(pergunta);
		String res = scanner.nextLine();
		while (!res.equalsIgnoreCase("s") && !res.equalsIgnoreCase("n")) {
			System.out.println(respostaInvalida);
			res = scanner.nextLine();
		}
		return res;
	}

	public List<Pet> recuperaTodosPets() {
		try {
			List<Pet> pets = petDAO.recuperarCadastros();
			return pets;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean alterarPet(Pet pet) {
		try {
			petDAO.alterarPet(pet);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deletarPet(Pet pet) {
		try {
			petDAO.deletarPet(pet);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public List<Pet> recuperarPetsPorAtributo(Map<String, String> atributoKeyword1,
			Map<String, String> atributoKeyword2) {
		List<Pet> petFiltrados = petDAO.recuperarPetsPorAtributo(atributoKeyword1,
				atributoKeyword2.isEmpty() ? atributoKeyword1 : atributoKeyword2);

		return petFiltrados;
	}

	public List<Pet> recuperarPetsPorData(List<Integer> data) {
		try {
			return petDAO.recuperarPetsPorData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

/*
 * Cadastrar um novo pet Alterar os dados do pet cadastrado Deletar um pet
 * Listar todos os pets cadastrados Listar pets por algum idade, nome, raça)
 * Sair
 */
