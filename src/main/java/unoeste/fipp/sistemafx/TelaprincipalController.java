package unoeste.fipp.sistemafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaprincipalController {
    public void onFechar(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja Fechar?");
        if (alert.showAndWait().get()==ButtonType.OK)
           Platform.exit();
    }

    public void onCadEmpresa(ActionEvent actionEvent) throws Exception{
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

    public void onCadProduto(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("produto-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("Cadastro de produtos");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onNovoPedido(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("form-pedidos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("Pedido de produtos");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onAbrirPedido(ActionEvent actionEvent) {
    }

    public void btAdicionar(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("form-pedidos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("Pedido de produtos");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
     public void onCadCategoria(ActionEvent event) throws Exception {
         FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("categoria-view.fxml"));
         Scene scene = new Scene(fxmlLoader.load());
         Stage stage = new Stage();
         stage.setTitle("Pedido de produtos");
         stage.setScene(scene);
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.showAndWait();
     }
    public void onTabelaEmpresas(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("tabela-empresa.fxml"));
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
