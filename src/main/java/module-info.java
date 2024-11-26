module unoeste.fipp.sistemafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.json;
    requires java.sql;
    requires java.desktop;
    requires itext;


    opens unoeste.fipp.sistemafx to javafx.fxml;
    opens unoeste.fipp.sistemafx.db.entidade to javafx.fxml;
    exports unoeste.fipp.sistemafx;
    exports unoeste.fipp.sistemafx.db.entidade;

}