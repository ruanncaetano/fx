package unoeste.fipp.sistemafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import unoeste.fipp.sistemafx.db.dal.CategoriaDAL;
import unoeste.fipp.sistemafx.db.dal.ProdutoDAL;
import unoeste.fipp.sistemafx.db.entidade.Categoria;

import unoeste.fipp.sistemafx.db.entidade.Produto;
import unoeste.fipp.sistemafx.db.util.SingletonDB;
import unoeste.fipp.sistemafx.util.MaskFieldUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoFormController implements Initializable {

    @FXML
    private Button bfCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea taDesc;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPreco;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaskFieldUtil.monetaryField(tfPreco);
        Platform.runLater(()->{tfNome.requestFocus();});
        carregarCategorias();
        Produto produto=ProdutoTableController.produto;
        if(produto!=null) {
            tfNome.setText(produto.getNome());
            tfID.setText("" + produto.getId());
            tfPreco.setText("" + produto.getValor());
            taDesc.setText(produto.getDescricao());
            cbCategoria.getSelectionModel().select(produto.getCategoria());
        }
    }

    private void carregarCategorias() {
        CategoriaDAL categoriaDAL=new CategoriaDAL();
        List<Categoria> categoriaList=categoriaDAL.get("");
        cbCategoria.setItems(FXCollections.observableArrayList(categoriaList));
    }

    @FXML
    void onCancelar(ActionEvent event) {
        bfCancelar.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        Produto produto=new Produto(tfNome.getText(),taDesc.getText(),
                Double.parseDouble(tfPreco.getText().replace(",", ".")),
                cbCategoria.getSelectionModel().getSelectedItem());
        ProdutoDAL produtoDAL=new ProdutoDAL();
        if(ProdutoTableController.produto==null) {
            if (!produtoDAL.gravar(produto)) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Deu erro: " + SingletonDB.getConexao().getMensagemErro());
                alert1.showAndWait();
            }
        }
        else{
               produto.setId(Integer.parseInt(tfID.getText()));
               if(!produtoDAL.alterar(produto))
               {
                   Alert alert1 = new Alert(Alert.AlertType.ERROR);
                   alert1.setContentText("Deu erro: " + SingletonDB.getConexao().getMensagemErro());
                   alert1.showAndWait();
               }
           }

        bfCancelar.getScene().getWindow().hide();

    }


}
