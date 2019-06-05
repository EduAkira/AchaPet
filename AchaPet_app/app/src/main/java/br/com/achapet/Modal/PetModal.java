package br.com.achapet.Modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PetModal  implements Serializable {

    public static String COLLECTION_PET_PERDIDO = "petPerdido";
    public static String COLLECTION_PET_ACHADO = "petAchado";

    private String id = "";
    private String tipo = "";
    private String raca = "";
    private String porte = "";
    private String sexo = "";
    private String comentario = "";
    private String collectionPet = "";
    private List<String> cor = new ArrayList<>();
    private List<String> foto = new ArrayList<>();

    public PetModal(){
    }

    public PetModal(String id, String tipo, String raca, String porte, String sexo, String comentario, String collectionPet, List<String> cor, List<String> foto) {
        this.id = id;
        this.tipo = tipo;
        this.raca = raca;
        this.porte = porte;
        this.sexo = sexo;
        this.comentario = comentario;
        this.collectionPet = collectionPet;
        this.cor = cor;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCollectionPet() {
        return collectionPet;
    }

    public void setCollectionPet(String collectionPet) {
        this.collectionPet = collectionPet;
    }

    public List<String> getCor() {
        return cor;
    }

    public void setCor(List<String> cor) {
        this.cor = cor;
    }

    public List<String> getFoto() {
        return foto;
    }
    public void setFoto(List<String> foto) {
        this.foto = foto;
    }

    public void addFoto(String foto){
        this.foto.add(foto);
    }

    public String pethFoto(){
        return "/images/" + collectionPet + "/" + id +"/";
    }


}
