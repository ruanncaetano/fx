package unoeste.fipp.sistemafx;

import com.lowagie.text.*;
import com.lowagie.text.Cell;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unoeste.fipp.sistemafx.db.dal.PedidoDAL;
import unoeste.fipp.sistemafx.db.dal.ProdutoDAL;
import unoeste.fipp.sistemafx.db.dal.TipoPagamentoDAL;
import unoeste.fipp.sistemafx.db.entidade.Pedido;
import unoeste.fipp.sistemafx.db.entidade.Produto;
import unoeste.fipp.sistemafx.db.entidade.TipoPagamento;
import unoeste.fipp.sistemafx.util.MaskFieldUtil;
import unoeste.fipp.sistemafx.util.ModalTable;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class formAlterarQuant implements Initializable
{
    @FXML
    private TextField btProduto;

    @FXML
    private TextField spQuantidade;

    @FXML
    void onCancelar(ActionEvent event) {
        btProduto.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event)
    {
        FormPedidosController.tableViewItens.getItems().remove(FormPedidosController.itemQuantidade);
        Pedido.Item item = new Pedido.Item(FormPedidosController.itemQuantidade.produto(), Integer.parseInt(spQuantidade.getText()), FormPedidosController.itemQuantidade.valor());
        FormPedidosController.tableViewItens.getItems().add(item);
        btProduto.getScene().getWindow().hide();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btProduto.setText(FormPedidosController.itemQuantidade.produto().getNome());

    }
}

