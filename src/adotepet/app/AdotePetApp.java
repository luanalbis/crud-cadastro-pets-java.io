package adotepet.app;

import java.util.Scanner;
import java.util.Set;

import adotepet.view.AdotePetAppView;

public class AdotePetApp {
	public static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		Set<String> menuOpcoes = Set.of("0", "1", "2");
	
		System.out.println("\n ----- MENU INICIAL ADOTEPET ----- \n");
		System.out.println("Escolha uma opção válida abaixo \n");

		System.out.println("1 - Inciar Sistema Pets");
		System.out.println("2 - Inciar Sistema edição de Formulários");
		System.out.println("\n0 - Fechar Programa\n");

		System.out.print("Escolha: ");
		String opcao = scanner.nextLine();

		while (!menuOpcoes.contains(opcao)) {
			System.out.print("Escolha uma opção válida: ");
			opcao = scanner.nextLine();
		}

		new AdotePetAppView().encaminharMenu(opcao);
		if (!opcao.equals("0")) {
			main(args);
		}
		System.out.println("\nObrigado por usar nosso programa. E lembre-se, ADOTE UM PET <3!");
		scanner.close();
	}

}
