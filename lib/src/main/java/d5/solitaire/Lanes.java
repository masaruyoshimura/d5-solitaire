package d5.solitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.vavr.Tuple2;

public class Lanes {
    List<List<Tuple2<Integer, Boolean>>> lanes; // レーン7組
    
    public static Lanes fromSet(List<Integer> set) {
        List<List<Tuple2<Integer, Boolean>>> lanes = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            var lane = new ArrayList<Tuple2<Integer, Boolean>>();
            lanes.add(lane);
            for (int j = 0; j < i + 1; j++) {
                lane.add(new Tuple2<>(set.remove(0), i == j)); // カードと、裏表のboolean（表ならtrue）
            }
        }
        return new Lanes(lanes);
    }

    public static Lanes fromLanes(List<List<Tuple2<Integer, Boolean>>> lanes) {
        return new Lanes(deepCopy(lanes));
    }

    private Lanes(List<List<Tuple2<Integer, Boolean>>> lanes) {
        this.lanes = lanes;
    }
    
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            var lane = lanes.get(i);
            ret.append("レーン" + i + ":");
            for (int j = 0; j < lane.size(); j++) {
                var card = lane.get(j);
                if (card._2) {
                    ret.append(Game.suit(card._1) + Game.number(card._1));
                } else {
                    ret.append("★");
                }
            }
            ret.append("\r\n");
        }
        return ret.toString();
    }
    
    public List<List<Tuple2<Integer, Boolean>>> deepCopy(){
        return deepCopy(lanes);
    }
    
    private static final <T> List<List<T>> deepCopy(List<List<T>> list) {
        return new ArrayList<>(list.stream().map(element -> new ArrayList<>(element)).collect(Collectors.toList()));
    }
    
    public Lanes moveFromLaneToLane(int commandNum, int command2Num) {
        List<List<Tuple2<Integer, Boolean>>> lanes = deepCopy(this.lanes);
        var lane = lanes.get(commandNum);
        var move = lane.stream().filter(t -> t._2).findFirst().get();
        var lane2 = lanes.get(command2Num);
        if (lane2.isEmpty()) { // 空列に移動できるのは K
            if (move._1 % 13 == 12) { // K
                for (int i = 0; i < lane.size(); i++) {
                    if (lane.get(i) == move) {
                        lane2.addAll(lane.subList(i, lane.size()));
                        lane.removeAll(lane.subList(i, lane.size()));
                        if (!lane.isEmpty()) {
                            lane.set(lane.size() - 1, new Tuple2<>(lane.get(lane.size() - 1)._1, true));
                        }
                        return new Lanes(lanes);
                    }
                }
            }
        } else { // 空列以外に移動できるのは色違い連番
            // TODO laneの最前(move)からの移動だけでなく、途中からの移動にも対応する
            var lane2Last = lane2.get(lane2.size() - 1);
            if ((lane2Last._1.intValue() % 13) == (move._1.intValue() % 13) + 1
                && (((move._1.intValue() / 26 + 1) + (lane2Last._1.intValue() / 26 + 1)) == 0b11)) {
                for (int i = 0; i < lane.size(); i++) {
                    if (lane.get(i) == move) {
                        lane2.addAll(lane.subList(i, lane.size()));
                        lane.removeAll(lane.subList(i, lane.size()));
                        if (!lane.isEmpty()) {
                            lane.set(lane.size() - 1, new Tuple2<>(lane.get(lane.size() - 1)._1, true));
                        }
                        return new Lanes(lanes);
                    }
                }
            }
        }
        return this;
    }
}
