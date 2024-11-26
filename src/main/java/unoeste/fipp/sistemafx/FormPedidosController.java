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

public class FormPedidosController implements Initializable {

    @FXML
    private Button btProduto;

    @FXML
    private ComboBox<TipoPagamento> cbTipoPagamento;

    @FXML
    private TableColumn<Pedido.Item, String> coProduto;

    @FXML
    private TableColumn<Pedido.Item, String> coQuantidade;

    @FXML
    private TableColumn<Pedido.Item, String> coValor;

    @FXML
    private DatePicker dpData;

    @FXML
    private Label lbTotal;

    @FXML
    private CheckBox rbViagem;

    @FXML
    private Spinner<Integer> spQuantidade;

    @FXML
    private TableView<Pedido.Item> tableView;

    @FXML
    private TextField tfCliente;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfTelefone;

    private Produto produto=null;

    @FXML
    void onAdicionar(ActionEvent event) {
        if(produto!=null){
            Pedido.Item item=new Pedido.Item(produto,spQuantidade.getValue(), produto.getValor()*spQuantidade.getValue());
            tableView.getItems().add(item);
            btProduto.setText("Selecione um produto");
            spQuantidade.getValueFactory().setValue(1);
            produto=null;
        }

    }

    @FXML
    void onCancelar(ActionEvent event) {
        btProduto.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        //verificar se todas as informações foram preenchidas
        Pedido pedido=new Pedido();
        pedido.setData(dpData.getValue());
        pedido.setNomeCliente(tfCliente.getText());
        pedido.setFoneCliente(tfTelefone.getText());
        pedido.setTotal(Double.parseDouble(lbTotal.getText()));
        pedido.setTipoPagamento(cbTipoPagamento.getValue());
        pedido.setViagem(rbViagem.isSelected()?'S':'N');
        for (int i=0;i<tableView.getItems().size();i++)
        {
            pedido.addItem(tableView.getItems().get(i));
        }
        PedidoDAL pedidoDAL=new PedidoDAL();
        pedidoDAL.gravar(pedido);
        //fecha a janela de pedidos
        btProduto.getScene().getWindow().hide();
    }

    @FXML
    void onSelProduto(ActionEvent event) {
        ModalTable mt=new ModalTable(new ProdutoDAL().get(""),new String[]{"id","nome","valor","categoria"},"nome");
        Stage stage=new Stage();
        stage.setScene(new Scene(mt));
        stage.setWidth(600); stage.setHeight(480); stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        produto=(Produto)mt.getSelecionado();
        if(produto!=null)
            btProduto.setText(produto.getNome());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coProduto.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().produto().getNome()));
        coQuantidade.setCellValueFactory(cellData->new SimpleStringProperty(""+cellData.getValue().quant()));
        coValor.setCellValueFactory(cellData->new SimpleStringProperty(""+cellData.getValue().valor()));
        spQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1));
        dpData.setValue(LocalDate.now());
        MaskFieldUtil.foneField(tfTelefone);
        carregarTiposPagamento();
        Platform.runLater(()->{tfCliente.requestFocus();});

    }

    private void carregarTiposPagamento() {
        //carrega os tipos de pagamento
        List<TipoPagamento> tipoPagamentoList=new TipoPagamentoDAL().get("");
        cbTipoPagamento.setItems(FXCollections.observableArrayList(tipoPagamentoList));
    }

    public void onPrint(ActionEvent actionEvent) {
        //gerar pdf e abrir para impressão
        try{
            Document document=new Document(PageSize.A4);
            PdfWriter.getInstance(document,new FileOutputStream("d:/pedido.pdf"));
            document.open();
            Font grande=new Font(Font.HELVETICA,48);
            grande.setColor(Color.RED);
            Paragraph paragraph=new Paragraph("Faiska Burguer");
            paragraph.setFont(grande);
            document.add(paragraph);
            float [] pointColumnWidths = {150F, 150F, 150F};
            Table table = new Table(10,10);//pointColumnWidths);

            // Adding cells to the table
            table.addCell(String.valueOf(new Cell().add("Name")));
            table.addCell(String.valueOf(new Cell().add("Raju")));
            table.addCell(String.valueOf(new Cell().add("Id")));
            table.addCell(String.valueOf(new Cell().add("1001")));
            table.addCell(String.valueOf(new Cell().add("Designation")));
            table.addCell(String.valueOf(new Cell().add("Programmer")));

            document.close();
            Desktop.getDesktop().open(new File("d:/pedido.pdf"));
        }catch (Exception e){
            System.out.println(e);
        }

    }
}

