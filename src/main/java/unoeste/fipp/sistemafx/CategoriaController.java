package unoeste.fipp.sistemafx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import unoeste.fipp.sistemafx.db.dal.CategoriaDAL;
import unoeste.fipp.sistemafx.db.dal.ProdutoDAL;
import unoeste.fipp.sistemafx.db.entidade.Categoria;
import unoeste.fipp.sistemafx.db.entidade.Produto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriaController implements Initializable {

    @FXML
    private TextField NovaCategoria;

    @FXML
    private Button btConfirmar;

    @FXML
    private TableColumn<Categoria, String> catgID;

    @FXML
    private TableColumn<Categoria, String> catgNome;

    @FXML
    private TableView<Categoria> tabela;

    private Categoria nCategoria;

    @FXML
    void onConfirmar(ActionEvent event) {
        Categoria catg=new Categoria();
        catg.setNome(NovaCategoria.getText());
        // conecta no banco
        CategoriaDAL catgD=new CategoriaDAL();
        catgD.gravar(catg);
        carregarTabela();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar as colunas com os atributos da classe Categoria
        catgID.setCellValueFactory(new PropertyValueFactory<>("id"));
        catgNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Carregar os dados na tabela
        carregarTabela();
    }
    private void carregarTabela() {
        CategoriaDAL categDAL=new CategoriaDAL();
        List<Categoria> catgList=categDAL.get("");

        tabela.setItems(FXCollections.observableArrayList(catgList));
    }
}