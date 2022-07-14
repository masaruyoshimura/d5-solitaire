package d5.solitaire.domain.model.game;

import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static d5.solitaire.domain.model.game.LaneIndex.*;

public class GameTest {
    
    @Test
    public void test初期状態() {
        var game = new Game(new Random(0));
        var actual = game.toString();
        assertEquals(""
            + "レーン0:♢9\r\n"
            + "レーン1:★♣K\r\n"
            + "レーン2:★★♢5\r\n"
            + "レーン3:★★★♠6\r\n"
            + "レーン4:★★★★♡6\r\n"
            + "レーン5:★★★★★♡Q\r\n"
            + "レーン6:★★★★★★♢2\r\n"
            + "ゴール♠:-\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:-\r\n"
            + "手札トップ:♣7", actual);
        assertEquals(Error.SUCCESS, game.error());
    }

    @Test
    public void test初期状態からundoしても何も起こらない() {
        var game = new Game(new Random(0)).undo();
        var actual = game.toString();
        assertEquals(""
            + "レーン0:♢9\r\n"
            + "レーン1:★♣K\r\n"
            + "レーン2:★★♢5\r\n"
            + "レーン3:★★★♠6\r\n"
            + "レーン4:★★★★♡6\r\n"
            + "レーン5:★★★★★♡Q\r\n"
            + "レーン6:★★★★★★♢2\r\n"
            + "ゴール♠:-\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:-\r\n"
            + "手札トップ:♣7", actual);
        assertEquals(Error.SUCCESS, game.error());
    }
    
    @Test
    public void testレーンからレーンの移動() {
        var game = new Game(new Random(0))
                .moveFromLaneToLane(LaneIndex.of(5), LaneIndex.of(1));
        var actual2 = game.toString();
        assertEquals(""
            + "レーン0:♢9\r\n"
            + "レーン1:★♣K♡Q\r\n"
            + "レーン2:★★♢5\r\n"
            + "レーン3:★★★♠6\r\n"
            + "レーン4:★★★★♡6\r\n"
            + "レーン5:★★★★♣8\r\n"
            + "レーン6:★★★★★★♢2\r\n"
            + "ゴール♠:-\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:-\r\n"
            + "手札トップ:♣7", actual2);
    }
    
    @Test
    public void test移動後にundo() {
        var game = new Game(new Random(0))
                .moveFromLaneToLane(LaneIndex.of(5), LaneIndex.of(1))
                .undo();
        var actual2 = game.toString();
        assertEquals(""
                + "レーン0:♢9\r\n"
                + "レーン1:★♣K\r\n"
                + "レーン2:★★♢5\r\n"
                + "レーン3:★★★♠6\r\n"
                + "レーン4:★★★★♡6\r\n"
                + "レーン5:★★★★★♡Q\r\n"
                + "レーン6:★★★★★★♢2\r\n"
                + "ゴール♠:-\r\n"
                + "ゴール♣:-\r\n"
                + "ゴール♡:-\r\n"
                + "ゴール♢:-\r\n"
                + "手札トップ:♣7", actual2);
    }
    
    @Test
    public void test手札からゴールへの移動() {
        var game = new Game(new Random(0))
            .moveFromLaneToLane(LaneIndex.of(5), LaneIndex.of(1))
            .moveFromLaneToLane(LaneIndex.of(5), LaneIndex.of(0))
            .moveFromLaneToLane(LaneIndex.of(2), LaneIndex.of(3))
            .moveFromLaneToLane(LaneIndex.of(2), LaneIndex.of(5))
            .moveFromLaneToLane(LaneIndex.of(6), LaneIndex.of(5))
            .rollHand()
            .rollHand()
            .rollHand()
            .rollHand()
            .rollHand()
            .moveFromHandToLane(LaneIndex.of(3))
            .moveFromHandToGoal();
        var actual2 = game.toString();
        assertEquals(""
            + "レーン0:♢9♣8\r\n"
            + "レーン1:★♣K♡Q\r\n"
            + "レーン2:♡J\r\n"
            + "レーン3:★★★♠6♢5♠4\r\n"
            + "レーン4:★★★★♡6\r\n"
            + "レーン5:★★★♢4♣3♢2\r\n"
            + "レーン6:★★★★★♡K\r\n"
            + "ゴール♠:1\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:-\r\n"
            + "手札トップ:♠5", actual2);
    }

    @Test
    public void testレーンからゴールへの移動() {
        var game = new Game(new Random(0))
            .moveFromLaneToLane(LaneIndex.of(5), LaneIndex.of(1))
            .moveFromLaneToLane(LaneIndex.of(5), LaneIndex.of(0))
            .moveFromLaneToLane(LaneIndex.of(2), LaneIndex.of(3))
            .moveFromLaneToLane(LaneIndex.of(2), LaneIndex.of(5))
            .moveFromLaneToLane(LaneIndex.of(6), LaneIndex.of(5))
            .rollHand()
            .rollHand()
            .rollHand()
            .rollHand()
            .rollHand()
            .moveFromHandToLane(LaneIndex.of(3))
            .moveFromHandToGoal()
            .rollHand()
            .rollHand()
            .moveFromHandToLane(LANE_4)
            .moveFromLaneToLane(LANE_5, LANE_4)
            .moveFromLaneToLane(LANE_5, LANE_3)
            .moveFromLaneToGoal(LANE_5)
            ;
        var actual2 = game.toString();
        assertEquals(""
            + "レーン0:♢9♣8\r\n"
            + "レーン1:★♣K♡Q\r\n"
            + "レーン2:♡J\r\n"
            + "レーン3:★★★♠6♢5♠4♢3\r\n"
            + "レーン4:★★★★♡6♣5♢4♣3♢2\r\n"
            + "レーン5:♡5\r\n"
            + "レーン6:★★★★★♡K\r\n"
            + "ゴール♠:1\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:1\r\n"
            + "手札トップ:♢Q", actual2);
    }
    
    @Test
    public void testレーンからレーンの移動_失敗() {
        var game = new Game(new Random(0))
                .moveFromLaneToLane(LaneIndex.of(5), LaneIndex.of(0));
        assertEquals(Error.NOT_MOVE_LANE_TO_LANE, game.error());
        var actual2 = game.toString();
        assertEquals(""
            + "レーン0:♢9\r\n"
            + "レーン1:★♣K\r\n"
            + "レーン2:★★♢5\r\n"
            + "レーン3:★★★♠6\r\n"
            + "レーン4:★★★★♡6\r\n"
            + "レーン5:★★★★★♡Q\r\n"
            + "レーン6:★★★★★★♢2\r\n"
            + "ゴール♠:-\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:-\r\n"
            + "手札トップ:♣7", actual2);
    }
    
    @Test
    public void testレーンからゴールへの移動_失敗() {
        var game = new Game(new Random(0))
                .moveFromLaneToGoal(LaneIndex.of(5));
        assertEquals(Error.NOT_MOVE_LANE_TO_GOAL, game.error());
        var actual2 = game.toString();
        assertEquals(""
            + "レーン0:♢9\r\n"
            + "レーン1:★♣K\r\n"
            + "レーン2:★★♢5\r\n"
            + "レーン3:★★★♠6\r\n"
            + "レーン4:★★★★♡6\r\n"
            + "レーン5:★★★★★♡Q\r\n"
            + "レーン6:★★★★★★♢2\r\n"
            + "ゴール♠:-\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:-\r\n"
            + "手札トップ:♣7", actual2);
    }
    

    @Test
    public void test手札からレーンへの移動_失敗() {
        var game = new Game(new Random(0))
                .moveFromHandToLane(LaneIndex.of(5));
        assertEquals(Error.NOT_MOVE_HAND_TO_LANE, game.error());
        var actual2 = game.toString();
        assertEquals(""
            + "レーン0:♢9\r\n"
            + "レーン1:★♣K\r\n"
            + "レーン2:★★♢5\r\n"
            + "レーン3:★★★♠6\r\n"
            + "レーン4:★★★★♡6\r\n"
            + "レーン5:★★★★★♡Q\r\n"
            + "レーン6:★★★★★★♢2\r\n"
            + "ゴール♠:-\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:-\r\n"
            + "手札トップ:♣7", actual2);
    }

    @Test
    public void test手札からゴールへの移動_失敗() {
        var game = new Game(new Random(0))
                .moveFromHandToGoal();
        assertEquals(Error.NOT_MOVE_HAND_TO_GOAL, game.error());
        var actual2 = game.toString();
        assertEquals(""
            + "レーン0:♢9\r\n"
            + "レーン1:★♣K\r\n"
            + "レーン2:★★♢5\r\n"
            + "レーン3:★★★♠6\r\n"
            + "レーン4:★★★★♡6\r\n"
            + "レーン5:★★★★★♡Q\r\n"
            + "レーン6:★★★★★★♢2\r\n"
            + "ゴール♠:-\r\n"
            + "ゴール♣:-\r\n"
            + "ゴール♡:-\r\n"
            + "ゴール♢:-\r\n"
            + "手札トップ:♣7", actual2);
    }
}
