# Networked 3-Card Poker (Java / JavaFX / Sockets)

This project is a networked **3-Card Poker** game built with **Java 11**, **JavaFX**, and **TCP sockets**.  
It consists of two separate Maven projects:

- `Server` – handles game logic, deals cards, evaluates hands, and responds to clients.
- `Client` – JavaFX GUI that connects to the server, places bets, and displays the game visually (with card images).

---

## 1. Features

- Client–server architecture using **TCP sockets**
- **JavaFX GUI** for the client and server (FXML + CSS)
- Full **3-Card Poker logic**:
  - Hand evaluation (straight flush, three of a kind, straight, flush, pair, high card)
  - Pair Plus payouts
  - Dealer qualification rules
  - Winnings calculation for Play / Fold
- Dealer cards appear **face down** after Deal and flip face up on Play/Fold
- **JUnit tests** for core poker logic (`ThreeCardLogic`)
- Separate **Client** and **Server** Maven projects

---

## 2. Technology Stack

- **Language:** Java 11  
- **GUI:** JavaFX (FXML + CSS)  
- **Build Tool:** Maven  
- **Networking:** Java Sockets (`ServerSocket`, `Socket`)  
- **Testing:** JUnit  
- **Serialization:** Java `Serializable` (for `PokerInfo` and `Card`)

---

## 3. Project Structure

``` text
hhass31Project3/
├── Client/
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   ├── ClientMain.java
│           │   ├── GameController.java
│           │   ├── WelcomeController.java
│           │   ├── ClientThread.java
│           │   ├── Card.java
│           │   └── PokerInfo.java
│           └── resources/
│               ├── Welcome.fxml
│               ├── Game.fxml
│               ├── client.css
│               └── cards/
│                   ├── 2C.png
│                   ├── 2D.png
│                   ├── ...
│                   ├── JH.png
│                   ├── QS.png
│                   ├── AH.png
│                   └── back.png
└── Server/
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/
        │   │   ├── ServerMain.java
        │   │   ├── ServerController.java
        │   │   ├── ServerThread.java
        │   │   ├── ThreeCardLogic.java
        │   │   ├── Deck.java
        │   │   ├── Card.java
        │   │   └── PokerInfo.java
        │   └── resources/
        │       ├── Server.fxml
        │       └── server.css
        └── test/
            └── java/
                └── MyTest.java
  ```

---

## 4. How the Game Works

1. Server is launched and begins listening on a TCP port.
2. Client connects to the server using the Welcome screen.
3. Player places **Ante** and **Pair Plus** bets and clicks **Deal**.
4. Server sends cards and results. Player sees their cards, dealer’s cards are face-down.
5. Player chooses **Play** or **Fold**.
6. Server evaluates result and sends back winnings, updated hands, and payout messages.

---

## 5. Requirements

- Java 11+
- Maven 3.6+
- JavaFX libraries (managed by `pom.xml`)

---

## 6. Running the Server

```bash
cd Server
mvn clean compile exec:java
```
---

## 7. Running the Client

```bash
cd Client
mvn clean compile exec:java
```
---

## 8. Playing a Round (Step by Step)
- Start server, then client.
- in client enter Ante and Pair Plus bets
- Click Deal:
  - Player's cards shown.
  - Dealers cards hidden
- Choose Play or Fold
- Dealer'scards flip to show real hand
- winnings are calculated and updated
---




