module org.earth.sim.earthsimjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens earthsim to javafx.fxml;
    exports earthsim;
}