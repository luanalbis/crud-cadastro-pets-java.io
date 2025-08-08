package adotepet.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import adotepet.model.Pet;

public class PetDAO {
	private static final String CAMINHO_PATH_CADASTROS = "/home/luan/eclipse-workspace/AdotePet/src/adotepet/database/pathcadastros.txt";

	private static final String CAMINHO_CADASTROS = "/home/luan/eclipse-workspace/AdotePet/src/adotepet/database/cadastros/";

	public void cadastrarPet(Pet pet) {
		String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm"));
		String nomeArquivo = data + "-" + pet.getDados().get("nome").toUpperCase().replace(" ", "").trim() + ".txt";
		File novoArquivo = new File(CAMINHO_CADASTROS + nomeArquivo);

		try (var writer = new BufferedWriter(new FileWriter(novoArquivo))) {
			writer.write("nomeArquivo=" + nomeArquivo);
			writer.newLine();
			for (String key : pet.getDados().keySet()) {
				writer.write(key + "=" + pet.getDados().get(key));
				writer.newLine();
			}

			salvarPathNovoArquivo(nomeArquivo);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Pet> recuperarCadastros() {

		List<String> listaPaths = recuperarPathCadastros();
		List<Pet> petsCadastrados = new ArrayList<>();

		for (String path : listaPaths) {
			try (var reader = new BufferedReader(new FileReader(CAMINHO_CADASTROS + path))) {
				String linha;
				Map<String, String> petDados = new LinkedHashMap<>();
				while ((linha = reader.readLine()) != null) {
					String[] partes = linha.split("=");
					petDados.put(partes[0], partes[1]);
				}
				petsCadastrados.add(new Pet(petDados));

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return petsCadastrados;
	}

	public void deletarPet(Pet pet) {
		List<String> paths = recuperarPathCadastros();
		try (var writer = new BufferedWriter(new FileWriter(CAMINHO_PATH_CADASTROS))) {

			String nomeArquivo = pet.getDados().get("nomeArquivo");

			File arquivo = new File(CAMINHO_CADASTROS + nomeArquivo);
			arquivo.delete();

			paths.removeIf(path -> path.trim().equals(nomeArquivo.trim()));

			for (String path : paths) {
				writer.write(path);
				writer.newLine();

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private List<String> recuperarPathCadastros() {
		List<String> paths = new ArrayList<>();
		String linha;

		try (var reader = new BufferedReader(new FileReader(CAMINHO_PATH_CADASTROS))) {

			while ((linha = reader.readLine()) != null) {
				paths.add(linha);
			}

			return paths;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void salvarPathNovoArquivo(String nomeArquivo) {
		try (var writerPathCadastro = new BufferedWriter(new FileWriter(CAMINHO_PATH_CADASTROS, true));) {
			writerPathCadastro.write(nomeArquivo);
			writerPathCadastro.newLine();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Pet> recuperarPetsPorAtributo(Map<String, String> chaveKeyword1, Map<String, String> chaveKeyword2) {
		List<Pet> pets = recuperarCadastros();
		List<Pet> petFiltrados = new ArrayList<>();
		System.out.println("DEBUG - " + chaveKeyword1);
		System.out.println("DEBUG - " + chaveKeyword2);

		String chave1 = chaveKeyword1.keySet().iterator().next();
		String keyword1 = chaveKeyword1.get(chave1).toLowerCase();

		String chave2 = chaveKeyword2.keySet().iterator().next();
		String keyword2 = chaveKeyword2.get(chave2).toLowerCase();

		for (Pet pet : pets) {
			if (pet.getDados().containsKey(chave1) && pet.getDados().containsKey(chave2)) {
				Map<String, String> dados = pet.getDados();
				String atributo1 = dados.get(chave1).toLowerCase();
				String atributo2 = dados.get(chave2).toLowerCase();

				boolean filtrou;

				if (chave1.equals(chave2)) {
					filtrou = atributo1.contains(keyword1);
				} else {
					filtrou = atributo1.contains(keyword1) && atributo2.contains(keyword2);
				}

				if (filtrou) {
					petFiltrados.add(pet);
				}
			}
		}

		return petFiltrados;
	}

	public void alterarPet(Pet pet) {

		String nomeArquivo = pet.getDados().get("nomeArquivo");
		try (var writer = new BufferedWriter(new FileWriter(CAMINHO_CADASTROS + nomeArquivo))) {

			for (String key : pet.getDados().keySet()) {
				writer.write(key + "=" + pet.getDados().get(key));
				writer.newLine();
			}

		} catch (Exception e) {
			System.out.println("Não foi possivel editar arquivo");
			e.printStackTrace();
		}
	}

	public List<Pet> recuperarPetsPorData(List<Integer> lData) {
		int anoPesquisa = lData.get(0), mesPesquisa = lData.get(1), diaPesquisa = lData.get(2);
		List<Pet> petFiltrados = new ArrayList<>();
		List<Pet> petsCadastrados = recuperarCadastros();
		for (Pet pet : petsCadastrados) {
			String dataCadastro = pet.getDados().get("nomeArquivo").split("-")[0];
			int anoCadastro = Integer.valueOf(dataCadastro.substring(0, 4));
			int mesCadastro = Integer.valueOf(dataCadastro.substring(4, 6));
			int diaCadastro = Integer.valueOf(dataCadastro.substring(6, 8));

			mesPesquisa = mesPesquisa == 0 ? mesCadastro : mesPesquisa;
			diaPesquisa = diaPesquisa == 0 ? diaCadastro : diaPesquisa;
			
			if(anoPesquisa == anoCadastro && mesPesquisa == mesCadastro &&  diaPesquisa == diaCadastro) {
				petFiltrados.add(pet);
			}
		}
		return petFiltrados;
	}
}
