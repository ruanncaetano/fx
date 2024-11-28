package unoeste.fipp.sistemafx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import unoeste.fipp.sistemafx.db.dal.EmpresaDAL;
import unoeste.fipp.sistemafx.db.entidade.Empresa;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaEmpresasControll implements Initializable {

    @FXML
    private Button btBuscar;

    @FXML
    private Button btCadEmpre;

    @FXML
    private TableColumn<Empresa,String> tbCNPJ;

    @FXML
    private TableColumn<Empresa, String> tbEmail;

    @FXML
    private TableColumn<Empresa,String> tbNomeFantasia;

    @FXML
    private TableColumn<Empresa, String> tbTelefone;

    @FXML
    private TableView<Empresa> tbTotal;
    @FXML
    private TextField tfBuscarEmpre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar as colunas com os atributos da classe Categoria
        tbNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("Nome Fantasia"));
        tbCNPJ.setCellValueFactory(new PropertyValueFactory<>("CNPJ"));
        tbTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        tbEmail.setCellValueFactory(new PropertyValueFactory<>("E-mail"));

        // Carregar os dados na tabela
        carregarTabela("");
    }
    private void carregarTabela(String filtro)
    {
        EmpresaDAL empD=new EmpresaDAL();
        List<Empresa> ListaEmp=empD.get(filtro);

        tbTotal.setItems(FXCollections.observableArrayList(ListaEmp));
    }

    public void onBuscar(javafx.event.ActionEvent actionEvent) {
        carregarTabela(tfBuscarEmpre.getText());
    }
}

