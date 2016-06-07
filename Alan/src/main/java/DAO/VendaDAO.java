
package DAO;

import conexao.ConexaoBanco;
import classes.entidades.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author caiqu
 */
public class VendaDAO {
    
    public static void adicionar(Venda saida) throws SQLException, ClassNotFoundException {
        Connection conexao = ConexaoBanco.obterConexao();
        //linguagem sql -> inserir no banco
        String sql = "INSERT INTO VENDA  "
                //Nomes dos campos no banco
                + "(codigoProduto, produto, valor, idUsuario, quantidade)"
                + "VALUES(?,?,?,?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, saida.getCodProduto());
            stmt.setString(2, saida.getProduto());
            stmt.setFloat(2, saida.getValor());
            stmt.setInt(3, saida.getCodigoUsuario());
            stmt.setInt(4, saida.getQuantidade());
            
            stmt.execute();
        }
    }
    
}