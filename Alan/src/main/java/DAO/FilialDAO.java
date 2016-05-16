package DAO;

import classes.Filial;
import conexao.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilialDAO {

    public static void adicionar(Filial filial) throws SQLException, ClassNotFoundException {
        Connection conexao = ConexaoBanco.obterConexao();
        //linguagem sql -> inserir no banco
        String sql = "INSERT INTO FILIAL  "
                //Nomes dos campos no banco
                + "(nome , fantasia, rua, numero, bairro, estado, cidade, cnpj)"
                + "VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, filial.getNome());
        stmt.setString(2, filial.getNomeFantasia());
        stmt.setString(3, filial.getRua());
        stmt.setInt(4, filial.getNum());
        stmt.setString(5, filial.getBairro());
        stmt.setString(6, filial.getEstado());
        stmt.setString(7, filial.getCidade());
        stmt.setString(8, filial.getCnpj());
        stmt.execute();
        stmt.close();
    }

    public static ArrayList<Filial> listar() throws SQLException, ClassNotFoundException {

        Connection conexao = ConexaoBanco.obterConexao();

        String sql = "SELECT * FROM FILIAL";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        ResultSet result = stmt.executeQuery();
        ArrayList<Filial> retorno = new ArrayList<>();

        while (result.next()) {
            //pego o retorno do banco e atribuo à variaveis
            int codigoFilial = result.getInt("codigofilial");
            String nome = result.getString("nome");
            String nomeFantasia = result.getString("fantasia");
            String rua = result.getString("rua");
            int num = result.getInt("numero");
            String bairro = result.getString("bairro");
            String estado = result.getString("estado");
            String cidade = result.getString("cidade");
            String cnpj = result.getString("cnpj");
            
            //crio um objeto filial
            Filial f = new Filial(nome, nomeFantasia, rua, num, bairro, estado, cidade, cnpj);
            
            //e adiciono no arraylist para retorno
            retorno.add(f);
            
        }

        return retorno;
    }

}