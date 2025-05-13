import java.util.Scanner;

public class RunConnectFourCvC{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        boolean win = false;
        int count = -1;
        int move = -1;
        int winner = -1;
        System.out.println("How many simulations?");
        int simulations = input.nextInt();
        System.out.println("Print moves?");
        boolean printMoves = input.nextBoolean();

        int engineWins = 0;
        int opponentWins = 0;
        int ties = 0;

        for (int i = 0; i < simulations; i++)
        {
            ConnectFour game = new ConnectFour();
            int human = (int) (Math.random() * 2) + 1;
            while(!win && !game.isFull()){
                count++;
                if(count%2+1==human){
                    move = ConnectFourEngine.move(game.getGameBoard(), count%2+1);
                    if(!game.drop(count%2+1,move)){
                        if (printMoves) 
                        {
                        System.out.println("Engine makes invalid move. Opponent engine wins.");
                        }
                        winner = (1+count)%2+1;
                        win = true;
                        break;
                    }
                }else{
                    move = TestEngine.move(game.getGameBoard(), count%2+1);
                    if(!game.drop(count%2+1,move)){
                        if (printMoves)
                        {
                            System.out.println("Opponent Engine makes invalid move. Engine wins.");
                        }
                        winner = (1+count)%2+1;
                        win = true;
                        break;
                    }
                }
                if (printMoves)
                {
                    System.out.println("Turn: " + (count+1));
                    game.print();
                }
                winner = game.determineWin();
                win = winner != 0;
            }

            if (printMoves)
            {
                if(win){
                    System.out.println("Player " + winner + " is the winner.");
                }else{
                    System.out.println("Tie game :(");
                }
            }

            if (winner == human)
            {
                engineWins++;
            } else if (winner == -1) {
                ties++;
            } else {
                opponentWins++;
            }

            win = false;
            count = -1;
            move = -1;
            winner = -1;
        }

        System.out.println("\nEngine: " + engineWins + " (" + (double) engineWins / simulations * 100 + "%)\nOpponent: " + opponentWins + " (" + (double) opponentWins / simulations * 100 + "%)\nTies: " + ties + " (" + (double) ties / simulations * 100 + "%)\n");
    }
}