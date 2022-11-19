package bridge.domain;

import java.util.List;

public class Bridge {
    private final PositionTable bridgeTable;

    private Bridge(List<String> bridge) {
        this.bridgeTable = mapToPositionTable(bridge);
    }

    private static PositionTable mapToPositionTable(List<String> bridge) {
        PositionTable positionTable = PositionTable.newInstance();
        bridge.stream().map(s -> Position.of(s)).forEach(positionTable::add);
        return positionTable;
    }

    public static Bridge of(List<String> generatedBridge) {
        return new Bridge(generatedBridge);
    }

    public Result play(PositionTable userTable) {
        if (bridgeTable.equals(userTable)) {
            return Result.WIN;
        }
        if (bridgeTable.matchLastOf(userTable)) {
            return Result.KEEP;
        }
        return Result.LOSE;
    }
}

