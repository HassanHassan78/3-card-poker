import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController {

    private ClientMain app;
    private int totalWinnings = 0;
    private PokerInfo lastDealInfo;
    private String getImageName(Card card) {
        int value = card.getValue();
        char suit = card.getSuit();

        String valuePart;
        if (value >= 2 && value <= 10) {
            valuePart = Integer.toString(value);
        } else if (value == 11) {
            valuePart = "J";
        } else if (value == 12) {
            valuePart = "Q";
        } else if (value == 13) {
            valuePart = "K";
        } else {
            valuePart = "A";
        }

        return valuePart + suit + ".png";
    }

    private void addCardImage(HBox box, Card card) {
        try {
            String fileName = getImageName(card);
            Image image = new Image(getClass().getResourceAsStream("/Cards/" + fileName));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(120);
            imageView.setPreserveRatio(true);
            box.getChildren().add(imageView);
        } catch (Exception e) {
            Label label = new Label(card.toString());
            box.getChildren().add(label);
        }
    }
    private void addCardBack(HBox box) {
        Image image = new Image(getClass().getResourceAsStream("/Cards/back.png"));
        ImageView view = new ImageView(image);
        view.setFitWidth(80);
        view.setFitHeight(120);
        view.setPreserveRatio(true);
        box.getChildren().add(view);
    }


    public void setApp(ClientMain app) {
        this.app = app;
    }
    public void appendGameMessage(String message){
        Platform.runLater(()->{gameInfoArea.appendText(message+"\n");});
    }
    public void updateFromPokerInfo(PokerInfo info) {
        Platform.runLater(() -> {
            playerCardsBox.getChildren().clear();
            dealerCardsBox.getChildren().clear();
            for (Card c : info.playerHand) {
                addCardImage(playerCardsBox, c);
            }
            if ("DEAL".equals(info.messageType)) {
                addCardBack(dealerCardsBox);
                addCardBack(dealerCardsBox);
                addCardBack(dealerCardsBox);
            } else {
                for (Card c : info.dealerHand) {
                    addCardImage(dealerCardsBox, c);
                }
            }
            if ("DEAL".equals(info.messageType)) {
                lastDealInfo = info;
                appendGameMessage("Cards dealt. PairPlus (potential): " + info.pairPlusWinnings);
            } else if ("RESULT".equals(info.messageType)) {
                totalWinnings += info.pairPlusWinnings + info.handWinnings;
                totalWinningsLabel.setText("$" + totalWinnings);
                appendGameMessage("Round result: " + info.statusMessage);
                appendGameMessage("PairPlus: " + info.pairPlusWinnings + ", Hand: " + info.handWinnings);
                lastDealInfo = null;
            } else {
                appendGameMessage("Received " + info.messageType + ": " + info.statusMessage);
            }
        });
    }

    @FXML
    private HBox playerCardsBox;

    @FXML
    private HBox dealerCardsBox;

    @FXML
    private TextField anteField;

    @FXML
    private TextField pairPlusField;

    @FXML
    private Label totalWinningsLabel;

    @FXML
    private TextArea gameInfoArea;

    @FXML
    private void initialize() {
        gameInfoArea.setText("Welcome to the Game!");
        totalWinningsLabel.setText("$0");
    }
    @FXML
    private void onDeal() {
        try {
            int ante = Integer.parseInt(anteField.getText().trim());
            int pairPlus = Integer.parseInt(pairPlusField.getText().trim());
            appendGameMessage("Sending bets: ante =" + ante + " pairPlus =" + pairPlus);
            PokerInfo info = new PokerInfo("BET");
            info.anteBet = ante;
            info.pairPlusBet = pairPlus;
            app.startRound(info, this);
        } catch (Exception e) {
            appendGameMessage("invalid bet values.");
        }
    }

    @FXML
    private void onPlay() {
        if (lastDealInfo == null) {
            appendGameMessage("You must Deal before you can play!");
            return;
        }
        appendGameMessage("Play clicked");
        PokerInfo info = new PokerInfo("PLAY");
        info.anteBet = lastDealInfo.anteBet;
        info.pairPlusBet = lastDealInfo.pairPlusBet;
        info.playerHand.addAll(lastDealInfo.playerHand);
        info.dealerHand.addAll(lastDealInfo.dealerHand);
        app.startRound(info, this);
    }

    @FXML
    private void onFold() {
        if (lastDealInfo == null) {
            appendGameMessage("You must Deal before you can Fold!");
            return;
        }
        appendGameMessage("Fold Clicked");
        PokerInfo info = new PokerInfo("FOLD");
        info.anteBet = lastDealInfo.anteBet;
        info.pairPlusBet = lastDealInfo.pairPlusBet;
        info.playerHand.addAll(lastDealInfo.playerHand);
        info.dealerHand.addAll(lastDealInfo.dealerHand);
        app.startRound(info, this);
    }

}
