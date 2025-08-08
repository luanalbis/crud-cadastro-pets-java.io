package adotepet.controller;

import java.util.Map;

import adotepet.dao.FormularioPetDAO;

public class FormularioPetController {
	private FormularioPetDAO dao = new FormularioPetDAO();

	public Map<String, String> recuperarFormularioPets() {
		try {
			return dao.recuperarFormularioPet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean cadastrarPergunta(Map<String, String> novaPergunta) {
		try {
			dao.salvarNovaPergunta(novaPergunta);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deletarPergunta(String perguntaExclusao) {
		try {
			dao.deletarPergunta(perguntaExclusao);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean editarPet(Map<String, String> mAtributoPergunta, String perguntaOriginal) {
		try {
			dao.alterarPergunta(mAtributoPergunta, perguntaOriginal);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}return false;
	}
}
