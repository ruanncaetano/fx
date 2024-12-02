package unoeste.fipp.sistemafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import unoeste.fipp.sistemafx.db.dal.EmpresaDAL;
import unoeste.fipp.sistemafx.db.entidade.Empresa;
import unoeste.fipp.sistemafx.util.MaskFieldUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class EmpresaController implements Initializable {

    @FXML
    private Button bfCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfCep;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfCnpj;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfEndereco;

    @FXML
    private TextField tfEstado;

    @FXML
    private TextField tfFantasia;

    @FXML
    private TextField tfIe;

    @FXML
    private TextField tfNumeroDaRua;

    @FXML
    private TextField tfRazao;

    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfVlrEmbala;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaskFieldUtil.cnpjField(tfCnpj);
        MaskFieldUtil.cepField(tfCep);
        MaskFieldUtil.foneField(tfTelefone);
    }

    @FXML
    void onAlterar(ActionEvent event)
    {
        EmpresaDAL empresaDAL = new EmpresaDAL();
        Empresa empresa = null;

        if (!empresaDAL.get("").isEmpty())
            empresa = empresaDAL.get("").get(0);
        boolean empresaJaExiste = (empresa == null) ? false : true;
        if (empresa == null)
            empresa = new Empresa();

        empresa.setRazaoSocial(tfRazao.getText());
        empresa.setBairro(tfBairro.getText());
        empresa.setCep(tfCep.getText());
        empresa.setCnpj(tfCnpj.getText());
        empresa.setCidade(tfCidade.getText());
        empresa.setEmail(tfEmail.getText());
        empresa.setNomeFantasia(tfFantasia.getText());
        empresa.setNumeroDaRua(tfNumeroDaRua.getText());
        empresa.setRua(tfTelefone.getText());
        empresa.setTelefone(tfTelefone.getText());
        empresa.setUf(tfEstado.getText());
        empresa.setValorDaEmbalagem(Double.parseDouble(tfVlrEmbala.getText()));

        if (empresaJaExiste)
            empresaDAL.alterar(empresa);
        else
            empresaDAL.gravar(empresa);

        tfEndereco.getScene().getWindow().hide();

    }
    @FXML
    void onCancelar(ActionEvent event) {
        bfCancelar.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        String mensagem="Confirma o cadastro da empresa "+tfRazao.getText();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(mensagem);
        if(alert.showAndWait().get()== ButtonType.OK)
        {

            Empresa emp=new Empresa(tfRazao.getText(),tfFantasia.getText(),tfCnpj.getText(),
                    tfCep.getText(),tfEndereco.getText(),tfNumeroDaRua.getText(),tfBairro.getText(),tfCidade.getText(),
                    tfEstado.getText(),tfTelefone.getText(),tfEmail.getText(),Double.parseDouble(tfVlrEmbala.getText()));
            EmpresaDAL empD=new EmpresaDAL();
            empD.gravar(emp);
        }

    }
}
