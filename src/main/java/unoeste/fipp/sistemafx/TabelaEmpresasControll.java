package unoeste.fipp.sistemafx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.sistemafx.db.dal.EmpresaDAL;
import unoeste.fipp.sistemafx.db.entidade.Empresa;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaEmpresasControll implements Initializable {

    @FXML
    private Button btBuscar;

    @FXML
    private Button btCadEmpre;

    @FXML
    private TableColumn<Empresa, String> tbCNPJ;

    @FXML
    private TableColumn<Empresa, String> tbEmail;

    @FXML
    private TableColumn<Empresa, String> tbNomeFantasia;

    @FXML
    private TableColumn<Empresa, String> tbTelefone;

    @FXML
    private TableView<Empresa> tbTotal;

    @FXML
    private TextField tfBuscarEmpre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar as colunas com os atributos da classe Empresa
        tbNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
        tbCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        tbTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tbEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Carregar os dados na tabela
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        EmpresaDAL empD = new EmpresaDAL();
        List<Empresa> ListaEmp = empD.get(filtro);

        tbTotal.setItems(FXCollections.observableArrayList(ListaEmp));
    }

    @FXML
    public void onBuscar(ActionEvent actionEvent) {
        carregarTabela(tfBuscarEmpre.getText());
    }
    @FXML
    void onCad(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("empresa-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("Informações das empresas");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setY(stage.getY()+25);
//        stage.setX(stage.getX());

        stage.showAndWait();
    }
}
