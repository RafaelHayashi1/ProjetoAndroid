package com.example.angendadeatividades.activity.model;

public class AtividadeHome {
    private String uid;
    private String titulo;
    private String data;
    private String descricao;
    private String hora;
    private String localizacao;
    private String participantes;

    public  AtividadeHome(String uid, String titulo, String data, String descricao, String hora, String localizacao, String participantes) {
        this.uid = uid;
        this.titulo = titulo;
        this.data = data;
        this.descricao = descricao;
        this.hora = hora;
        this.localizacao = localizacao;
        this.participantes = participantes;
    }

    public String getUid() {
        return uid;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getHora() {
        return hora;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getParticipantes() {
        return participantes;
    }
}

