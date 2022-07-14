package d5.solitaire.domain.model.game;

import java.util.HashMap;
import java.util.Map;

/**
 * レーンズ。レーンの集合。
 */
public class Lanes {
    
    final Map<LaneIndex, Lane> lanes; // レーン7組 FIXME private
    
    public Lanes(Map<LaneIndex,Lane> lanes) {
        if(lanes == null) throw new IllegalArgumentException("lanes must not be null.");
        this.lanes = new HashMap<>(lanes);
    }
    
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (LaneIndex index : LaneIndex.values()) {
            var lane = lanes.get(index);
            ret.append("レーン" + index.index() + ":");
            ret.append(lane.toString());
            ret.append("\r\n");
        }
        return ret.toString();
    }
    
    public Lanes moveFromLaneToLane(LaneIndex commandNum, LaneIndex command2Num) {
        var lane = lanes.get(commandNum);
        var lane2 = lanes.get(command2Num);
        var ret = lane2.adapt(lane);
        if(ret != null) {
            var lanes = new HashMap<>(this.lanes);
            lanes.put(commandNum, ret._1);
            lanes.put(command2Num, ret._2);
            return new Lanes(lanes);
        }
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Lanes)) return false;
        Lanes other = (Lanes)obj;
        return other.lanes.equals(lanes);
    }
    
    public Lanes moveFromHandToLane(LaneIndex command2Num, Card card) {
        var lane2 = lanes.get(command2Num);
        var result = lane2.moveFromHandToLane(card);
        if(result != null) {
            return copyAndSet(command2Num, result);
        }
        return null;
    }
    
    public Lanes copyAndSet(LaneIndex index, Lane lane) {
        var lanes = new HashMap<>(this.lanes);
        lanes.put(index, lane);
        return new Lanes(lanes);
    }
    
    public Card lastCard(LaneIndex commandNum) {
        return lanes.get(commandNum).last();
    }
    
    public Lanes removeLast(LaneIndex commandNum) {
        var lane = lanes.get(commandNum);
        var newLane = lane.removeLast();
        return copyAndSet(commandNum, newLane);
    }
}
