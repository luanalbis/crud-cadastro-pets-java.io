package adotepet.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FormularioPetDAO {
	private static final String CAMINHO_FORMULARIO_PET = "/home/luan/eclipse-workspace/AdotePet/src/adotepet/database/formulariopet.txt";

	public Map<String, String> recuperarFormularioPet() {
		Map<String, String> perguntas = new LinkedHashMap<>();

		String linha;

		try (var reader = new BufferedReader(new FileReader(CAMINHO_FORMULARIO_PET))) {

			while ((linha = reader.readLine()) != null) {
				String[] partes = linha.split("=");
				perguntas.put(partes[0], partes[1]);
			}

			return perguntas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void salvarNovaPergunta(Map<String, String> novaPergunta) {
		File novoArquivo = new File(CAMINHO_FORMULARIO_PET);

		try (var writer = new BufferedWriter(new FileWriter(novoArquivo, true))) {

			for (String key : novaPergunta.keySet()) {

				writer.write(key + "=" + novaPergunta.get(key));
				writer.newLine();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void deletarPergunta(String perguntaExclusao) {
		Map<String, String> mAtributoPergunta = recuperarFormularioPet();
		String key = perguntaExclusao.split("=")[0];
		mAtributoPergunta.remove(key);

		try (var writer = new BufferedWriter(new FileWriter(new File(CAMINHO_FORMULARIO_PET)))) {
			for (String atributo : mAtributoPergunta.keySet()) {
				System.out.println(atributo + " - " + mAtributoPergunta.get(atributo));

				writer.write(atributo + "=" + mAtributoPergunta.get(atributo));
				writer.newLine();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void alterarPergunta(Map<String, String> mAtributoPergunta, String perguntaOriginal) {
		Map<String, String> mFormularioOriginal = recuperarFormularioPet();
		Map<String, String> mFormularioNovo = new LinkedHashMap<>();
		String chaveOriginal = perguntaOriginal.split("=")[0];
		String atributoParaEditar = mAtributoPergunta.keySet().iterator().next();
		String perguntaParaEditar = mAtributoPergunta.get(atributoParaEditar);

		for (String key : mFormularioOriginal.keySet()) {
			if (!key.equals(chaveOriginal)) {
				mFormularioNovo.put(key, mFormularioOriginal.get(key));
			} else {
				mFormularioNovo.put(atributoParaEditar, perguntaParaEditar);
			}
		}

		try (var writer = new BufferedWriter(new FileWriter(new File(CAMINHO_FORMULARIO_PET)))) {
			for (String atributo : mFormularioNovo.keySet()) {
				System.out.println(atributo + " - " + mFormularioNovo.get(atributo));

				writer.write(atributo + "=" + mFormularioNovo.get(atributo));
				writer.newLine();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
