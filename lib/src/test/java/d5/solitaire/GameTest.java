package d5.solitaire;

import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    
    @Test
    public void test初期状態() {
        var game = new Game(new Random(0));
        var actual = game.display();
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
    }
    

    @Test
    public void testレーンからレーンの移動() {
        var game = new Game(new Random(0))
                .step(5, 1);
        String actual = game.lastMessage();
        assertEquals("★:レーン5からレーン1への移動\r\n", actual);
        var actual2 = game.display();
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
    public void test手札からゴールへの移動() {
        var game = new Game(new Random(0))
            .step(5, 1)
            .step(5, 0)
            .step(2, 3)
            .step(2, 5)
            .step(6, 5)
            .step(7, 8)
            .step(7, 8)
            .step(7, 8)
            .step(7, 8)
            .step(7, 8)
            .step(7, 3)
            .step(7, 7);
        String actual = game.lastMessage();
        assertEquals("★:手札をゴールへ\r\n", actual);
        var actual2 = game.display();
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
}
