module np.com.satyarajawasthi.smartcreditmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    exports np.com.satyarajawasthi.smartcreditmanager;
    opens np.com.satyarajawasthi.smartcreditmanager.controller to javafx.fxml;

}