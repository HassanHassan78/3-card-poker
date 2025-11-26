import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ServerMain extends Application {

    @FXML
    private TextArea logArea;

    private ServerThread serverThread;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Server.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/server.css").toExternalForm());
        primaryStage.setTitle("3 Card Poker – Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        appendLog("Server not running.");
    }

    @FXML
    private void startServer() {
        if (serverThread == null || !serverThread.isAlive()) {
            appendLog("Server thread started on port 5555.");
            serverThread = new ServerThread(this, 5555);
            serverThread.start();
        } else {
            appendLog("Server is already running.");
        }
    }

    @FXML
    private void stopServer() {
        if (serverThread != null) {
            appendLog("Server stop requested.");
            serverThread.stopServer();
            serverThread = null;
        } else {
            appendLog("Server is not running.");
        }
    }

    public void appendLog(String text) {
        Platform.runLater(() -> {
            if (logArea != null) {
                logArea.appendText(text + "\n");
            } else {
                System.out.println(text);
            }
        });
    }
}
