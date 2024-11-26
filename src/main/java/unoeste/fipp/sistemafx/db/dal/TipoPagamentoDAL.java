package unoeste.fipp.sistemafx.db.dal;


import unoeste.fipp.sistemafx.db.entidade.TipoPagamento;
import unoeste.fipp.sistemafx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipoPagamentoDAL implements IDAL<TipoPagamento>{
    @Override
    public boolean gravar(TipoPagamento entidade) {
        String sql= """
                INSERT INTO tipo_pagamento(tpg_nome) VALUES ('#1');
                """;
        sql=sql.replace("#1",entidade.getNome());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(TipoPagamento entidade) {
        String sql= """
                UPDATE tipo_pagamento SET tpg_nome='#1' WHERE tpg_id=#2;
                """;
        sql=sql.replace("#1",entidade.getNome());
        sql=sql.replace("#2",""+entidade.getId());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(TipoPagamento entidade) {
        return SingletonDB.getConexao().manipular(
                "DELETE FROM tipo_pagamento WHERE tpg_id="+entidade.getId());
    }

    @Override
    public TipoPagamento get(int id) {
        TipoPagamento tipoPagamento=null;
        String sql="SELECT * FROM tipo_pagamento WHERE tpg_id="+id;
        ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {
                tipoPagamento = new TipoPagamento(resultSet.getInt("tpg_id"),
                        resultSet.getString("tpg_nome"));
            }
        }catch (Exception e){e.printStackTrace();}
        return tipoPagamento;
    }

    @Override
    public List<TipoPagamento> get(String filtro) {
        List <TipoPagamento> tipoPagamentos=new ArrayList<>();
        String sql="SELECT * FROM tipo_pagamento";
        if(!filtro.isEmpty())
            sql+=" WHERE "+filtro;
        ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
        try {
            while (resultSet.next()) {
                TipoPagamento tipoPagamento = new TipoPagamento(resultSet.getInt("tpg_id"),
                        resultSet.getString("tpg_nome"));
                tipoPagamentos.add(tipoPagamento);
            }
        }catch (Exception e){e.printStackTrace();}
        return tipoPagamentos;
    }
}
