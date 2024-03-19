package com.social.android.sarah.petts.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.social.android.sarah.petts.helper.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String caminhoFoto;
    private String cidade;
    private String bairro;
    private String cep;
    private String telefone;
    private String nomepet;
    private int seguidores = 0;
    private int seguindo = 0;
    private int postagens = 0;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child( getId() );
        usuariosRef.setValue( this );
    }

    public void atualizarQtdPostagem(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuariosRef = firebaseRef
                .child("usuarios")
                .child( getId() );

        HashMap<String, Object> dados = new HashMap<>();
        dados.put("postagens", getPostagens() );

        usuariosRef.updateChildren( dados );

    }


    public void atualizar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();

        Map objeto = new HashMap();
        objeto.put("/usuarios/" + getId() + "/nome", getNome() );
        objeto.put("/usuarios/" + getId() + "/caminhoFoto", getCaminhoFoto() );

        firebaseRef.updateChildren( objeto );

    }

    public Map<String, Object> converterParaMap(){

        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", getEmail() );
        usuarioMap.put("nome", getNome() );
        usuarioMap.put("id", getId() );
        usuarioMap.put("caminhoFoto", getCaminhoFoto() );
        usuarioMap.put("seguidores", getSeguidores() );
        usuarioMap.put("seguindo", getSeguindo() );
        usuarioMap.put("cidade", getCidade() );
        usuarioMap.put("bairro", getBairro() );
        usuarioMap.put("cep", getCep() );
        usuarioMap.put("telefone", getTelefone() );

        return usuarioMap;

    }

    public int getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }

    public int getSeguindo() {
        return seguindo;
    }

    public void setSeguindo(int seguindo) {
        this.seguindo = seguindo;
    }

    public int getPostagens() {
        return postagens;
    }

    public void setPostagens(int postagens) {
        this.postagens = postagens;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cidade = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String cep) { this.telefone = telefone;
    }
}
