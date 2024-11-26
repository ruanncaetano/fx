package unoeste.fipp.sistemafx.db.dal;

import com.faiskaburguer.db.entidade.Empresa;
import com.faiskaburguer.db.entidade.Endereco;
import com.faiskaburguer.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAL implements IDAL <Endereco>{
    @Override
    public boolean gravar(Endereco entidade) {
        String sql = """
                    INSERT INTO endereco(end_cep, end_rua, end_numero, end_bairro, end_cidade, end_uf, end_id) VALUES ('#1', '#2', '#3', '#4', '#5', '#6',#7);
                """;

        sql = sql.replace("#1", entidade.getCep());
        sql = sql.replace("#2", entidade.getRua());
        sql = sql.replace("#3", entidade.getNumero());
        sql = sql.replace("#4", entidade.getBairro());
        sql = sql.replace("#5", entidade.getCidade());
        sql = sql.replace("#6", entidade.getUf());
        sql = sql.replace("#7", ""+(SingletonDB.getConexao().getMaxPK("endereco", "end_id")+1));
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Endereco entidade) {
        String sql = """
               UPDATE public.empresa
               SET  end_cep='#1', end_rua='#2', end_numero='#3', end_bairro='#4', end_cidade='#5', end_uf='#6')
               WHERE end_id=#7;
                """;

        sql = sql.replace("#1", entidade.getCep());
        sql = sql.replace("#2", entidade.getRua());
        sql = sql.replace("#3", entidade.getNumero());
        sql = sql.replace("#4", entidade.getBairro());
        sql = sql.replace("#5", entidade.getCidade());
        sql = sql.replace("#6", entidade.getUf());
        sql = sql.replace("#7", ""+ entidade.getId());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Endereco entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM endereco WHERE end_id="+entidade.getId());
    }

    @Override
    public Endereco get(int id) throws SQLException {
        Endereco endereco = null;
        String sql="SELECT * FROM endereco WHERE end_id="+id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try {
            if(resultSet.next()) {
                endereco = new Endereco(resultSet.getInt("end_id"),
                        resultSet.getString("end_cep"),
                        resultSet.getString("end_rua"),
                        resultSet.getString("end_numero"),
                        resultSet.getString("end_bairro"),
                        resultSet.getString("end_cidade"),
                        resultSet.getString("end_uf"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return endereco;
    }

    @Override
    public List get(String filtro) {
        List<Endereco> enderecoList = new ArrayList<Endereco>();
        String sql="SELECT * FROM endereco";
        if(!filtro.isEmpty())
            sql+= " WHERE "+ filtro;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try {
            while(resultSet.next()) {
                Endereco endereco = new Endereco(resultSet.getInt("end_id"),
                        resultSet.getString("end_cep"),
                        resultSet.getString("end_rua"),
                        resultSet.getString("end_numero"),
                        resultSet.getString("end_bairro"),
                        resultSet.getString("end_cidade"),
                        resultSet.getString("end_uf"));
                enderecoList.add(endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enderecoList;
    }
}
