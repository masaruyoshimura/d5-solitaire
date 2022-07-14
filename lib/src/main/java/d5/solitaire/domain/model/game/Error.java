package d5.solitaire.domain.model.game;

public class Error {

    // TODO enum が無かった頃のJavaの列挙型の実装方法。特に意味は無く、enumで良い。
    public static final Error SUCCESS = new Error("");
    public static final Error NOT_MOVE_LANE_TO_LANE = new Error("★★エラー: レーンからレーンへ移動できません");
    public static final Error NOT_MOVE_LANE_TO_GOAL = new Error("★★エラー: レーンからゴールへ移動できません");
    public static final Error NOT_MOVE_HAND_TO_LANE = new Error("★★エラー: 手札からレーンへ移動できません");
    public static final Error NOT_MOVE_HAND_TO_GOAL = new Error("★★エラー: 手札からゴールへ移動できません");
    public static final Error NO_HAND = new Error("★★エラー: 手札はありません");

    private final String message;
    
    private Error(String message) {
        this.message = message;
    }
    
    public String message() {
        return message;
    }
}
