package unoeste.fipp.sistemafx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import unoeste.fipp.sistemafx.db.dal.EmpresaDAL;
import unoeste.fipp.sistemafx.db.dal.PedidoDAL;
import unoeste.fipp.sistemafx.db.entidade.Empresa;
import unoeste.fipp.sistemafx.db.entidade.Pedido;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaPedidos implements Initializable {
    @FXML
    private TableColumn<Pedido, String> tbCli;

    @FXML
    private TableColumn<Pedido, Date> tbData;

    @FXML
    private TableColumn<Pedido, String> tbFone;

    @FXML
    private TableColumn<Pedido, Integer> tbId;

    @FXML
    private TableColumn<Pedido, String> tbPagamento;

    @FXML
    private TableView<Pedido> tbPedidos;

    @FXML
    private TableColumn<Pedido, Character> tbViage;

    @FXML
    private TableColumn<Pedido, Double> tbTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar as colunas com os atributos da classe Empresa
        tbId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbData.setCellValueFactory(new PropertyValueFactory<>("Data"));
        tbCli.setCellValueFactory(new PropertyValueFactory<>("Cliente"));
        tbFone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tbTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        tbPagamento.setCellValueFactory(new PropertyValueFactory<>("Pagamento"));
        tbViage.setCellValueFactory(new PropertyValueFactory<>("Viagem"));

        // Carregar os dados na tabela
        carregarTabela();
    }


    private void carregarTabela() {
        PedidoDAL pDal= new PedidoDAL();
        List<Pedido> ListaPedi = pDal.get("");
        tbPedidos.setItems(FXCollections.observableArrayList(ListaPedi));
    }

}

