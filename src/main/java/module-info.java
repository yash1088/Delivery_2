module com.concorida.tvm {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires java.desktop;


    opens com.concorida.tvm to javafx.fxml;
    exports com.concorida.tvm;
}