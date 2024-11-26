package unoeste.fipp.sistemafx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.sistemafx.db.dal.ProdutoDAL;
import unoeste.fipp.sistemafx.db.entidade.Categoria;
import unoeste.fipp.sistemafx.db.entidade.Produto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoTableController implements Initializable {
    public static Produto produto=null;

    @FXML
    private TableColumn<Produto, Categoria> colCategoria;

    @FXML
    private TableColumn<Produto, String> colID;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, String> colPreco;

    @FXML
    private TableView<Produto> tabela;

    @FXML
    private TextField tfFiltro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //vinculando cada coluna do tableview com o atributo equivalente da classe Produto
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("valor"));
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        ProdutoDAL produtoDAL=new ProdutoDAL();
        List<Produto> produtoList=produtoDAL.get(filtro);

        tabela.setItems(FXCollections.observableArrayList(produtoList));
    }

    @FXML
    void onAlterar(ActionEvent event) throws Exception{
        //verifica se há produto selecionado
        if(tabela.getSelectionModel().getSelectedIndex()>=0) {
            produto = tabela.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("produto-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Alteração de produtos");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            produto = null;
            carregarTabela("");
        }
    }

    @FXML
    void onApagar(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedIndex()>-1)
        {
            Produto produtoSelecionado=tabela.getSelectionModel().getSelectedItem();
            ProdutoDAL produtoDAL=new ProdutoDAL();
            produtoDAL.apagar(produtoSelecionado);
            carregarTabela("");
        }
    }

    @FXML
    void onFiltrar(KeyEvent event) {
        String filtro="upper(pro_nome) LIKE '%"+tfFiltro.getText().toUpperCase()+"%'";
        carregarTabela(filtro);
    }

    @FXML
    void onNovo(ActionEvent event) throws Exception{
        tfFiltro.getScene().getWindow().setOpacity(0.2); //tela atual fica semi transparente
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("produto-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("Cadastro de produtos");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tfFiltro.getScene().getWindow().setOpacity(1); //tela atual fica com cores sólidas
        carregarTabela("");
    }


}
