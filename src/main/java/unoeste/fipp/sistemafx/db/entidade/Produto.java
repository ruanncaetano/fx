package unoeste.fipp.sistemafx.db.entidade;

import java.time.LocalDate;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double valor;
    private Categoria categoria;

    public Produto(int id, String nome, String descricao, double valor, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
    }

    public Produto(String nome, String descricao, double valor, Categoria categoria) {
        this(0,nome,descricao,valor,categoria);
    }

    public Produto() {
        this(0,"","",0,null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nome;
    }
}
