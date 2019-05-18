package br.com.achapet.Modal;

import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PetModal {

    private String nome;
    private String descricao;
    private String date;
    private List<Uri> foto;

    public PetModal(String nome, String descricao, String date) {
        this.nome = nome;
        this.descricao = descricao;
        this.date = date;
        this.foto = new ArrayList<>();
    }

    public PetModal(){
        this.foto = new ArrayList<>();
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

    public void setFoto(List<Uri> foto) {
        this.foto = foto;
    }

    public void adicionarFoto(Uri url){
        foto.add(url);
    }
}
