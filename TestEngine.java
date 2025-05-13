public class TestEngine {
    public static int move(int[][] gameBoard, int player)
    {
        return (int)(Math.random() * 6) + 1; // return random move if every move gives opponent a chance
    }
}
