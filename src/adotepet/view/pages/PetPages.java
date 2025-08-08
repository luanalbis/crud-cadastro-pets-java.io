package adotepet.view.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import adotepet.app.AdotePetApp;
import adotepet.controller.FormularioPetController;
import adotepet.controller.PetController;
import adotepet.input.PetInput;
import adotepet.model.Pet;

public class PetPages {
	private Scanner scanner = AdotePetApp.scanner;
	private PetController petController = new PetController();
	private FormularioPetController formularioPetController = new FormularioPetController();
	private PetInput petInput = new PetInput();

	public void processarOpcao(String opcao) {
		switch (opcao) {
		case "1" -> cadastroPetPage();
		case "2" -> listarPetsPage();
		case "3" -> pesquisarPetPage();
		case "4" -> pesquisarPetPorDataPage();

		}
	}

	private void cadastroPetPage() {
		System.out.println("\n---- CADASTRO DE PET ----\n");
		Map<String, String> mapaPerguntas = formularioPetController.recuperarFormularioPets();
		Pet pet = new Pet(new LinkedHashMap<>());
		for (String key : mapaPerguntas.keySet()) {

			System.out.print(mapaPerguntas.get(key) + ": ");
			String atributo;
			if (key.equals("endereco")) {
				System.out.println();
				atributo = petInput.coletaEndereco();
			} else {
				atributo = petInput.formularioCadastro(key, scanner.nextLine());
			}

			while (atributo == null) {
				System.out.print(mapaPerguntas.get(key) + ": ");
				atributo = petInput.formularioCadastro(key, scanner.nextLine());
			}
			pet.getDados().put(key, atributo);
		}

		System.out.println();
		for (String key : pet.getDados().keySet()) {
			System.out.println(key.toUpperCase() + " - " + pet.getDados().get(key));
		}

		String res = petInput.coletaSimOuNao("\nDeseja cadastrar esse PET?(Reponda com 'S' para SIM ou 'N' para NÂO: ",
				"\nPet cadastrado com sucesso!\n");

		if (res.equalsIgnoreCase("s")) {
			if (petController.cadastraPet(pet)) {
				System.out.println("\nPet cadastrado com sucesso!");
				return;
			}
			System.out.println("\nNão possível cadastrar o PET!");
		}
	}

	private void listarPetsPage() {
		List<Pet> pets = petController.recuperaTodosPets();
		System.out.println("\n----- MENU PETS CADASTRADOs -----\n");
		System.out.println("Pets cadastrados: " + pets.size() + "\n");
		int count = 1;
		Map<String, Pet> mapaMenuPets = new LinkedHashMap<>();
		for (Pet pet : pets) {
			mapaMenuPets.put(String.valueOf(count), pet);
			System.out.println(count++ + " - " + pet.getDados().get("nome"));

		}
		if (pets.size() > 0) {
			Pet petEscolhido = petInput.menuEscolherPet(mapaMenuPets);
			if (petEscolhido == null)
				return;

			processarAcaoPetEscolhido(petInput.menuAcaoComPet(petEscolhido), petEscolhido);
		}
	}

	private void pesquisarPetPage() {
		Map<String, String> mapaOpcoesAtributo = new LinkedHashMap<>();
		Map<String, String> atributoKey1 = new LinkedHashMap<>();
		Map<String, String> atributoKey2 = new LinkedHashMap<>();

		System.out.println("\n----- MENU PESQUISAR PET -----\n");
		System.out.println("Você pode combinar até 2 atributos para pesquisar");

		String combinar = petInput.coletaSimOuNao("Combinar atributos? 'S' para SIM e 'N' para NÂO: ",
				"Apenas 'S' ou 'N'");

		mapaOpcoesAtributo.put("0", "- VOLTAR");
		System.out.println("\n0 - VOLTAR");
		int i = 1;
		for (String key : formularioPetController.recuperarFormularioPets().keySet()) {
			mapaOpcoesAtributo.put(String.valueOf(i), key);
			System.out.println(i + " - " + key.toUpperCase());
			i++;
		}

		atributoKey1 = petInput.capturarAtributoEKeyword(mapaOpcoesAtributo,
				"\nDigite o número de um atributo VÁLIDO: ", null);
		atributoKey2 = atributoKey1;
		if (atributoKey1 == null) {
			return;
		}

		String atributoEscolhido = atributoKey1.keySet().iterator().next();

		if (combinar.equalsIgnoreCase("s")) {
			atributoKey2 = petInput.capturarAtributoEKeyword(mapaOpcoesAtributo,
					"\nDigite o número de um segundo atributo VÁLIDO: ", atributoEscolhido);
			if (atributoKey2 == null) {
				return;
			}
		}

		List<Pet> petFiltrados = petController.recuperarPetsPorAtributo(atributoKey1, atributoKey2);

		Map<String, Pet> mapaPetFiltrado = new LinkedHashMap<>();

		System.out.println("\nPETS ENCONTRADOS COM ESSA PESQUISA: " + petFiltrados.size() + "\n");
		int count = 1;
		for (Pet p : petFiltrados) {
			System.out.println(count + " - " + p.getDados().get("nome"));
			mapaPetFiltrado.put(String.valueOf(count++), p);

		}
		if (mapaPetFiltrado.size() > 0) {
			Pet petEscolhido = petInput.menuEscolherPet(mapaPetFiltrado);
			if ((petEscolhido) == null)
				return;
			processarAcaoPetEscolhido(petInput.menuAcaoComPet(petEscolhido), petEscolhido);

		}
	}

	private void deletarPetPage(Pet pet) {
		System.out.println("\n----- DELETAR PET-----\n");

		String res = petInput.coletaSimOuNao("Após a confirmação, esse PET será excluído.\n"
				+ "Digite 'S' para DELETAR o PET" + " e 'N' para CANCELAR operação", "Digite uma escolha VÀLIDA: ");

		if (res.equalsIgnoreCase("s")) {
			if (petController.deletarPet(pet)) {
				System.out.println("\n---- PET DELETADO COM SUCESSO ----\n");
			} else {
				System.out.println("\n---- NÂO FOI POSSÌVEL DELETAR O PET ----\n");
			}
			return;

		}
		System.out.println("\n---- OPERAÇÂO CANCELADA ----\n");
	}

	private void alterarPetPage(Pet pet) {
		Map<String, String> mapaFormularioPet = formularioPetController.recuperarFormularioPets();
		final Set<String> IMUTAVEIS = Set.of("tipo", "sexo");
		Map<String, String> mapaOpcoesMenu = new LinkedHashMap<>();

		System.out.println("\n----- MENU EDIÇÂO DO PET -----\n");

		int count = 1;
		mapaOpcoesMenu.put(String.valueOf(0), "CANCELAR");
		System.out.println(" 0 - CANCELAR");
		for (String key : mapaFormularioPet.keySet()) {
			if (!IMUTAVEIS.contains(key)) {
				mapaOpcoesMenu.put(String.valueOf(count), key);
				String atributo = mapaOpcoesMenu.get(String.valueOf(count)).toUpperCase();
				String atributoPreenchido = pet.getDados().get(key);
				System.out.println(" " + count + " - " + atributo + ": " + atributoPreenchido);

				count++;
			}
		}

		System.out.print("\n'S'- SALVAR todas as alterações\n");

		System.out.print("\nEscolha uma opção para EDITAR, CANCELAR ou SALVAR: ");

		String opcaoEscolhida = scanner.nextLine();
		while (!mapaOpcoesMenu.containsKey(opcaoEscolhida) && !opcaoEscolhida.equals("0")
				&& !opcaoEscolhida.equalsIgnoreCase("s")) {
			System.out.print("Dígite uma opção válida!");
			opcaoEscolhida = scanner.nextLine();
		}

		if (opcaoEscolhida.equals("0")) {
			System.out.println("\n-- OPERAÇÃO CANCELADA --\n");
			return;
		}

		if (opcaoEscolhida.equalsIgnoreCase("S")) {
			System.out.println("\n-- PET ATUALIZADO COM SUCESSO --\n");
			petController.alterarPet(pet);
			return;
		}

		String atributoValidar = mapaOpcoesMenu.get(opcaoEscolhida);
		String atributo;
		System.out.print("Digite a atualização de " + atributoValidar.toUpperCase() + ": ");
		if (atributoValidar.equals("endereco")) {
			System.out.println();
			atributo = petInput.coletaEndereco();
		} else {
			atributo = scanner.nextLine();
		}

		while (atributo == null) {
			System.out.print(atributoValidar + ": ");
			atributo = petInput.formularioCadastro(atributoValidar, scanner.nextLine());
		}

		pet.getDados().put(atributoValidar, atributo);
		alterarPetPage(pet);
	}

	private void processarAcaoPetEscolhido(String escolhaAcao, Pet pet) {
		switch (escolhaAcao) {
		case "1" -> alterarPetPage(pet);
		case "2" -> deletarPetPage(pet);
		}
	}

	private void pesquisarPetPorDataPage() {
		System.out.println("\n----- MENU DE BUSCA POR DATA -----\n");

		List<Integer> data = petInput.coletarData();

		List<Pet> petFiltrados = petController.recuperarPetsPorData(data);
		Map<String, Pet> mapaPetFiltrado = new LinkedHashMap<>();
		System.out.println("\nPETS ENCONTRADOS COM ESSA PESQUISA: " + petFiltrados.size() + "\n");
		int count = 1;
		for (Pet p : petFiltrados) {
			System.out.println(count + " - " + p.getDados().get("nome"));
			mapaPetFiltrado.put(String.valueOf(count++), p);

		}
		if (mapaPetFiltrado.size() > 0) {
			Pet petEscolhido = petInput.menuEscolherPet(mapaPetFiltrado);
			if ((petEscolhido) == null)
				return;
			processarAcaoPetEscolhido(petInput.menuAcaoComPet(petEscolhido), petEscolhido);

		} else {
			System.out.println("\n----- NÃO FORAM ENCONTRADOS PETS COM ESSA PESQUISA -----\n");
		}
	}
}
