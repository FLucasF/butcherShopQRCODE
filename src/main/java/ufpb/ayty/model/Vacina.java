package ufpb.ayty.model;

import java.util.Date;

public class Vacina {
    private int id;
    private int carneId;
    private String nome;
    private Date dataAplicacao;

    public Vacina(int id, int carneId, String nome, Date dataAplicacao) {
        this.id = id;
        this.carneId = carneId;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
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

    public Date getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(Date dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }
}
