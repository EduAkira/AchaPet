package br.com.achapet.Modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PetModal  implements Serializable {

    public static String COLLECTION_PET_PERDIDO = "petPerdido";
    public static String COLLECTION_PET_ACHADO = "petAchado";

    private String tipo = "";
    private String raca = "";
    private String porte = "";
    private String sexo = "";
    private String comentario = "";
    private List<String> cores = new ArrayList<>();
    private List<String> fotos = new ArrayList<>();

    public PetModal(){
    }

    public PetModal(String tipo, String raca, String porte, String sexo, String comentario, List<String> cores, List<String> fotos) {
        this.tipo = tipo;
        this.raca = raca;
        this.porte = porte;
        this.sexo = sexo;
        this.comentario = comentario;
        this.cores = cores;
        this.fotos = fotos;
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

    public List<String> getCores() {
        return cores;
    }

    public void setCores(List<String> cores) {
        this.cores = cores;
    }

    public void addCor(String cor){
        this.cores.add(cor);
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public void addFoto(String foto){
        this.fotos.add(foto);
    }
}
