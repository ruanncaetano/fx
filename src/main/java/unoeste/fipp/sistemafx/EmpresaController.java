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
import org.json.JSONObject;
import unoeste.fipp.sistemafx.db.dal.EmpresaDAL;
import unoeste.fipp.sistemafx.db.entidade.Empresa;
import unoeste.fipp.sistemafx.db.viacep.Endereco;
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
    private TextField tfRazao;

    @FXML
    private TextField tfTelefone;

    private Endereco endereco;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaskFieldUtil.cnpjField(tfCnpj);
        MaskFieldUtil.cepField(tfCep);
        MaskFieldUtil.foneField(tfTelefone);
    }
    private void buscaCep()
    {
        String cep=tfCep.getText();
        StringBuffer dados = new StringBuffer();
        try {
            URL url = new URL("https://viacep.com.br/ws/"+ cep + "/json");
            URLConnection con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while (null != (s = br.readLine()))
                dados.append(s);
            br.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        JSONObject my_obj = new JSONObject(dados.toString());
        tfCidade.setText(my_obj.getString("localidade"));
        tfBairro.setText(my_obj.getString("bairro"));
        tfEndereco.setText(my_obj.getString("logradouro"));
        tfEstado.setText(my_obj.getString("estado"));

        endereco.setCep(my_obj.getString("localidade"));
        endereco.setBairro(my_obj.getString("bairro"));
        endereco.setRua(my_obj.getString("logradouro"));
        endereco.setUf(my_obj.getString("estado"));
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
                    endereco,tfTelefone.getText(),tfEmail.getText());
            EmpresaDAL empD=new EmpresaDAL();
            empD.gravar(emp);
        }

    }


    public void onCepDigitado(KeyEvent keyEvent) {
        if(tfCep.getText().length()==9)
        {
            buscaCep();
        }
    }
}
