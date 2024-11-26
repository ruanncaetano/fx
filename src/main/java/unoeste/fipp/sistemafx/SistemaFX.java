package unoeste.fipp.sistemafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unoeste.fipp.sistemafx.db.util.SingletonDB;

import javax.swing.*;
import java.io.IOException;

public class SistemaFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaFX.class.getResource("telaprincipal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PedidosFX v 0.1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if(!SingletonDB.conectar())
        {
            JOptionPane.showMessageDialog(null,"Erro ao acessar o banco"+
                    SingletonDB.getConexao().getMensagemErro());
            Platform.exit();
        }
        launch();
    }
}