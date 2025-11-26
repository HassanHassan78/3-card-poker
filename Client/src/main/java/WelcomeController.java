import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WelcomeController {

    private ClientMain app;

    public void setApp(ClientMain app) {
        this.app = app;
    }
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private Label statusLabel;
    @FXML
    private void connectPressed() {
        try {
            String host = ipField.getText().trim();
            int port = Integer.parseInt(portField.getText().trim());
            statusLabel.setText("Connecting...");
            app.setserverInfo(host, port);
            app.showGameScene();
        }catch (Exception e) {
            statusLabel.setText("Invalid IP Address or Port");
        }
    }
}
