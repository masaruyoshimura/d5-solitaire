package d5.solitaire;

import java.util.Random;
import java.util.Scanner;

import d5.solitaire.domain.model.game.Game;
import d5.solitaire.domain.model.game.LaneIndex;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(new Random());
        try (var scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(game.toString()); 
                System.out.print("どうしますか？(操作対象のレーン。手札の場合は7。undo の場合は8）:");
                var command = scanner.next();
                var commandNum = Integer.parseInt(command);
                if (commandNum <= 6) {
                    System.out.print("レーン" + commandNum + "をどうしますか？(操作対象のレーン。ゴールの場合は7）:");
                    var command2 = scanner.next();
                    var command2Num = Integer.parseInt(command2);
                    if (command2Num <= 6) {
                        System.out.println("★:レーン" + commandNum + "からレーン" + command2Num + "への移動");
                        game = game.moveFromLaneToLane(LaneIndex.of(commandNum), LaneIndex.of(command2Num));
                        System.out.println(game.error());
                    } else {
                        System.out.println("★:レーン" + commandNum + "をゴールへ");
                        game = game.moveFromLaneToGoal(LaneIndex.of(commandNum));
                        System.out.println(game.error());
                    }
                } else if (commandNum == 8) {
                    game = game.undo();
                } else {
                    System.out.print("手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:");
                    var command2 = scanner.next();
                    var command2Num = Integer.parseInt(command2);
                    if (command2Num <= 6) {
                        System.out.println("★:手札をレーン" + command2Num + "へ");
                        game = game.moveFromHandToLane(LaneIndex.of(command2Num));
                        System.out.println(game.error());
                    } else if (command2Num == 7){
                        System.out.println("★:手札をゴールへ");
                        game = game.moveFromHandToGoal();
                        System.out.println(game.error());
                    } else {
                        System.out.println("★:手札を繰る");
                        game = game.rollHand();
                    }
                }
            }
        }
    }
}
