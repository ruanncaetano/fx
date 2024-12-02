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
            Pedido.Item item=new Pedido.Item(produto,spQuantidade.getValue(), produto.getValor());
            tableView.getItems().add(item);
            btProduto.setText("Selecione um produto");
            spQuantidade.getValueFactory().setValue(1);
            produto=null;
        }
        atualizarTotal();
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
        double valorTotalPedido = 0.0;

        for (Pedido.Item item : tableView.getItems()) {
            Produto produto = item.produto();
            int quantidade = item.quant();
            double valorItem = item.valor();

            valorTotalPedido += valorItem * quantidade;
        }
        pedido.setTotal(valorTotalPedido);
        pedido.setTipoPagamento(cbTipoPagamento.getValue());
        pedido.setViagem(rbViagem.isSelected()?'S':'N');
        for (int i=0;i<tableView.getItems().size();i++)
        {
            pedido.addItem(tableView.getItems().get(i));
        }
        PedidoDAL pedidoDAL=new PedidoDAL();
        pedidoDAL.gravar(pedido);
        System.out.println(pedido.getItens());

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
    private void atualizarTotal() {

        double valorTotalPedido = 0.0;

        for (Pedido.Item item : tableView.getItems()) {
            Produto produto = item.produto();
            int quantidade = item.quant();
            double valorItem = item.valor();

            valorTotalPedido += valorItem * quantidade;
        }

        lbTotal.setText(String.format("%.2f", valorTotalPedido));
    }
    private void carregarTiposPagamento() {
        //carrega os tipos de pagamento
        List<TipoPagamento> tipoPagamentoList=new TipoPagamentoDAL().get("");
        cbTipoPagamento.setItems(FXCollections.observableArrayList(tipoPagamentoList));
    }
    @FXML
    public void onPrint(ActionEvent actionEvent) {
        try {
            // criando o doc
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream("pedido.pdf"));
            document.open();

            // config de fontas
            Font grande = new Font(Font.HELVETICA, 48, Font.BOLD, Color.RED);
            Font normal = new Font(Font.HELVETICA, 12);

            // titulo
            Paragraph titulo = new Paragraph("Faiska Burguer\n\n", grande);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // infos do pedido
            Paragraph infoPedido = new Paragraph(
                    "Cliente: " + tfCliente.getText() + "\n" +
                            "Telefone: " + tfTelefone.getText() + "\n" +
                            "Data: " + dpData.getValue().toString() + "\n" +
                            "Tipo de Pagamento: " + (cbTipoPagamento.getValue() != null ? cbTipoPagamento.getValue().getNome() : "N/A") + "\n" +
                            "Viagem: " + (rbViagem.isSelected() ? "Sim" : "Não") + "\n",
                    normal
            );
            document.add(infoPedido);

            // tabela de itens
            Table table = new Table(3);
            table.setWidths(new float[]{50f, 20f, 30f});
            table.setWidth(100);

            // cabecalho
            Cell cell;
            cell = new Cell(new Phrase("Produto", normal));
            cell.setHeader(true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new Cell(new Phrase("Quantidade", normal));
            cell.setHeader(true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new Cell(new Phrase("Valor", normal));
            cell.setHeader(true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            table.endHeaders();
            double total=0;
            // adicionando os itens a tabela
            for (Pedido.Item item : tableView.getItems()) {
                table.addCell(new Cell(new Phrase(item.produto().getNome(), normal)));
                table.addCell(new Cell(new Phrase(String.valueOf(item.quant()), normal)));
                double valor = item.valor();
                total += valor;
                table.addCell(new Cell(new Phrase(String.format("R$ %.2f", item.valor()), normal)));

            }
            Cell totalCell = new Cell(new Phrase("TOTAL", normal));
            totalCell.setColspan(2);  // Ocupa duas colunas (Produto e Quantidade)
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(totalCell);

            table.addCell(new Cell(new Phrase(String.format("R$ %.2f", total), normal)));

            document.add(table);

            // fecha o documento
            document.close();

            // abre o pdf
            Desktop.getDesktop().open(new File("pedido.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


