package adotepet.view;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import adotepet.app.AdotePetApp;
import adotepet.controller.MenuPetController;
import adotepet.view.pages.FormularioPetPages;
import adotepet.view.pages.PetPages;

public class AdotePetAppView {
	private static Scanner scanner = AdotePetApp.scanner;

	private MenuPetController menuPetController = new MenuPetController();

	public void encaminharMenu(String opcao) {
		switch (opcao) {
		case "1" -> iniciarMenuPets();
		case "2" -> iniciarMenuFormulario();
		}
	}

	private void iniciarMenuPets() {
		Map<String, String> opcoesMenuPets = menuPetController.recuperarMenuPets();
		System.out.println("\n----- MENU OPERAÇOES PET -----\n");
		System.out.println("Escolha uma opção válida abaixo \n");

		for (String key : opcoesMenuPets.keySet()) {
			System.out.println(key + "-" + opcoesMenuPets.get(key));
		}

		System.out.print("\nEscolha: ");
		String opcao = scanner.nextLine();
		while (!opcoesMenuPets.containsKey(opcao)) {
			System.out.print("Escolha uma opção VÀLIDA!: ");
			opcao = scanner.nextLine();
		}

		new PetPages().processarOpcao(opcao);
		if (!opcoesMenuPets.get(opcao).equals("Voltar")) {
			iniciarMenuPets();
		}

	}

	private void iniciarMenuFormulario() {
		Set<String> opcoesPermitidas = Set.of("0", "1", "2");
		System.out.println("\n----- MENU OPERAÇÔES FORMULÀRIO -----\n");
		System.out.println("Escolha uma opção válida abaixo \n");

		System.out.println("1 - Adicionar nova pergunta");
		System.out.println("2 - Excluir ou alterar perguntas");
		System.out.println("\n0 - Voltar");

		System.out.print("\nEscolha: ");
		String opcao = scanner.nextLine();
		while (!opcoesPermitidas.contains(opcao)) {
			System.out.print("Escolha uma opção VÀLIDA!: ");
			opcao = scanner.nextLine();
		}

		if (!opcao.equals("0")) {
			new FormularioPetPages().processarOpcao(opcao);
			iniciarMenuFormulario();
		}

		return;
	}
}
