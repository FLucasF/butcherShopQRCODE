package ufpb.ayty.model;

import java.sql.Date;
import java.util.List;

public class Carne {
    private int id;
    private String tipo;
    private String origem;
    private String corte;
    private Date validade;
    private String bioma;
    private int idadeAbate;
    private String marmoreio;
    private String certificacaoQualidade;
    private String qrCodeUrl;
    private List<Vacina> vacinas;
    private List<Alimentacao> alimentacoes;
    private List<Certificacao> certificacoes;
    private String dicasPreparo;

    public Carne(int id, String tipo, String origem, String corte, Date validade, String bioma,
                 int idadeAbate, String marmoreio, String certificacaoQualidade, String qrCodeUrl, List<Vacina> vacinas,
                    List<Alimentacao> alimentacoes, List<Certificacao> certificacoes, String dicasPreparo) {
        this.id = id;
        this.tipo = tipo;
        this.origem = origem;
        this.corte = corte;
        this.validade = validade;
        this.bioma = bioma;
        this.idadeAbate = idadeAbate;
        this.marmoreio = marmoreio;
        this.certificacaoQualidade = certificacaoQualidade;
        this.qrCodeUrl = qrCodeUrl;
        this.vacinas = vacinas;
        this.alimentacoes = alimentacoes;
        this.certificacoes = certificacoes;
        this.dicasPreparo = dicasPreparo;
    }

    public Carne() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getCorte() {
        return corte;
    }

    public void setCorte(String corte) {
        this.corte = corte;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public String getBioma() {
        return bioma;
    }

    public void setBioma(String bioma) {
        this.bioma = bioma;
    }

    public int getIdadeAbate() {
        return idadeAbate;
    }

    public void setIdadeAbate(int idadeAbate) {
        this.idadeAbate = idadeAbate;
    }

    public String getMarmoreio() {
        return marmoreio;
    }

    public void setMarmoreio(String marmoreio) {
        this.marmoreio = marmoreio;
    }

    public String getCertificacaoQualidade() {
        return certificacaoQualidade;
    }

    public void setCertificacaoQualidade(String certificacaoQualidade) {
        this.certificacaoQualidade = certificacaoQualidade;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public List<Vacina> getVacinas() {
        return vacinas;
    }

    public void setVacinas(List<Vacina> vacinas) {
        this.vacinas = vacinas;
    }

    public List<Alimentacao> getAlimentacoes() {
        return alimentacoes;
    }

    public void setAlimentacoes(List<Alimentacao> alimentacoes) {
        this.alimentacoes = alimentacoes;
    }

    public List<Certificacao> getCertificacoes() {
        return certificacoes;
    }

    public void setCertificacoes(List<Certificacao> certificacoes) {
        this.certificacoes = certificacoes;
    }

    public String getDicasPreparo() {
        return dicasPreparo;
    }

    public void setDicasPreparo(String dicasPreparo) {
        this.dicasPreparo = dicasPreparo;
    }
}
