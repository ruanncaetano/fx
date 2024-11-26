package unoeste.fipp.sistemafx.db.dal;


import unoeste.fipp.sistemafx.db.entidade.Produto;
import unoeste.fipp.sistemafx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAL implements IDAL<Produto>{

    @Override
    public boolean gravar(Produto entidade) {
        String sql= """
                INSERT INTO produto(
                pro_nome, pro_descr, pro_valor, cat_id)
                VALUES ('#1', '#2', #3, #4);
                """;
        sql=sql.replace("#1",entidade.getNome());
        sql=sql.replace("#2",entidade.getDescricao());
        sql=sql.replace("#3",""+entidade.getValor());
        sql=sql.replace("#4",""+entidade.getCategoria().getId());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Produto entidade) {
        String sql= """
                UPDATE produto SET pro_nome='#1', pro_descr='#2', 
                pro_valor=#3, cat_id=#4 
                WHERE pro_id=#5
                """;
        sql=sql.replace("#1",entidade.getNome());
        sql=sql.replace("#2",entidade.getDescricao());
        sql=sql.replace("#3",""+entidade.getValor());
        sql=sql.replace("#4",""+entidade.getCategoria().getId());
        sql=sql.replace("#5",""+entidade.getId());
        return SingletonDB.getConexao().manipular(sql);
    }
    @Override
    public boolean apagar(Produto entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM produto WHERE pro_id="+entidade.getId());
    }

    @Override
    public Produto get(int id) {
        Produto produto=null;
        String sql="SELECT * FROM produto WHERE pro_id="+id;
        try{
            ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
            if(resultSet.next())
            {
                produto=new Produto(resultSet.getInt("pro_id"),resultSet.getString("pro_nome"),
                        resultSet.getString("pro_descr"),resultSet.getDouble("pro_valor"),
                        new CategoriaDAL().get(resultSet.getInt("cat_id")));
            }
        }
        catch (Exception e){ e.printStackTrace();}

        return produto;
    }

    @Override
    public List<Produto> get(String filtro) {
        List <Produto> listProduto = new ArrayList<>();
        String sql="SELECT * FROM produto";
        if(!filtro.isEmpty())
           sql+=" WHERE "+filtro;
        try {
            ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
            while (resultSet.next()) {
                Produto produto = new Produto(resultSet.getInt("pro_id"), resultSet.getString("pro_nome"),
                        resultSet.getString("pro_descr"), resultSet.getDouble("pro_valor"),
                        new CategoriaDAL().get(resultSet.getInt("cat_id")));
                listProduto.add(produto);
            }
        }
        catch (Exception e){ e.printStackTrace();}
        return listProduto;
    }
}
