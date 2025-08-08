package adotepet.input;

import java.time.Year;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import adotepet.app.AdotePetApp;
import adotepet.model.Pet;
import adotepet.model.enums.SexoEnum;
import adotepet.model.enums.TipoEnum;

public class PetInput {
	private Scanner scanner = AdotePetApp.scanner;
	private static final String NAO_INFORMADO = "NÃO INFORMADO";

	public String formularioCadastro(String key, String atributo) {
		final Set<String> NÂO_PERMITIDO_VAZIOS = Set.of("sexo", "tipo", "nome");
		if (atributo == null || atributo.trim().isEmpty()) {
			if (!NÂO_PERMITIDO_VAZIOS.contains(key)) {
				return NAO_INFORMADO;
			}
			return null;
		}

		atributo = atributo.trim();

		if (key.equals("nome")) {
			if (!atributo.matches("^\\p{L}+(\\s+\\p{L}+)+$")) {
				System.out.println("O nome deve conter nome e sobrenome, sem caracteres especiais.");
				return null;
			}
			return atributo;
		}

		if (key.equals("tipo")) {
			if (atributo.equalsIgnoreCase("c") || atributo.equalsIgnoreCase("g")) {
				switch (atributo.toLowerCase()) {
				case "c" -> atributo = TipoEnum.C.getDescricao();
				case "g" -> atributo = TipoEnum.G.getDescricao();
				}
				return atributo;
			}
			System.out.println("TipoEnum inválido. Use 'c' para cachorro ou 'g' para gato.");
			return null;
		}

		if (key.equals("sexo")) {

			if (atributo.equalsIgnoreCase("m") || atributo.equalsIgnoreCase("f")) {
				switch (atributo.toLowerCase()) {
				case "m" -> atributo = SexoEnum.M.getDescricao();
				case "f" -> atributo = SexoEnum.F.getDescricao();
				}
				return atributo;
			}
			System.out.println("Sexo inválido. Use 'm' para macho ou 'f' para fêmea.");
			return null;
		}

		if (key.equals("endereco")) {
			coletaEndereco();
			System.out.println("Reposta inválida. User 'S' para SIM e 'N' para não ");
			return null;
		}

		if (key.equals("peso")) {

			try {
				atributo = atributo.replace(",", ".");
				double peso = Double.parseDouble(atributo);
				if (peso < 0.5 || peso > 60) {
					System.out.println("Peso deve estar entre 0.5kg e 60kg.");
					return null;
				}

				atributo = String.valueOf(peso).replace(".", ",") + "KG";
				return atributo;
			} catch (NumberFormatException e) {
				System.out.println("Peso inválido. Digite um número válido com vírgula ou ponto.");
				return null;
			}
		}

		if (key.equals("idade")) {
			try {
				atributo = atributo.replace(",", ".");
				double idade = Double.parseDouble(atributo);
				if (idade > 20) {
					System.out.println("Idade não pode ser maior que 20 anos.");
					return null;
				} else if (idade < 0) {
					System.out.println("Idade não pode ser menor que 0");
					return null;
				}

				atributo = String.valueOf(idade).replace(".", ",") + " Anos";
				return atributo;

			} catch (NumberFormatException e) {
				System.out.println("Idade inválida. Digite um número válido com vírgula ou ponto.");
				return null;
			}
		}

		if (key.equals("raca")) {
			if (!atributo.matches("^[A-Za-z ]+$")) {
				System.out.println("Raça inválida. Use apenas letras.");
				return null;
			}
			return atributo;
		}

		if (atributo == null || atributo.isEmpty()) {
			atributo = NAO_INFORMADO;
		}

		return atributo;
	}

	public String coletaEndereco() {
		System.out.print("Rua: ");
		String rua = scanner.nextLine().trim();
		while (rua.isEmpty()) {
			System.out.print("Rua não pode ser vazia. Digite novamente: ");
			rua = scanner.nextLine().trim();
		}

		System.out.print("Número da casa: ");
		String numeroCasa = scanner.nextLine().trim();
		if (numeroCasa.trim().isEmpty()) {
			numeroCasa = NAO_INFORMADO;
		}

		System.out.print("Cidade: ");
		String cidade = scanner.nextLine().trim();
		while (cidade.isEmpty()) {
			System.out.print("Cidade não pode ser vazia. Digite novamente: ");
			cidade = scanner.nextLine().trim();
		}

		return rua + ", " + numeroCasa + ", " + cidade;
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

	public Pet menuEscolherPet(Map<String, Pet> menuNomePets) {
		System.out.println("\nDigite o numero do PET para ver detalhes, excluir ou alterar.");
		System.out.println("Digite 0 para voltar");
		System.out.print("Escolha: ");
		String res = scanner.nextLine();
		while (!menuNomePets.containsKey(res) && !res.equals("0")) {
			System.out.print("Digite uma opção válida!: ");
			res = scanner.nextLine();
		}
		if (menuNomePets.containsKey(res)) {
			System.out.println("Pet escolhido: " + res + " - " + menuNomePets.get(res).getDados().get("nome") + "\n");
			return menuNomePets.get(res);
		}

		return null;
	}

	public String menuAcaoComPet(Pet pet) {
		System.out.println("\n----- DETALHE DO PET -----\n");
		Set<String> respostasAceitas = Set.of("0", "1", "2");
		for (String key : pet.getDados().keySet()) {
			if (!key.equals("nomeArquivo")) {
				System.out.println(key.toUpperCase() + " - " + pet.getDados().get(key));
			}
		}

		System.out.println("\nDigite 1 para ALTERAR o PET.");
		System.out.println("Digite 2 para DELETAR o PET");
		System.out.println("Digite 0 para VOLTAR");

		System.out.print("\nEscolha: ");
		String res = scanner.nextLine();
		while (!respostasAceitas.contains(res)) {
			System.out.print("Digite uma escolha VÀLIDA: ");
			res = scanner.nextLine();
		}

		return res;

	}

	public Map<String, String> capturarAtributoEKeyword(Map<String, String> opcoes, String mensagem,
			String opcaoJaEscolhida) {

		System.out.print(mensagem);
		String res = scanner.nextLine();
		while (!opcoes.containsKey(res) && !res.equals("0") || opcoes.get(res).equals(opcaoJaEscolhida)) {
			System.out.print(
					"Digite um número válido" + (opcaoJaEscolhida != null ? " e diferente do anterior" : "") + ": ");
			res = scanner.nextLine();
		}

		if (!res.equals("0")) {
			System.out.print("Pesquisar por " + opcoes.get(res).toUpperCase() + ": ");
			String keyword = scanner.nextLine();
			while (keyword == null || keyword.trim().isEmpty()) {
				System.out.print("Digite algo! ");
				keyword = scanner.nextLine();
			}
			return new LinkedHashMap<>(Map.of(opcoes.get(res), keyword));
		}
		return null;
	}

	public List<Integer> coletarData() {
		int anoAtual = Year.now().getValue();
		int ano = 0, mes = 0, dia = 0;

		System.out.print("Digite o ano entre " + 2024 + " e " + anoAtual + ". (obrigatório): ");
		String entrada = scanner.nextLine();

		while (!entrada.matches("\\d+") || !range(Integer.parseInt(entrada), 2024, anoAtual)) {
			System.out.print("Ano inválido! Digite um ano numérico entre " + 2024 + " e " + anoAtual + ": ");
			entrada = scanner.nextLine();
		}

		ano = Integer.parseInt(entrada);

		System.out.print("Digite o mês (0 a 12, 0 se não quiser informar): ");
		entrada = scanner.nextLine();

		while (!entrada.matches("\\d+") || !range(Integer.parseInt(entrada), 0, 12)) {
			System.out.print("Mês inválido! Digite um número entre 0 e 12: ");
			entrada = scanner.nextLine();
		}

		mes = entrada.isEmpty() ? 0 : Integer.parseInt(entrada);

		if (mes != 0) {
			System.out.print("Digite o dia (0 a 31, 0 se não quiser informar): ");
			entrada = scanner.nextLine();

			while (!entrada.matches("\\d+") || !range(Integer.parseInt(entrada), 0, 31)) {
				System.out.print("Dia inválido! Digite um número entre 0 e 31: ");
				entrada = scanner.nextLine();
			}
		}

		if (entrada.isEmpty())
			entrada = "0";

		dia = entrada.isEmpty() ? 0 : Integer.parseInt(entrada);

		return List.of(ano, mes, dia);
	}

	private boolean range(int valor, int min, int max) {
		return valor >= min && valor <= max;
	}

}
