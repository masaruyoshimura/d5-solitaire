package d5.solitaire.domain.model.game;

import java.util.Arrays;

/**
 * レーン番号
 */
public enum LaneIndex {
    
    LANE_0(0),
    LANE_1(1),
    LANE_2(2),
    LANE_3(3),
    LANE_4(4),
    LANE_5(5),
    LANE_6(6);
    
    int index;
    
    private LaneIndex(int index) {
        this.index = index;
    }
    
    public int index() {
        return index;
    }

    public static LaneIndex of(int i) {
        return Arrays.stream(values()).filter(index -> index.index == i).findFirst().orElseGet(null);
    }
}
