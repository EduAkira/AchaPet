package br.com.achapet.Modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PetModal  implements Serializable {

    public static String COLLECTION_PET_PERDIDO = "petPerdido";
    public static String COLLECTION_PET_ACHADO = "petAchado";

    private String tipo = "";
    private String raca = "";
    private String prote = "";
    private String sexo = "";
    private String comentario = "";
    private String collectionPet = "";
    private List<String> cor = new ArrayList<>();
    private List<String> foto = new ArrayList<>();

    public PetModal(){
    }

    public PetModal(String tipo, String raca, String prote, String sexo, String comentario, String status, String collectionPet, List<String> cor, List<String> foto) {
        this.tipo = tipo;
        this.raca = raca;
        this.prote = prote;
        this.sexo = sexo;
        this.comentario = comentario;
        this.collectionPet = collectionPet;
        this.cor = cor;
        this.foto = foto;
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

    public String getProte() {
        return prote;
    }

    public void setProte(String prote) {
        this.prote = prote;
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

    public void setCollectionPet(int collectionPet) {
        if(collectionPet == 1){
            this.collectionPet = COLLECTION_PET_ACHADO;
        }else{
            this.collectionPet = COLLECTION_PET_PERDIDO;
        }
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

    @Override
    public String toString() {
        return "PetModal{" +
                "tipo='" + tipo + '\'' +
                ", raca='" + raca + '\'' +
                ", prote='" + prote + '\'' +
                ", sexo='" + sexo + '\'' +
                ", comentario='" + comentario + '\'' +
                ", collectionPet='" + collectionPet + '\'' +
                ", cor=" + cor +
                ", foto=" + foto +
                '}';
    }
}
