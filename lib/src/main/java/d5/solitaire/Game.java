package d5.solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import io.vavr.Tuple2;

public class Game {

    List<Integer> set; // トランプ一組、手札

    Lanes lanes; // レーン7組

    List<List<Integer>> goals; // ゴール4組

    int index; // 手札のindex
    
    String lastMessage; // 最後の操作によるメッセージ
    
    Game previous;

    public Game(Random random) {
        // 初期化
        set = new ArrayList<Integer>(); // トランプ一組、手札
        for (int i = 0; i < 13 * 4; i++) {
            set.add(i);
        }
        Collections.shuffle(set, random); // トランプをシャッフル
        lanes = Lanes.fromSet(set); // FIXME set の内容を変更してしまう
        goals = new ArrayList<List<Integer>>();
        for (int i = 0; i < 4; i++) {
            var goal = new ArrayList<Integer>();
            goals.add(goal);
        }
        index = 0; // 手札のindex
    }
    
    private Game(List<Integer> set, Lanes lanes,List<List<Integer>> goals, int index, String lastMessage, Game previous) {
        this.set = set;
        this.lanes = lanes;
        this.goals = goals;
        this.index = index;
        this.lastMessage = lastMessage; 
        this.previous = previous;
    }

    public String display() {
        StringBuilder ret = new StringBuilder();
        ret.append(lanes.toString());
        for (int i = 0; i < 4; i++) {
            var goal = goals.get(i);
            ret.append("ゴール" + suit2(i) + ":" + (goal.size() == 0 ? "-" : goal.size()));
            ret.append("\r\n");
        }
        var card = set.isEmpty() ? null : set.get(index);
        ret.append("手札トップ:" + (card == null ? "無し" : (suit(card) + number(card))));
        return ret.toString();
    }

    public Game moveFromLaneToLane(int commandNum, int command2Num) {
        StringBuilder ret = new StringBuilder();
        String lastMessage = null;

        ret.append("★:レーン" + commandNum + "からレーン" + command2Num + "への移動");
        ret.append("\r\n");
        Lanes newLanes = lanes.moveFromLaneToLane(commandNum, command2Num);
        if(newLanes == lanes) { // FIXME オブジェクトが同じならエラー、というのはわかりづらい
            ret.append("★★エラー: 移動できません");
            ret.append("\r\n");
        }
        lastMessage = ret.toString();
        return new Game(this.set, newLanes, this.goals, this.index, lastMessage, this);
    }
    
    public Game moveFromLaneToGoal(int commandNum) {
        List<List<Tuple2<Integer, Boolean>>> lanes = this.lanes.deepCopy();
        List<List<Integer>> goals = deepCopy(this.goals);
        String lastMessage = null;

        StringBuilder ret = new StringBuilder();
        ret.append("★:レーン" + commandNum + "をゴールへ");
        ret.append("\r\n");
        var lane = lanes.get(commandNum);
        var laneLast = lane.get(lane.size() - 1);
        var goal = goals.get(laneLast._1 / 13);
        if (goal.size() == laneLast._1 % 13) {
            goal.add(laneLast._1);
            lane.remove(lane.size() - 1);
            if (!lane.isEmpty()) {
                lane.set(lane.size() - 1, new Tuple2<>(lane.get(lane.size() - 1)._1, true));
            }
        } else {
            ret.append("★★エラー: 移動できません");
            ret.append("\r\n");
        }
        lastMessage = ret.toString();
        return new Game(this.set, Lanes.fromLanes(lanes), goals, this.index, lastMessage, this);
    }
    

    public Game moveFromHandToLane(int command2Num) {
        List<Integer> set = new ArrayList<>(this.set);
        List<List<Tuple2<Integer, Boolean>>> lanes = this.lanes.deepCopy();
        int index = this.index;
        String lastMessage = null;

        StringBuilder ret = new StringBuilder();
        var card = set.isEmpty() ? null : set.get(index);
        if (card != null) {
            var lane2 = lanes.get(command2Num);
            boolean success = false;
            if (lane2.isEmpty()) { // 空列に移動できるのは K
                if (card % 13 == 12) { // K
                    lane2.add(new Tuple2<>(card, true));
                    set.remove(index);
                    if (index >= set.size()) {
                        index--;
                    }
                    success = true;
                }
            } else { // 空列以外に移動できるのは色違い連番
                var lane2Last = lane2.get(lane2.size() - 1);
                if ((lane2Last._1.intValue() % 13) == (card.intValue() % 13) + 1
                    && (((card.intValue() / 26 + 1)
                        + (lane2Last._1.intValue() / 26 + 1)) == 0b11)) {
                    lane2.add(new Tuple2<>(card, true));
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
        } else {
            ret.append("★★エラー: 手札はありません");
            ret.append("\r\n");
        }
        lastMessage = ret.toString();
        return new Game(set, Lanes.fromLanes(lanes), this.goals, index,lastMessage, this);
    }
    

    public Game moveFromHandToGoal() {
        List<Integer> set = new ArrayList<>(this.set);
        List<List<Integer>> goals = deepCopy(this.goals);
        int index = this.index;
        String lastMessage = null;

        StringBuilder ret = new StringBuilder();
        var card = set.isEmpty() ? null : set.get(index);
        if (card != null) {
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
            ret.append("★★エラー: 手札はありません");
            ret.append("\r\n");
        }
        lastMessage = ret.toString();
        return new Game(set, this.lanes, goals, index, lastMessage, this);
    }

    public Game rollHand() {
        int index = this.index;
        String lastMessage = this.lastMessage;

        StringBuilder ret = new StringBuilder();
        ret.append("★:手札を繰る");
        ret.append("\r\n");
        index++;
        if (index >= set.size()) {
            index = 0;
        }
        lastMessage = ret.toString();
        return new Game(set, lanes, goals, index, lastMessage, this);
    }

    public String lastMessage() {
        return lastMessage;
    }
    
    public Game undo() {
        return previous == null ? this : previous;
    }

    // スートの文字列に変換
    static final String suit(int i) {
        if (i < 13)
            return "♠";
        if (i < 13 * 2)
            return "♣";
        if (i < 13 * 3)
            return "♡";
        return "♢";
    }

    // スートの文字列に変換（0～3）
    static final String suit2(int i) {
        if (i == 0)
            return "♠";
        if (i == 1)
            return "♣";
        if (i == 2)
            return "♡";
        return "♢";
    }

    // カードの番号を表示
    static final String number(int i) {
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
    
    private static final <T> List<List<T>> deepCopy(List<List<T>> list) {
        return new ArrayList<>(list.stream().map(element -> new ArrayList<>(element)).collect(Collectors.toList()));
    }
}
