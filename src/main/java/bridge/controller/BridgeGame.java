package bridge.controller;

import bridge.domain.Bridge;
import bridge.domain.GameResult;
import bridge.domain.PositionTable;

import java.util.stream.Stream;

public class BridgeGame {
    private Bridge bridge;
    private PositionTable userTable;
    private int tryNumber = 0;

    private BridgeGame(Bridge bridge, PositionTable userTable) {
        this.bridge = bridge;
        this.userTable = userTable;
    }
    public static BridgeGame of(Bridge bridge, PositionTable userTable) {
        return new BridgeGame(bridge, userTable);
    }

    public GameResult determineRetry(GameResult gameResult) {
        if (gameResult.isLose()) {
            gameResult = GameResult.retryOrNot(BridgeGameManager.readGameCommand());
        }
        return gameResult;
    }

    public GameResult move() {
        return Stream.iterate(0,i->i<bridge.size(),i->i+1)
                .map(i-> BridgeGameManager.moveUser(userTable, bridge))
                .filter(GameResult::isNotKeep)
                .findFirst()
                .orElse(GameResult.KEEP);
    }
    public int getTryNumber() {
        return tryNumber;
    }
    public GameResult retry(GameResult gameResult) {
        while (gameResult.isKeep()) {
            gameResult = startGame();
        }
        return gameResult;
    }

    public GameResult startGame() {
        tryNumber += 1;
        GameResult gameResult = move();
        return determineRetry(gameResult);
    }
}
