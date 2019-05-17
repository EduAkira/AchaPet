package br.com.achapet.Modal;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PetModal {

    private String nome;
    private String descricao;
    private String date;
    private List<URI> foto = new ArrayList<>();

    public PetModal(String nome, String descricao, String date) {
        this.nome = nome;
        this.descricao = descricao;
        this.date = date;
    }

    public PetModal() {

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

    public List getFoto() {
        return foto;
    }

    public void setFoto(List<URI> foto) {
        this.foto = foto;
    }

    public void adicionarFoto(URI url){
        foto.add(url);
    }
}
