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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unoeste.fipp.sistemafx.db.dal.EmpresaDAL;
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
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FormPedidosController implements Initializable {

    public static Pedido.Item itemQuantidade = null;
    public static TableView<Pedido.Item> tableViewItens = null;

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

    private Produto produto = null;

    @FXML
    void onPrint(ActionEvent event) {
        try {
            // criando o documento
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream("pedido.pdf"));
            document.open();

            // configurando fontes
            Font grande = new Font(Font.HELVETICA, 48, Font.BOLD, Color.RED);
            Font normal = new Font(Font.HELVETICA, 12);

            // título
            Paragraph titulo;
            if (tfID.getText().isEmpty()) {
                titulo = new Paragraph("Faiska Burguer\n\n", grande);
            } else {
                titulo = new Paragraph("Pedido nº " + TabelaPedidosController.pedido.getId() + " | Faiska Burguer\n\n", grande);
            }
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // informações do pedido
            Paragraph infoPedido = new Paragraph(
                    "Cliente: " + tfCliente.getText() + "\n" +
                            "Telefone: " + tfTelefone.getText() + "\n" +
                            "Data: " + (dpData.getValue() != null ? dpData.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A") + "\n" +
                            "Tipo de Pagamento: " + (cbTipoPagamento.getValue() != null ? cbTipoPagamento.getValue().getNome() : "N/A") + "\n" +
                            "Viagem: " + (rbViagem.isSelected() ? "Sim" : "Não") + "\n" +
                            "Total: R$ " + lbTotal.getText() + "\n\n",
                    normal
            );
            document.add(infoPedido);

            // tabela de itens
            if (tableView.getItems().size() != 0) {
                Table table = new Table(3);
                table.setWidths(new float[]{50f, 20f, 30f});
                table.setWidth(100);

                // cabeçalho
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

                // adicionando os itens à tabela
                for (Pedido.Item item : tableView.getItems()) {
                    table.addCell(new Cell(new Phrase(item.produto().getNome(), normal)));
                    table.addCell(new Cell(new Phrase(String.valueOf(item.quant()), normal)));
                    table.addCell(new Cell(new Phrase(String.format("R$ %.2f", item.valor() * item.quant()), normal)));
                }

                document.add(table);
            }

            // valor final
            Paragraph valorFinal;
            EmpresaDAL empresaDAL = new EmpresaDAL();
            double valorViagem = empresaDAL.get("").get(0).getValorDaEmbalagem();
            if (valorViagem != 0 && rbViagem.isSelected()) {
                valorFinal = new Paragraph("Valor final: R$ " + lbTotal.getText() + "  | R$ " + valorViagem + " incluso de embalagem", normal);
            } else {
                valorFinal = new Paragraph("Valor final: R$ " + lbTotal.getText(), normal);
            }
            document.add(valorFinal);

            // fechando o documento
            document.close();

            // abrindo o PDF
            Desktop.getDesktop().open(new File("pedido.pdf"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void alterarLabelValorTotal() {
        double valorTotal = 0;
        for (int i = 0; i < tableView.getItems().size(); i++) {
            valorTotal += tableView.getItems().get(i).quant() * tableView.getItems().get(i).valor();
        }


        EmpresaDAL empresaDAL = new EmpresaDAL();
        double valorViagem = empresaDAL.get("").get(0).getValorDaEmbalagem();
        if (valorViagem != 0 && rbViagem.isSelected()) {
            valorTotal += valorViagem;
        }
        String valorTotalString = "" + valorTotal;
        valorTotalString = valorTotalString.replace(".", ",");
        if (valorTotalString.length() - valorTotalString.indexOf(",") == 2)
            valorTotalString += "0";
        lbTotal.setText("R$" + valorTotalString);
    }

    @FXML
    void onAdicionar(ActionEvent event) {
        // Adiciona o item mesmo que repetido
        Pedido.Item item = new Pedido.Item(produto, spQuantidade.getValue(), produto.getValor());


        // Verifica se há itens repetidos, adicionando os valores em quantidadeTotal
        int quantidadeTotal = 0;
        boolean existe = false;
        for (Pedido.Item itens : tableView.getItems()) {

            if (itens.produto().getId() == item.produto().getId()) {
                existe = true;
                quantidadeTotal = itens.quant();
                System.out.println("entrou");

            }

        }
        if (existe) System.out.println("Há itens repetidos");
        else System.out.println("Não há itens repetidos");
        if (existe) {
            for (int i = 0; i < tableView.getItems().size(); i++) {
                if (tableView.getItems().get(i).produto().getId() == item.produto().getId()) {
                    tableView.getItems().remove(i);
                }
            }
            item = new Pedido.Item(produto, quantidadeTotal + spQuantidade.getValue(), produto.getValor());
            tableView.getItems().add(item);
        } else {
            tableView.getItems().add(item);
        }
        btProduto.setText("Selecione um item");
        spQuantidade.getEditor().setText("1");
        spQuantidade.getValueFactory().setValue(1);
        alterarLabelValorTotal();
        System.out.println("spQuantidade = " + spQuantidade.getValue());
        //voltar o spinner para 1
    }


    @FXML
    void onApagarProduto(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            Pedido.Item item = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(item);
            tableView.setItems(tableView.getItems());
            alterarLabelValorTotal();
        }
    }

    @FXML
    void onAlterarQuantidade(ActionEvent event) throws IOException {

        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            Pedido.Item item = tableView.getSelectionModel().getSelectedItem();
            itemQuantidade = item;
            tableViewItens = tableView;
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("form-pedidos-alterar-quantidade-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Pedidos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        }

        alterarLabelValorTotal();

    }

    @FXML
    void onCancelar(ActionEvent event) {
        lbTotal.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {

        Pedido pedido = new Pedido();
        pedido.setData(dpData.getValue());
        pedido.setFoneCliente(tfTelefone.getText());
        pedido.setNomeCliente(tfCliente.getText());
        pedido.setTipoPagamento(cbTipoPagamento.getSelectionModel().getSelectedItem());
        pedido.setViagem(rbViagem.isSelected() ? 'S' : 'N');
        for (int i = 0; i < tableView.getItems().size(); i++) {
            pedido.addItem(tableView.getItems().get(i));
        }
        pedido.totalizar();

        PedidoDAL pedidoDAL = new PedidoDAL();
        if (TabelaPedidosController.pedido == null)
        {
            pedidoDAL.gravar(pedido);
        }
        else
        {
            pedido.setId(Integer.parseInt(tfID.getText()));
            pedidoDAL.alterar(pedido);
        }


        btProduto.getScene().getWindow().hide();
    }

    @FXML
    void onSelProduto(ActionEvent event) {
        ModalTable mt = new ModalTable(new ProdutoDAL().get(""), new String[]{"id", "nome", "valor", "categoria"}, "nome");
        Stage stage = new Stage();
        stage.setScene(new Scene(mt));
        stage.setWidth(600);
        stage.setHeight(480);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        produto = (Produto) mt.getSelecionado();
        if (produto != null)
            btProduto.setText(produto.getNome());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coProduto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().produto().getNome()));
        coQuantidade.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().quant()));
        coValor.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().valor() * cellData.getValue().quant()));
        MaskFieldUtil.foneField(tfTelefone);
        spQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        carregarTiposPagamento();

        if (TabelaPedidosController.pedido != null) {
            //PREENCHER OS INPUTS
            Pedido pedido = TabelaPedidosController.pedido;
            tfID.setText("" + pedido.getId());
            tfCliente.setText(pedido.getNomeCliente());
            tfTelefone.setText(pedido.getFoneCliente());
            dpData.setValue(pedido.getData());
            int indexDoTipoPagamento = 0;
            TipoPagamento teste = null;
            for (int i = 0; i < cbTipoPagamento.getItems().size(); i++) {

                TipoPagamento tipoPagamentoDoPedido = cbTipoPagamento.getItems().get(i);
                System.out.println(tipoPagamentoDoPedido.getNome());
                if (tipoPagamentoDoPedido.getId() == pedido.getTipoPagamento().getId()) {
                    System.out.println("comboBoxID = " + tipoPagamentoDoPedido.getId());
                    System.out.println("pedidoID = " + pedido.getTipoPagamento().getId());
                    indexDoTipoPagamento = i;
                    teste = tipoPagamentoDoPedido;
                }


            }
            cbTipoPagamento.getSelectionModel().select(pedido.getTipoPagamento());
            tableView.setItems(FXCollections.observableArrayList(pedido.getItens()));
            alterarLabelValorTotal();

        }
    }

    private void carregarTiposPagamento() {
        List<TipoPagamento> tipoPagamentoList = new TipoPagamentoDAL().get("");
        cbTipoPagamento.setItems(FXCollections.observableArrayList(tipoPagamentoList));
        //cbTipoPagamento.getSelectionModel().select(0);// marcar o primeiro da lista
    }

}