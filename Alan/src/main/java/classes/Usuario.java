/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import util.Criptografia;

/**
 *
 * @author Nicolas
 */
public class Usuario {

    private String nome;
    private int codigoUnitario;
    private int codigoFilial;
    private int codigoPerfil;
    private String login;
    private char[] hashSenha;
    private boolean status;
    private List<Integer> funcionalidades;
    
    public Usuario()
    {
        
    }

    //Inicia o usuario com status ativo
    public Usuario(String nome, int codUnitario, int codFilial, int codPerfil, String login,
            String senha, Boolean status, List<Integer> funcionalidades) {
        this.nome = nome;
        this.codigoUnitario = codUnitario;
        this.codigoFilial = codFilial;
        this.login = login;
        this.codigoPerfil = codPerfil;
        this.status = status;
        this.funcionalidades = funcionalidades;
        
        try {
            this.hashSenha = Criptografia.gerarHashSenhaPBKDF2(senha);
        } catch(NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigoUnitario() {
        return codigoUnitario;
    }

    public void setCodigoUnitario(int codigoUnitario) {
        this.codigoUnitario = codigoUnitario;
    }

    public int getCodigoFilial() {
        return codigoFilial;
    }

    public void setCodigoFilial(int codigoFilial) {
        this.codigoFilial = codigoFilial;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getHashSenha() {
        return hashSenha;
    }

    public int getCodigoPerfil() {
        return this.codigoPerfil;
    }

    public void setCodigoPerfil(char tipo) {
        this.codigoPerfil = tipo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public boolean autenticar(String login, String senha) {
        if (this.nome != null) {
          try {
            return this.login.equals(login) && Arrays.equals(this.hashSenha, Criptografia.gerarHashSenhaPBKDF2(senha));
          } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        return false;
    }
    
    public boolean autorizado(int perfilIdNecessario) {        
        return this.funcionalidades.contains(perfilIdNecessario);
    }
}