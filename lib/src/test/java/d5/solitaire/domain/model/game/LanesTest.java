package d5.solitaire.domain.model.game;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import io.vavr.Tuple2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static d5.solitaire.domain.model.game.LaneIndex.*;

public class LanesTest {

    @Test
    public void testFromSet() {
        List<Card> set = Stream.of(Suit.values()).flatMap(s -> Rank.stream().map(r -> Card.of(r, s))).collect(Collectors.toList());
        var lanesFromSet = new CardsArranger().arrange(new Cards(set))._1;
        var list = Map.of(
                LANE_0, new Lane(List.of(fore(0))),
                LANE_1,new Lane(List.of(back(1), fore(2))),
                LANE_2,new Lane(List.of(back(3), back(4), fore(5))),
                LANE_3,new Lane(List.of(back(6), back(7), back(8), fore(9))),
                LANE_4,new Lane(List.of(back(10), back(11), back(12), back(13), fore(14))),
                LANE_5,new Lane(List.of(back(15), back(16), back(17), back(18), back(19), fore(20))),
                LANE_6,new Lane(List.of(back(21), back(22), back(23), back(24), back(25), back(26), fore(27)))
                );
        var lanesFromLanes = new Lanes(list);
        assertEquals(lanesFromLanes, lanesFromSet);
        assertTrue(lanesFromLanes.lanes.equals(list));
        assertTrue(lanesFromLanes.lanes !=  list); // equals だけどオブジェクトとしては異なる（deepCopy）
    }
    
    @Test
    public void testToString() {
        List<Card> set = Stream.of(Suit.values()).flatMap(s -> Rank.stream().map(r -> Card.of(r, s))).collect(Collectors.toList());
        var lanes = new CardsArranger().arrange(new Cards(set))._1;
        assertEquals("レーン0:♠A\r\n"
                + "レーン1:★♠3\r\n"
                + "レーン2:★★♠6\r\n"
                + "レーン3:★★★♠10\r\n"
                + "レーン4:★★★★♣2\r\n"
                + "レーン5:★★★★★♣8\r\n"
                + "レーン6:★★★★★★♡2\r\n", lanes.toString());
    }
    
    @Test
    public void testMoveFromLaneToLane色違いが移動できる() {
        assertEquals(
            new Lanes(Map.of(
                    LANE_0, new Lane(List.of(fore(0))), // ♠A
                    LANE_1, new Lane(List.of(fore(13*2+1))) // ♡2
            )).moveFromLaneToLane(LANE_0, LANE_1),
            new Lanes(Map.of(
                    LANE_0,new Lane(List.of()), // empty
                    LANE_1,new Lane(List.of(fore(13*2+1), fore(0))) // ♡2♠A
            ))); // 一枚だけ移動
        assertEquals(
            new Lanes(Map.of(
                    LANE_0,new Lane(List.of(fore(2))), // ♠3
                    LANE_1,new Lane(List.of(back(12), fore(13*2+1), fore(0))) // ★♡2♠A
            )).moveFromLaneToLane(LANE_1, LANE_0),
            new Lanes(Map.of(
                    LANE_0,new Lane(List.of(fore(2), fore(13*2+1), fore(0))), // ♠3♡2♠A
                    LANE_1,new Lane(List.of(fore(12))) // ♠K
            ))); // 二枚つながってるものをセットで移動、移動後の裏カードが表になる
        assertEquals(
                new Lanes(Map.of(
                        LANE_0,new Lane(List.of(fore(13*2+1), fore(0))), // ♡2♠A
                        LANE_1,new Lane(List.of(fore(13*3+1))) // ♢2
                )).moveFromLaneToLane(LANE_0, LANE_1),
                new Lanes(Map.of(
                        LANE_0,new Lane(List.of(fore(13*2+1))), // ♡2
                        LANE_1,new Lane(List.of(fore(13*3+1), fore(0))) // ♢2♠A
                ))); // laneの途中からの移動（♠Aだけが移動している）

    }

    @Test
    public void testMoveFromLaneToLane空列にKが移動できる() {
        assertEquals(
            new Lanes(Map.of(
                    LANE_0,new Lane(List.of(fore(12))), // ♠K
                    LANE_1,new Lane(List.of()) // empty
            )).moveFromLaneToLane(LANE_0, LANE_1),
            new Lanes(Map.of(
                    LANE_0,new Lane(List.of()), // empty
                    LANE_1,new Lane(List.of(fore(12))) // ♠K
            ))); // 一枚だけ移動
        assertEquals(
            new Lanes(Map.of(
                    LANE_0,new Lane(List.of()), // empty
                    LANE_1,new Lane(List.of(back(0), fore(12), fore(13*2+11))) // ★♠K♡Q
            )).moveFromLaneToLane(LANE_1, LANE_0),
            new Lanes(Map.of(
                    LANE_0,new Lane(List.of(fore(12), fore(13*2+11))), // ♠K♡Q
                    LANE_1,new Lane(List.of(fore(0))) // ♠A
            ))); // 二枚つながってるものをセットで移動、移動後の裏カードが表になる
    }

    @Test
    public void testMoveFromLaneToLane異常系() {
        var lanes = new Lanes(Map.of(
                LANE_0,new Lane(List.of(fore(0))), // ♠A
                LANE_1,new Lane(List.of(fore(13*1+1))) // ♣2
            ));
        assertNull(lanes.moveFromLaneToLane(LANE_0, LANE_1)); // 同色連番は移動できないのでnull
        lanes = new Lanes(Map.of(
                LANE_0,new Lane(List.of(fore(0))), // ♠A
                LANE_1,new Lane(List.of()) // empty
            ));
        assertNull(lanes.moveFromLaneToLane(LANE_0, LANE_1)); // 空列に移動できるのはK始まりのみ
    }

    // 表
    private static Tuple2<Card, Boolean> fore(int i) {
        return new Tuple2<>(Card.of(Rank.of(i%13+1), Suit.values()[i/13]), true);
    }

    // 裏
    private static Tuple2<Card, Boolean> back(int i) {
        return new Tuple2<>(Card.of(Rank.of(i%13+1), Suit.values()[i/13]), false);
    }
}
