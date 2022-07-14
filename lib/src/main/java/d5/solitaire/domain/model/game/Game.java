package d5.solitaire.domain.model.game;

import java.util.Random;

/**
 * ゲームの局面
 */
public class Game {

    private final Hand hand; // トランプ一組、手札

    private final Lanes lanes; // レーン7組

    private final Goals goals; // ゴール4組

    private final Error error; // FIXME 操作のエラーを内部状態で持つのはおかしいかも。いっそ例外投げちゃう？
    
    private final Game previous;

    public Game(Random random) {
        var result = new CardsArranger().arrange(random);
        this.lanes = result._1;
        this.hand = result._2;
        this.goals = new Goals();
        previous = this;
        error = Error.SUCCESS;
    }
    
    private Game(Hand set, Lanes lanes,Goals goals, Error error, Game previous) {
        this.hand = set;
        this.lanes = lanes;
        this.goals = goals;
        this.error = error; 
        this.previous = previous;
    }
    
    public Error error() {
        return error;
    }

    @Override
    public String toString() {
        return lanes.toString() + goals.toString() + hand.toString();
    }

    public Game moveFromLaneToLane(LaneIndex commandNum, LaneIndex command2Num) {
        Lanes newLanes = lanes.moveFromLaneToLane(commandNum, command2Num);
        if(newLanes == null) {
            return new Game(this.hand, this.lanes, this.goals, Error.NOT_MOVE_LANE_TO_LANE, this);
        }
        return new Game(this.hand, newLanes, this.goals, Error.SUCCESS, this);
    }
    
    public Game moveFromLaneToGoal(LaneIndex commandNum) {
        var lastCard = lanes.lastCard(commandNum);
        if (goals.transferable(lastCard)) {
            return new Game(this.hand, lanes.removeLast(commandNum), goals.transfer(lastCard), Error.SUCCESS, this);
        } else {
            return new Game(this.hand, this.lanes, this.goals, Error.NOT_MOVE_LANE_TO_GOAL, this);
        }
    }
    

    public Game moveFromHandToLane(LaneIndex command2Num) {
        var card = hand.card();
        if (card != null) { // FIXME 手札がある状態と無い状態でクラスを作ると if の分岐がなくせる
            var result = this.lanes.moveFromHandToLane(command2Num, card);
            if(result != null) {
                return new Game(hand.remove(), result, this.goals, Error.SUCCESS, this);
            } else {
                return new Game(hand, lanes, goals, Error.NOT_MOVE_HAND_TO_LANE, this);
            }
        } else {
            return new Game(hand, lanes, goals, Error.NO_HAND, this);
        }
    }
    

    public Game moveFromHandToGoal() {
        var card = hand.card();
        if (card != null) {
            if (goals.transferable(card)) {
                return new Game(hand.remove(), lanes, goals.transfer(card), Error.SUCCESS, this);
            } else {
                return new Game(hand, lanes, goals, Error.NOT_MOVE_HAND_TO_GOAL, this);
            }
        } else {
            return new Game(hand, lanes, goals, Error.NO_HAND, this);
        }
    }

    public Game rollHand() {
        return new Game(hand.roll(), lanes, goals, Error.SUCCESS, this);
    }

    public Game undo() {
        return previous;
    }
}
