package ufpb.ayty.model;

public class Certificacao {
    private int id;
    private int carneId;
    private String nome;
    private String descricao;

    public Certificacao(int id, int carneId, String nome, String descricao) {
        this.id = id;
        this.carneId = carneId;
        this.nome = nome;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
