/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.refrival.model;

import java.util.Calendar;

/**
 *
 * @author vitor
 */
public class SessaoUsuario {
    private Integer id;
    private String login;
    private Calendar dataLogin;
    
    public SessaoUsuario(Integer id,String login){
        this.id = id;
        this.login = login;
        dataLogin = Calendar.getInstance();
    }
    public SessaoUsuario(){
        
    }

    public Calendar getDataLogin() {
        return dataLogin;
    }

    public void setDataLogin(Calendar dataLogin) {
        this.dataLogin = dataLogin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
