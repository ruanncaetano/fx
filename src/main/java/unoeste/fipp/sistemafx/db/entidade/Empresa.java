package unoeste.fipp.sistemafx.db.entidade;

import unoeste.fipp.sistemafx.db.viacep.Endereco;

public class Empresa {
    private int emp_id;
    private String emp_razao;
    private String emp_fantasia;
    private String emp_cnpj;
    public Endereco endereco;
    private String emp_fone;
    private String emp_email;
    private Double emp_vlremb;

    public Empresa()
    {

    }
    public Empresa(String emp_razao, String emp_fantasia, String emp_cnpj, Endereco endereco, String emp_fone, String emp_email) {
        this.emp_razao = emp_razao;
        this.emp_fantasia = emp_fantasia;
        this.emp_cnpj = emp_cnpj;
        this.endereco = endereco;
        this.emp_fone = emp_fone;
        this.emp_email = emp_email;
    }

    public Empresa(int emp_id, String emp_razao, String emp_fantasia, String emp_cnpj, String emp_fone, String emp_email, Double emp_vlremb) {
        this.emp_id = emp_id;
        this.emp_razao = emp_razao;
        this.emp_fantasia = emp_fantasia;
        this.emp_cnpj = emp_cnpj;
        this.emp_fone = emp_fone;
        this.emp_email = emp_email;
        this.emp_vlremb = emp_vlremb;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_razao() {
        return emp_razao;
    }

    public void setEmp_razao(String emp_razao) {
        this.emp_razao = emp_razao;
    }

    public String getEmp_fantasia() {
        return emp_fantasia;
    }

    public void setEmp_fantasia(String emp_fantasia) {
        this.emp_fantasia = emp_fantasia;
    }

    public String getEmp_cnpj() {
        return emp_cnpj;
    }

    public void setEmp_cnpj(String emp_cnpj) {
        this.emp_cnpj = emp_cnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getEmp_fone() {
        return emp_fone;
    }

    public void setEmp_fone(String emp_fone) {
        this.emp_fone = emp_fone;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public Double getEmp_vlremb() {
        return emp_vlremb;
    }

    public void setEmp_vlremb(Double emp_vlremb) {
        this.emp_vlremb = emp_vlremb;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco= endereco;
    }
}