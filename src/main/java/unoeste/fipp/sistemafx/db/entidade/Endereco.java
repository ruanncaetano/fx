package unoeste.fipp.sistemafx.db.dal;

import com.faiskaburguer.db.viacep.ConsultaCEP;
import com.faiskaburguer.db.viacep.EnderecoRecordAPI;

public class Endereco {
    private int id;
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

    // to get on the db
    public Endereco(int id, String cep, String rua, String numero, String bairro, String cidade, String uf) {
        this.id = id;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    // to push on db
    public Endereco(String cep, String rua, String numero) {
        this.cep = cep;

        EnderecoRecordAPI endereco = ConsultaCEP.consulta(cep);

        if(endereco.logradouro().isEmpty())
            this.rua = rua;
        else
            this.rua = endereco.logradouro();

        this.numero = numero;
        this.bairro = endereco.bairro();
        this.cidade = endereco.localidade();
        this.uf = endereco.uf();
    }

    @Override
    public String toString()
    {
        return rua + ", " + numero + ", " + bairro + ", " + cidade + ", " + uf + ", " + cep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}