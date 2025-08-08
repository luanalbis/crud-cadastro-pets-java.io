package adotepet.view.pages;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import adotepet.app.AdotePetApp;
import adotepet.controller.FormularioPetController;
import adotepet.input.FormularioPetInput;

public class FormularioPetPages {
	Scanner scanner = AdotePetApp.scanner;
	FormularioPetController formularioController = new FormularioPetController();
	FormularioPetInput formularioInput = new FormularioPetInput();

	public void processarOpcao(String opcao) {
		switch (opcao) {
		case "1" -> cadastroFormularioPetPage();
		case "2" -> listarPerguntasPage();
		}
	}

	private void cadastroFormularioPetPage() {
		System.out.println("\n----- MENU CADASTRO DE NOVA PERGUNTA -----\n");
		Map<String, String> novaPergunta = formularioInput
				.coletarPerguntaCadastro(formularioController.recuperarFormularioPets());

		String key = novaPergunta.keySet().iterator().next();
		System.out.println("\n" + key.toUpperCase() + " - " + novaPergunta.get(key));
		if (formularioInput.coletaSimOuNao("Deseja cadastrar essa pergunta?Digite apenas 'S' ou 'N': ",
				"Digite apenas 'S' ou 'N': ").equalsIgnoreCase("n")) {
			System.out.println("\n2--- OPERAÇÃO CANCELADA ---\n");
			return;
		}
		if (formularioController.cadastrarPergunta(novaPergunta)) {
			System.out.println("\n--- PERGUNTA ADICIONADA COM SUCESSO ---\n");
			return;
		}
		System.out.println("\n--- ERRO AO CADASTRAR PERGUNTA ---\n");

	}

	private void listarPerguntasPage() {
		final Set<String> FIXAS = Set.of("nome", "tipo", "sexo", "endereco", "idade", "peso", "raca");
		Map<String, String> mAtributoPergunta = formularioController.recuperarFormularioPets();
		Map<String, String> mOpcaoAtributo = new LinkedHashMap<>();
		System.out.println("-\n---- LISTAGEM DO FORMULÁRIO -----\n");
		System.out.println("Perguntas cadastradas: " + mAtributoPergunta.size() + "\n");

		int count = 1;
		for (String key : mAtributoPergunta.keySet()) {
			String fixa = "";
			if (FIXAS.contains(key)) {
				fixa = " (F)";
			}
			System.out.println(count + "-" + key.toUpperCase() + fixa + " - " + mAtributoPergunta.get(key));
			mOpcaoAtributo.put(String.valueOf(count++), key);
		}

		String perguntaEscolhida = formularioInput.coletarPerguntaEscolhida(mAtributoPergunta, mOpcaoAtributo);
		String opcaoAcaoEscolhida = "0";
		if (perguntaEscolhida != null) {
			System.out.println("\n-- MENU ALTERAR OU APAGAR PERGUNTA --\n");
			opcaoAcaoEscolhida = formularioInput.coletarAcaoOpcaoEscolhida(perguntaEscolhida);
		}

		switch (opcaoAcaoEscolhida) {
		case "1" -> editarPerguntasPage(perguntaEscolhida);
		case "2" -> deletarFormularioPetPage(perguntaEscolhida);
		}
	}

	private void deletarFormularioPetPage(String perguntaExclusao) {
		System.out.println("\n----- MENU EXCLUSÂO DE PERGUNTA -----\n");
		System.out.print("Pergunta: ");
		String[] partes = perguntaExclusao.split("=");
		System.out.println(partes[0].toUpperCase() + " - " + partes[1]);
		String res = formularioInput.coletaSimOuNao("Deseja Excluir essa pergunta? 'S' ou 'N'",
				"Responda apenas com 'S' ou 'N'");
		if (res.equalsIgnoreCase("s")) {
			if (formularioController.deletarPergunta(perguntaExclusao)) {
				System.out.println("--- PERGUNTA EXCLUIDA COM SUCESSO ---");
				return;
			}
			System.out.println("--- ERRO AO EXCLUIR PERGUNTA ---");
		} else {
			System.out.println("--- OPERAÇÂO CANCELADA ---");
		}

	}

	private void editarPerguntasPage(String perguntaEdicao) {
		System.out.println("\n----- MENU EDIÇÂO DE PERGUNTA -----\n");
		System.out.print("Pergunta: ");
		String[] partes = perguntaEdicao.split("=");
		System.out.println(partes[0].toUpperCase() + " - " + partes[1]);
		Map<String, String> mAtributoPergunta = formularioInput.coletarPerguntaEdicao(perguntaEdicao,
				formularioController.recuperarFormularioPets());
		String res = formularioInput.coletaSimOuNao("Deseja alterar essa Pergunta? 'S' ou 'N'", "Responda apenas com S ou N");
		if(res.equalsIgnoreCase("s")){
			if(formularioController.editarPet(mAtributoPergunta,perguntaEdicao)) {
				System.out.println("\n----- PERGUNTA ATUALIZADA COM SUCESSO -----\\n ");
				return;
			}
			System.out.println("\n-----  ERRO AO EDITAR PET -----\\n");
		}else{
			System.out.println("\n----- OPERAÇÃO CANCELADA -----\n");
		}
		
		
	}

}
