package ufpb.ayty.model;

public class Alimentacao {
    private int id;
    private int carneId;
    private String tipo;
    private String descricao;

    public Alimentacao(int id, int carneId, String tipo, String descricao) {
        this.id = id;
        this.carneId = carneId;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarneId() {
        return carneId;
    }

    public void setCarneId(int carneId) {
        this.carneId = carneId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
