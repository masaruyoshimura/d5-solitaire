package d5.solitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import io.vavr.Tuple2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LanesTest {

    @Test
    public void testFromSet() {
        List<Integer> set = new ArrayList<>(IntStream.range(0, 13*4).mapToObj(i -> i).collect(Collectors.toList()));
        var lanesFromSet = Lanes.fromSet(set);
        var list = new ArrayList<>(List.of(
                List.of(fore(0)),
                List.of(back(1), fore(2)),
                List.of(back(3), back(4), fore(5)),
                List.of(back(6), back(7), back(8), fore(9)),
                List.of(back(10), back(11), back(12), back(13), fore(14)),
                List.of(back(15), back(16), back(17), back(18), back(19), fore(20)),
                List.of(back(21), back(22), back(23), back(24), back(25), back(26), fore(27))
                ));
        var lanesFromLanes = Lanes.fromLanes(list);
        assertEquals(lanesFromLanes, lanesFromSet);
        assertTrue(lanesFromLanes.lanes.equals(list));
        assertTrue(lanesFromLanes.lanes !=  list); // equals だけどオブジェクトとしては異なる（deepCopy）
    }
    
    @Test
    public void testToString() {
        List<Integer> set = new ArrayList<>(IntStream.range(0, 13*4).mapToObj(i -> i).collect(Collectors.toList()));
        var lanes = Lanes.fromSet(set);
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
            Lanes.fromLanes(List.of(
                List.of(fore(0)), // ♠A
                List.of(fore(13*2+1)) // ♡2
            )).moveFromLaneToLane(0, 1),
            Lanes.fromLanes(List.of(
                List.of(), // empty
                List.of(fore(13*2+1), fore(0)) // ♡2♠A
            ))); // 一枚だけ移動
        assertEquals(
            Lanes.fromLanes(List.of(
                List.of(fore(2)), // ♠3
                List.of(back(12), fore(13*2+1), fore(0)) // ★♡2♠A
            )).moveFromLaneToLane(1, 0),
            Lanes.fromLanes(List.of(
                List.of(fore(2), fore(13*2+1), fore(0)), // ♠3♡2♠A
                List.of(fore(12)) // ♠K
            ))); // 二枚つながってるものをセットで移動、移動後の裏カードが表になる
    }

    @Test
    public void testMoveFromLaneToLane空列にKが移動できる() {
        assertEquals(
            Lanes.fromLanes(List.of(
                List.of(fore(12)), // ♠K
                List.of() // empty
            )).moveFromLaneToLane(0, 1),
            Lanes.fromLanes(List.of(
                List.of(), // empty
                List.of(fore(12)) // ♠K
            ))); // 一枚だけ移動
        assertEquals(
            Lanes.fromLanes(List.of(
                List.of(), // empty
                List.of(back(0), fore(12), fore(13*2+11)) // ★♠K♡Q
            )).moveFromLaneToLane(1, 0),
            Lanes.fromLanes(List.of(
                List.of(fore(12), fore(13*2+11)), // ★♠K♡Q
                List.of(fore(0)) // 2♠A
            ))); // 二枚つながってるものをセットで移動、移動後の裏カードが表になる
    }

    @Test
    public void testMoveFromLaneToLane異常系() {
        var lanes = Lanes.fromLanes(List.of(
                List.of(fore(0)), // ♠A
                List.of(fore(13*1+1)) // ♣2
            ));
        assertTrue(lanes.moveFromLaneToLane(0, 1) == lanes); // 同色連番は移動できないので同じインスタンス
        lanes = Lanes.fromLanes(List.of(
                List.of(fore(0)), // ♠A
                List.of() // empty
            ));
        assertTrue(lanes.moveFromLaneToLane(0, 1) == lanes); // 空列に移動できるのはK始まりのみ
        lanes = Lanes.fromLanes(List.of(
                List.of(fore(13*2+1), fore(0)), // ♡2♠A
                List.of(fore(13*3+1)) // ♢2
            ));
        assertTrue(lanes.moveFromLaneToLane(0, 1) == lanes); // FIXME laneの途中からの移動に対応していない
    }

    // 表
    private static Tuple2<Integer, Boolean> fore(int i) {
        return new Tuple2<>(i, true);
    }

    // 裏
    private static Tuple2<Integer, Boolean> back(int i) {
        return new Tuple2<>(i, false);
    }
}
