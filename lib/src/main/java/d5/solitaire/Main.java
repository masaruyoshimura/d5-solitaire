package d5.solitaire;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(new Random());
        try (var scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(game.display()); 
                System.out.print("どうしますか？(操作対象のレーン。手札の場合は7。undo の場合は8）:");
                var command = scanner.next();
                var commandNum = Integer.parseInt(command);
                if (commandNum <= 6) {
                    System.out.print("レーン" + commandNum + "をどうしますか？(操作対象のレーン。ゴールの場合は7）:");
                    var command2 = scanner.next();
                    var command2Num = Integer.parseInt(command2);
                    game = game.step(commandNum, command2Num);
                } else if (commandNum == 8) {
                    game = game.undo();
                } else {
                    System.out.print("手札をどうしますか？(操作対象のレーン。ゴールの場合は7, 繰る場合は8）:");
                    var command2 = scanner.next();
                    var command2Num = Integer.parseInt(command2);
                    game = game.step(commandNum, command2Num);
                }
            }
        }
    }
}
