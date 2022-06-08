package d5.solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import io.vavr.Tuple2;

public class Game {

    List<Integer> set; // トランプ一組、手札

    List<List<Tuple2<Integer, Boolean>>> lines; // レーン7組

    List<List<Integer>> goals; // ゴール4組

    int index; // 手札のindex

    public Game(Random random) {
        // 初期化
        set = new ArrayList<Integer>(); // トランプ一組、手札
        for (int i = 0; i < 13 * 4; i++) {
            set.add(i);
        }
        Collections.shuffle(set, random); // トランプをシャッフル
        lines = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            var line = new ArrayList<Tuple2<Integer, Boolean>>();
            lines.add(line);
            for (int j = 0; j < i + 1; j++) {
                line.add(new Tuple2<>(set.remove(0), i == j)); // カードと、裏表のboolean（表ならtrue）
            }
        }
        goals = new ArrayList<List<Integer>>();
        for (int i = 0; i < 4; i++) {
            var goal = new ArrayList<Integer>();
            goals.add(goal);
        }
        index = 0; // 手札のindex
    }

    public String display() {
        StringBuilder ret = new StringBuilder();
        // 表示
        for (int i = 0; i < 7; i++) {
            var line = lines.get(i);
            ret.append("レーン" + i + ":");
            for (int j = 0; j < line.size(); j++) {
                var card = line.get(j);
                if (card._2) {
                    ret.append(suit(card._1) + number(card._1));
                } else {
                    ret.append("★");
                }
            }
            ret.append("\r\n");
        }
        for (int i = 0; i < 4; i++) {
            var goal = goals.get(i);
            ret.append("ゴール" + suit2(i) + ":" + (goal.size() == 0 ? "-" : goal.size()));
            ret.append("\r\n");
        }
        var card = set.isEmpty() ? null : set.get(index);
        ret.append("手札トップ:" + (card == null ? "無し" : (suit(card) + number(card))));
        return ret.toString();
    }

    public String step(int commandNum, int command2Num) {
        StringBuilder ret = new StringBuilder();
        var card = set.isEmpty() ? null : set.get(index);
        // 入力、処理
        if (commandNum <= 6) {
            if (command2Num <= 6) {
                ret.append("★:レーン" + commandNum + "からレーン" + command2Num + "への移動");
                ret.append("\r\n");
                var line = lines.get(commandNum);
                var move = line.stream().filter(t -> t._2).findFirst().get();
                var line2 = lines.get(command2Num);
                boolean success = false;
                if (line2.isEmpty()) { // 空列に移動できるのは K
                    if (move._1 % 13 == 12) { // K
                        for (int i = 0; i < line.size(); i++) {
                            if (line.get(i) == move) {
                                line2.addAll(line.subList(i, line.size()));
                                line.removeAll(line.subList(i, line.size()));
                                if (!line.isEmpty()) {
                                    line.set(line.size() - 1, new Tuple2<>(line.get(line.size() - 1)._1, true));
                                }
                                success = true;
                                break;
                            }
                        }
                    }
                } else { // 空列以外に移動できるのは色違い連番
                    // TODO lineの最前(move)からの移動だけでなく、途中からの移動にも対応する
                    var line2Last = line2.get(line2.size() - 1);
                    if ((line2Last._1.intValue() % 13) == (move._1.intValue() % 13) + 1
                        && (((move._1.intValue() / 26 + 1) + (line2Last._1.intValue() / 26 + 1)) == 0b11)) {
                        for (int i = 0; i < line.size(); i++) {
                            if (line.get(i) == move) {
                                line2.addAll(line.subList(i, line.size()));
                                line.removeAll(line.subList(i, line.size()));
                                if (!line.isEmpty()) {
                                    line.set(line.size() - 1, new Tuple2<>(line.get(line.size() - 1)._1, true));
                                }
                                success = true;
                                break;
                            }
                        }
                    }
                }
                if (!success) {
                    ret.append("★★エラー: 移動できません");
                    ret.append("\r\n");
                }
            } else {
                ret.append("★:レーン" + commandNum + "をゴールへ");
                ret.append("\r\n");
                var line = lines.get(commandNum);
                var lineLast = line.get(line.size() - 1);
                var goal = goals.get(lineLast._1 / 13);
                if (goal.size() == lineLast._1 % 13) {
                    goal.add(lineLast._1);
                    line.remove(line.size() - 1);
                    if (!line.isEmpty()) {
                        line.set(line.size() - 1, new Tuple2<>(line.get(line.size() - 1)._1, true));
                    }
                } else {
                    ret.append("★★エラー: 移動できません");
                    ret.append("\r\n");
                }
            }
        } else {
            if (card != null) {
                if (command2Num <= 6) {
                    var line2 = lines.get(command2Num);
                    boolean success = false;
                    if (line2.isEmpty()) { // 空列に移動できるのは K
                        if (card % 13 == 12) { // K
                            line2.add(new Tuple2<>(card, true));
                            set.remove(index);
                            if (index >= set.size()) {
                                index--;
                            }
                            success = true;
                        }
                    } else { // 空列以外に移動できるのは色違い連番
                        var line2Last = line2.get(line2.size() - 1);
                        if ((line2Last._1.intValue() % 13) == (card.intValue() % 13) + 1
                            && (((card.intValue() / 26 + 1)
                                + (line2Last._1.intValue() / 26 + 1)) == 0b11)) {
                            line2.add(new Tuple2<>(card, true));
                            set.remove(index);
                            if (index >= set.size()) {
                                index--;
                            }
                            success = true;
                        }
                    }
                    if (!success) {
                        ret.append("★★エラー: 移動できません");
                        ret.append("\r\n");
                    }
                } else if (command2Num == 7) {
                    ret.append("★:手札をゴールへ");
                    ret.append("\r\n");
                    var goal = goals.get(card / 13);
                    if (goal.size() == card % 13) {
                        goal.add(card);
                        set.remove(index);
                        if (index >= set.size()) {
                            index--;
                        }
                    } else {
                        ret.append("★★エラー: 移動できません");
                        ret.append("\r\n");
                    }
                } else {
                    ret.append("★:手札を繰る");
                    ret.append("\r\n");
                    index++;
                    if (index >= set.size()) {
                        index = 0;
                    }
                }
            } else {
                ret.append("★★エラー: 手札はありません");
                ret.append("\r\n");
            }
        }
        return ret.toString();
    }

    // スートの文字列に変換
    private static final String suit(int i) {
        if (i < 13)
            return "♠";
        if (i < 13 * 2)
            return "♣";
        if (i < 13 * 3)
            return "♡";
        return "♢";
    }

    // スートの文字列に変換（0～3）
    private static final String suit2(int i) {
        if (i == 0)
            return "♠";
        if (i == 1)
            return "♣";
        if (i == 2)
            return "♡";
        return "♢";
    }

    // カードの番号を表示
    private static final String number(int i) {
        int ret = (i % 13) + 1;
        if (ret == 11)
            return "J";
        if (ret == 12)
            return "Q";
        if (ret == 13)
            return "K";
        if (ret == 1)
            return "A";
        return "" + ret;
    }
}
