package unoeste.fipp.sistemafx.db.dal;

import unoeste.fipp.sistemafx.db.entidade.Empresa;
import unoeste.fipp.sistemafx.db.util.SingletonDB;
import unoeste.fipp.sistemafx.db.viacep.Endereco;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EmpresaDAL implements IDAL <Empresa> {
    @Override
    public boolean gravar(Empresa entidade) {
        EnderecoDAL enderecoDAL = new EnderecoDAL();
        enderecoDAL.gravar(entidade.getEndereco());

        String sql = """
                INSERT INTO public.empresa(
                emp_id, emp_razao, emp_fantasia, emp_cnpj, emp_fone, emp_email, emp_vlremb, end_id)
        VALUES (#1, '#2', '#3', '#4', '#5', '#6', '#7', '#8');
        """;

        sql = sql.replace("#1", "" + (SingletonDB.getConexao().getMaxPK("empresa","emp_id")+1));
        sql = sql.replace("#2", entidade.getEmp_razao());
        sql = sql.replace("#3", entidade.getEmp_fantasia());
        sql = sql.replace("#4", entidade.getEmp_cnpj());
        sql = sql.replace("#5", entidade.getEmp_fone());
        sql = sql.replace("#6", entidade.getEmp_email());
        sql = sql.replace("#7", "" + entidade.getEmp_vlremb());
        sql = sql.replace("#8", "" + SingletonDB.getConexao().getMaxPK("endereco","end_id"));
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Empresa entidade) {
        String sql = """
               UPDATE public.empresa
               SET  emp_razao='#1', emp_fantasia='#2', emp_cnpj='#3', emp_fone='#4', emp_email='#5', emp_vlremb=#6
               WHERE emp_id='#7';
                """;

        sql = sql.replace("#1", entidade.getEmp_razao());
        sql = sql.replace("#2", entidade.getEmp_fantasia());
        sql = sql.replace("#3", entidade.getEmp_cnpj());
        sql = sql.replace("#4", entidade.getEmp_fone());
        sql = sql.replace("#5", entidade.getEmp_email());
        sql = sql.replace("#6", ""+entidade.getEmp_vlremb());
        sql = sql.replace("#7", ""+entidade.getEmp_id());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Empresa entidade) {
        int idEnd = entidade.getEndereco().getId();
        return SingletonDB.getConexao().manipular("DELETE FROM empresa WHERE emp_id="+entidade.getEmp_id()) && SingletonDB.getConexao().manipular("DELETE FROM endereco WHERE end_id="+idEnd);
    }

    @Override
    public Empresa get(int id) throws IOException {
        Empresa empresa = null;
        String sql="SELECT * FROM empresa WHERE emp_id="+id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            if(resultSet.next())
                empresa = new Empresa(resultSet.getInt("emp_id"),
                        resultSet.getString("emp_razao"),
                        resultSet.getString("emp_fantasia"),
                        resultSet.getString("emp_cnpj"),
                        resultSet.getString("emp_fone"),
                        resultSet.getString("emp_email"),
                        resultSet.getDouble("emp_vlremb"));
            Endereco endereco = new EnderecoDAL().get(resultSet.getInt("end_id"));
            empresa.setEndereco(endereco);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empresa;
    }

    @Override
    public List<Empresa> get(String filtro) {
        List<Empresa> empresaList = new ArrayList<>();
        String sql = "SELECT * FROM empresa";
        if(!filtro.isEmpty())
            sql+= " WHERE "+ filtro;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            while(resultSet.next()) {
                Empresa empresa = new Empresa(resultSet.getInt("emp_id"),
                        resultSet.getString("emp_razao"),
                        resultSet.getString("emp_fantasia"),
                        resultSet.getString("emp_cnpj"),
                        resultSet.getString("emp_fone"),
                        resultSet.getString("emp_email"),
                        resultSet.getDouble("emp_remember"));
                Endereco endereco = new EnderecoDAL().get(resultSet.getInt("end_id"));
                empresa.setEndereco(endereco);

                empresaList.add(empresa);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return empresaList;
    }
}