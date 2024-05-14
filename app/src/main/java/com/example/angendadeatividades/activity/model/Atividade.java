package com.example.angendadeatividades.activity.model;


public class Atividade {
    private String uid_atividade; // ID único da atividade
    private String uid_usuario; // UID do usuário
    private String titulo_atividade;
    private String descricao_atividade;
    private String data_atividade;
    private String hora_atividade;
    private String localizacao_atividade;
    private String participantes_atividade;

    // Construtor
    public Atividade() {
        // Construtor vazio necessário para Firebase
    }

    // Getters e Setters
    public String getUid_atividade() {
        return uid_atividade;
    }

    public void setUid_atividade(String uid_atividade) {
        this.uid_atividade = uid_atividade;
    }

    public String getUid_usuario() {
        return uid_usuario;
    }

    public void setUid_usuario(String uid_usuario) {
        this.uid_usuario = uid_usuario;
    }

    public String getTitulo_atividade() {
        return titulo_atividade;
    }

    public void setTitulo_atividade(String titulo_atividade) {
        this.titulo_atividade = titulo_atividade;
    }

    public String getDescricao_atividade() {
        return descricao_atividade;
    }

    public void setDescricao_atividade(String descricao_atividade) {
        this.descricao_atividade = descricao_atividade;
    }

    public String getData_atividade() {
        return data_atividade;
    }

    public void setData_atividade(String data_atividade) {
        this.data_atividade = data_atividade;
    }

    public String getHora_atividade() {
        return hora_atividade;
    }

    public void setHora_atividade(String hora_atividade) {
        this.hora_atividade = hora_atividade;
    }

    //teste
    public String getLocalizacao_atividade() {
        return localizacao_atividade;
    }

    public void setLocalizacao_atividade(String localizacao_atividade) {
        this.localizacao_atividade = localizacao_atividade;
    }

    public String getParticipantes_atividade() {
        return participantes_atividade;
    }

    public void setParticipantes_atividade(String participantes_atividade) {
        this.participantes_atividade = participantes_atividade;
    }
}