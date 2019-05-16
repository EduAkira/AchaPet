package br.com.achapet.Modal;

public class PetModal {

    private String nome;
    private String descricao;
    private String date;

    public PetModal(String nome, String descricao, String date) {
        this.nome = nome;
        this.descricao = descricao;
        this.date = date;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDate() {
        return date;
    }
}
