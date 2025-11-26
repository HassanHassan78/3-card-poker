import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private Stage primaryStage;
    private String serverHost;
    private int serverPort;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Welcome.fxml"));
        Parent root = loader.load();
        WelcomeController controller = loader.getController();
        controller.setApp(this);
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/client.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("3 Card Poker");
        primaryStage.show();

    }
    public void setserverInfo(String host, int port){
        serverHost = host;
        serverPort = port;
    }
    public String getserverHost(){
        return serverHost;
    }
    public int getServerPort () {
        return serverPort;
    }
    public void startRound(PokerInfo request, GameController controller ){
        new ClientThread(serverHost, serverPort, request, controller).start();
    }
    public void appendLog(String text){
        Platform.runLater(()->{System.out.println(text);});
    }
    public void showGameScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.setApp(this);
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/client.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("3 Card Poker");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
