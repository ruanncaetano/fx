package unoeste.fipp.sistemafx.db.entidade;

public class TipoPagamento {
    private int id;
    private String nome;

    public TipoPagamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public TipoPagamento(String nome) {
        this(0,nome);
    }

    public TipoPagamento() {
        this(0,"");
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

    @Override
    public String toString() {
        return nome;
    }
}
