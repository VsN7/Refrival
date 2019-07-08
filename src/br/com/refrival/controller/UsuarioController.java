package br.com.refrival.controller;

import br.com.refrival.util.CriptografiaUtil;
import br.com.refrival.dao.UsuarioDAO;
import br.com.refrival.model.SessaoUsuario;
import br.com.refrival.model.Usuario;
import br.com.refrival.view.CadastroContaView;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vitor
 */
public class UsuarioController {
    private final UsuarioDAO dao;
    private Usuario usuario;
    private List<Usuario> usuarios;
    private SessaoUsuario sessao;
    static int id;
    static String login;

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
    

    public UsuarioController() {
        usuario = new Usuario();
        dao = new UsuarioDAO();
        usuarios = ObservableCollections.observableList(new ArrayList<>());
        pesquisar();
    }
    
    private void pesquisar(){
        usuarios.clear();
        usuarios.addAll(dao.findUsuarioEntities());
    }
    
    public void salvar() throws NoSuchAlgorithmException{
        encriptografarSenhaUsuario();
        dao.create(usuario);
        usuario = new Usuario();
        pesquisar();
    }
    
    public boolean efetuarLogin() throws NoSuchAlgorithmException{
        encriptografarSenhaUsuario();
        if(dao.efetuarLogin(usuario) != null){
            id = dao.retornaId(usuario);
            login = usuario.getLogin();
            
            usuario.setId(dao.retornaId(usuario));
            sessao = new SessaoUsuario(dao.retornaId(usuario), usuario.getLogin());
            CadastroContaView ccs = new CadastroContaView();
            return true;
        }
        else return false;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    private void encriptografarSenhaUsuario() throws NoSuchAlgorithmException {
        usuario.setSenha(CriptografiaUtil.encriptografarSenha(usuario.getSenha()));
    }
    
    public void cancelar(){
        usuario = new Usuario();
    }

    public SessaoUsuario getSessao() {
        return sessao;
    }
    
    public void relatorioUsuariosGeral(){
        dao.relatorioUsuariosGeral();
    }
    
    
}
