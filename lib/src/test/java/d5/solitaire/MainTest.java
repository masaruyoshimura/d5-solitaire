package d5.solitaire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    
    String LINE_SEPARATOR = System.lineSeparator(); 

    @Test
    public void test初期表示() throws UnsupportedEncodingException {
        Random random = new Random(0L);
        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream, true, "UTF-8");
        try {
            Main.game(random, in, out);
            org.junit.jupiter.api.Assertions.fail("scanner.next() で例外が発生する");
        } catch (NoSuchElementException e) {
            String actual = byteArrayOutputStream.toString("UTF-8");
            assertEquals(""
                + "レーン0:♢9" + LINE_SEPARATOR
                + "レーン1:★♣K" + LINE_SEPARATOR
                + "レーン2:★★♢5" + LINE_SEPARATOR
                + "レーン3:★★★♠6" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★★★♡Q" + LINE_SEPARATOR
                + "レーン6:★★★★★★♢2" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:", actual);
        }
    }
    
    @Test
    public void testレーンからレーンの移動() throws UnsupportedEncodingException {
        Random random = new Random(0L);
        ByteArrayInputStream in = new ByteArrayInputStream("5\n1\n".getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream, true, "UTF-8");
        try {
            Main.game(random, in, out);
            org.junit.jupiter.api.Assertions.fail("scanner.next() で例外が発生する");
        } catch (NoSuchElementException e) {
            String actual = byteArrayOutputStream.toString("UTF-8");
            assertEquals(""
                + "レーン0:♢9" + LINE_SEPARATOR
                + "レーン1:★♣K" + LINE_SEPARATOR
                + "レーン2:★★♢5" + LINE_SEPARATOR
                + "レーン3:★★★♠6" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★★★♡Q" + LINE_SEPARATOR
                + "レーン6:★★★★★★♢2" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:レーン5をどうしますか？(操作対象のレーン。ゴールの場合は7）:★:レーン5からレーン1への移動" + LINE_SEPARATOR
                + "レーン0:♢9" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:★★♢5" + LINE_SEPARATOR
                + "レーン3:★★★♠6" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★★♣8" + LINE_SEPARATOR
                + "レーン6:★★★★★★♢2" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:", actual);
        }
    }
    
    @Test
    public void test手札からゴールへの移動() throws UnsupportedEncodingException {
        Random random = new Random(0L);
        ByteArrayInputStream in = new ByteArrayInputStream("5\n1\n5\n0\n2\n3\n2\n5\n6\n5\n7\n8\n7\n8\n7\n8\n7\n8\n7\n8\n7\n3\n7\n7\n".getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream, true, "UTF-8");
        try {
            Main.game(random, in, out);
            org.junit.jupiter.api.Assertions.fail("scanner.next() で例外が発生する");
        } catch (NoSuchElementException e) {
            String actual = byteArrayOutputStream.toString("UTF-8");
            assertEquals(""
                + "レーン0:♢9" + LINE_SEPARATOR
                + "レーン1:★♣K" + LINE_SEPARATOR
                + "レーン2:★★♢5" + LINE_SEPARATOR
                + "レーン3:★★★♠6" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★★★♡Q" + LINE_SEPARATOR
                + "レーン6:★★★★★★♢2" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:レーン5をどうしますか？(操作対象のレーン。ゴールの場合は7）:★:レーン5からレーン1への移動" + LINE_SEPARATOR
                + "レーン0:♢9" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:★★♢5" + LINE_SEPARATOR
                + "レーン3:★★★♠6" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★★♣8" + LINE_SEPARATOR
                + "レーン6:★★★★★★♢2" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:レーン5をどうしますか？(操作対象のレーン。ゴールの場合は7）:★:レーン5からレーン0への移動" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:★★♢5" + LINE_SEPARATOR
                + "レーン3:★★★♠6" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4" + LINE_SEPARATOR
                + "レーン6:★★★★★★♢2" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:レーン2をどうしますか？(操作対象のレーン。ゴールの場合は7）:★:レーン2からレーン3への移動" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:★♣3" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4" + LINE_SEPARATOR
                + "レーン6:★★★★★★♢2" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:レーン2をどうしますか？(操作対象のレーン。ゴールの場合は7）:★:レーン2からレーン5への移動" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3" + LINE_SEPARATOR
                + "レーン6:★★★★★★♢2" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:レーン6をどうしますか？(操作対象のレーン。ゴールの場合は7）:★:レーン6からレーン5への移動" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3♢2" + LINE_SEPARATOR
                + "レーン6:★★★★★♡K" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:★:手札を繰る" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3♢2" + LINE_SEPARATOR
                + "レーン6:★★★★★♡K" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♣2" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:★:手札を繰る" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3♢2" + LINE_SEPARATOR
                + "レーン6:★★★★★♡K" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♠7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:★:手札を繰る" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3♢2" + LINE_SEPARATOR
                + "レーン6:★★★★★♡K" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♠9" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:★:手札を繰る" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3♢2" + LINE_SEPARATOR
                + "レーン6:★★★★★♡K" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♡7" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:★:手札を繰る" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3♢2" + LINE_SEPARATOR
                + "レーン6:★★★★★♡K" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♠4" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5♠4" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3♢2" + LINE_SEPARATOR
                + "レーン6:★★★★★♡K" + LINE_SEPARATOR
                + "ゴール♠:-" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♠A" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:★:手札をゴールへ" + LINE_SEPARATOR
                + "レーン0:♢9♣8" + LINE_SEPARATOR
                + "レーン1:★♣K♡Q" + LINE_SEPARATOR
                + "レーン2:♡J" + LINE_SEPARATOR
                + "レーン3:★★★♠6♢5♠4" + LINE_SEPARATOR
                + "レーン4:★★★★♡6" + LINE_SEPARATOR
                + "レーン5:★★★♢4♣3♢2" + LINE_SEPARATOR
                + "レーン6:★★★★★♡K" + LINE_SEPARATOR
                + "ゴール♠:1" + LINE_SEPARATOR
                + "ゴール♣:-" + LINE_SEPARATOR
                + "ゴール♡:-" + LINE_SEPARATOR
                + "ゴール♢:-" + LINE_SEPARATOR
                + "手札トップ:♠5" + LINE_SEPARATOR
                + "どうしますか？(操作対象のレーン。手札の場合は7）:", actual);
        }
    }
}
