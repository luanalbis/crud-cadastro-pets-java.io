package adotepet.input;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import adotepet.app.AdotePetApp;

public class FormularioPetInput {
	Scanner scanner = AdotePetApp.scanner;

	public Map<String, String> coletarPerguntaCadastro(Map<String, String> formulario) {
		Map<String, String> mapa = new LinkedHashMap<>();

		System.out.print("Informe o que essa pergunta representa (apenas letras, sem espaços): ");
		String key = scanner.nextLine().toLowerCase();

		while (formulario.containsKey(key) || key.isBlank() || !key.matches("^[a-zA-Z]+$")) {
			System.out.print("Digite seguindo as especificações");
			if (formulario.containsKey(key)) {
				System.out.print(", campo '" + key.toUpperCase() + "' já está cadastrado: ");
			} else {
				System.out.print(" (apenas letras, sem espaços e sem simbolos): ");
			}
			key = scanner.nextLine().toLowerCase();
		}

		System.out.print("\nInforme a pergunta: ");
		String pergunta = scanner.nextLine();
		while (pergunta.isBlank() || pergunta.contains("=")) {
			System.out.print("Digite algo válido (sem usar o símbolo '='): ");
			pergunta = scanner.nextLine();
		}

		mapa.put(key.trim(), pergunta.trim());
		return mapa;
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

	public String coletarPerguntaEscolhida(Map<String, String> mAtributoPergunta, Map<String, String> mOpcaoAtributo) {
		mOpcaoAtributo.put("0", "VOLTAR");

		final Set<String> FIXAS = Set.of("nome", "tipo", "sexo", "endereco", "idade", "peso", "raca");

		System.out.println("\nDigite o numero da PERGUNTA para excluir ou alterar.");
		System.out.println("Não é permitido alterar ou excluir as perguntas marcadas como '(F)'");

		System.out.println("\nDigite 0 para voltar");
		System.out.print("\nEscolha: ");

		String res = scanner.nextLine();
		while (!mOpcaoAtributo.containsKey(res) && !res.equals("0") || FIXAS.contains(mOpcaoAtributo.get(res))) {
			System.out.print("Digite uma opção válida!: ");
			res = scanner.nextLine();
		}

		if (!res.equals("0")) {
			String atributo = mOpcaoAtributo.get(res);
			return atributo + "=" + mAtributoPergunta.get(atributo);
		}

		return null;
	}

	public String coletarAcaoOpcaoEscolhida(String perguntaEscolhida) {
		Set<String> respostaValida = Set.of("1", "2", "0");

		String[] partes = perguntaEscolhida.split("=");
		System.out.println("Pergunta: " + partes[0].toUpperCase() + " - " + partes[1]);

		System.out.println("\n1 - Editar pergunta:");
		System.out.println("2 - Excluir pergunta");
		System.out.println("0 - Voltar");

		System.out.print("\nEscolha uma opçao válida: ");
		String res = scanner.nextLine();
		while (!respostaValida.contains(res)) {
			System.out.print("Escolha uma opção válida: ");
			res = scanner.nextLine();
		}
		return res;
	}

	public Map<String, String> coletarPerguntaEdicao(String perguntaOriginal, Map<String, String> mAtributoPergunta) {
		String atributo = perguntaOriginal.split("=")[0];
		String pergunta = perguntaOriginal.split("=")[1];
		mAtributoPergunta.remove(perguntaOriginal.split("=")[0]);

		Map<String, String> mapaParaEditar = new LinkedHashMap<>();
		String resEditarAtributo = coletaSimOuNao("Deseja editar o atributo? 'S' ou 'N'",
				"Responda apenas com 'S' ou 'N'").toLowerCase();
		String resEditarPergunta = coletaSimOuNao("Deseja editar a pergunta? 'S' ou 'N'",
				"Responda apenas com 'S' ou 'N'").toLowerCase();

		if (resEditarAtributo.equals("s")) {
			System.out.print("Informe o que essa pergunta representa (apenas letras, sem espaços): ");
			atributo = scanner.nextLine().toLowerCase();

			while (mAtributoPergunta.containsKey(atributo) || atributo.isBlank() || !atributo.matches("^[a-zA-Z]+$")) {
				System.out.print("Digite seguindo as especificações");
				if (mAtributoPergunta.containsKey(atributo)) {
					System.out.print(", campo '" + atributo.toUpperCase() + "' já está cadastrado: ");
				} else {
					System.out.print(" (apenas letras, sem espaços e sem simbolos): ");
				}
				atributo = scanner.nextLine().toLowerCase();
			}
		}
		if (resEditarPergunta.equals("s")) {
			System.out.print("\nInforme a nova pergunta: ");
			pergunta = scanner.nextLine();
			while (pergunta.isBlank() || pergunta.contains("=")) {
				System.out.print("Digite algo válido (sem usar o símbolo '='): ");
				pergunta = scanner.nextLine();
			}
		}
		
		mapaParaEditar.put(atributo.trim(), pergunta.trim());
		System.out.println(mapaParaEditar);
		return mapaParaEditar;
	}
}