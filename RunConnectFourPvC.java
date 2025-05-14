import java.util.Scanner;

public class RunConnectFourPvC{
    public static void main(String[] args){
        ConnectFour game = new ConnectFour();
        Scanner input = new Scanner(System.in);
        boolean win = false;
        int count = -1;
        int move = -1;
        int winner = -1;
        System.out.println("Would the human like to go first or second?");
        int human = input.nextInt();

        while(!win && !game.isFull()){
            count++;
            if(count%2+1==human){
                System.out.println("What is your move?");
                move = input.nextInt();
                while(!game.drop(count%2+1,move -1)){ // "- 1" makes cols go from 1-7 instead of 0-6
                  System.out.println("Please try again.");
                  move = input.nextInt();
                }
            }else{
                move = TannerCantwellConnectFour.move(game.getGameBoard(), count%2+1);
                if(!game.drop(count%2+1,move)){
                    System.out.println("Computer makes invalid move. Human wins.");
                    winner = (1+count)%2+1;
                    win = true;
                    break;
                }
            }
            System.out.println("Turn: " + (count+1));
            game.print();
            winner = game.determineWin();
            win = winner != 0;
        }
        if(win){
            System.out.println("Player " + winner + " is the winner.");
        }else{
            System.out.println("Tie game :(");
        }
    }
}