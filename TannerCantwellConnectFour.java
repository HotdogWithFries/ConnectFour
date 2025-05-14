import java.util.ArrayList;

public class TannerCantwellConnectFour {

    public static int move(int[][] gameBoard, int turn)
    {
        int engineNumber = turn;
        int opponentNumber = turn % 2 + 1;

        int engineWin = checkPossibleWin(gameBoard, engineNumber);
        int opponentWin = checkPossibleWin(gameBoard, opponentNumber);

        if (engineWin != -1) // check if engine can win on this move 
        {
            //System.out.println(1 + " - " + engineWin);
            return engineWin;
        }

        if (opponentWin != -1) // check if opponent can win on their next move (block if so)
        {
            //System.out.println(2 + " - " + opponentWin);
            return opponentWin;
        }

        //System.out.println(3);

        ArrayList<Integer> possibleMoves = new ArrayList<Integer>(); // list to hold moves that don't give opponent a chance

        for (int i = 0; i < 7; i++) // check what moves don't give opponent a win chance and are in bounds
        {
            if (!moveGivesWinChance(gameBoard, engineNumber, opponentNumber, i) && gameBoard[0][i] == 0)
            {
                possibleMoves.add(i);
            }
        }

        int engineDouble = checkPossibleDouble(gameBoard, engineNumber);
        int opponentDouble = checkPossibleDouble(gameBoard, opponentNumber);

        if (engineDouble != -1 && possibleMoves.contains(engineDouble)) // check if the engine can make a double (same directions) and if so, make the move as long as it doesn't give opponent a chance to win
        {
            return engineDouble;
        }

        for (int i = 0; i < possibleMoves.size(); i++) // check if any possible move gives engine a double win opportunity (different directions)
        {
            if (moveCausesDoubleWin(gameBoard, engineNumber, i))
            {
                return possibleMoves.get(i);
            }
        }

        for (int i = 0; i < possibleMoves.size(); i++) // remove moves that will give opponent double chance
        {
            if (moveGivesDoubleChance(gameBoard, engineNumber, opponentNumber, possibleMoves.get(i)))
            {
                possibleMoves.remove(i);
                i--;
            }
        }

        if (opponentDouble != -1 && possibleMoves.contains(opponentDouble)) // check if opponent can make a double (same directions) on next move (block if so)
        {
            return opponentDouble;
        }

        for (int i = 0; i < 7; i++) // check if opponent can make a double win opportunity (different directions) (block if so)
        {
            if (moveCausesDoubleWin(gameBoard, opponentNumber, i))
            {
                if (possibleMoves.contains(i))
                {
                    return i;
                }
            }
        }

        for (int i = 0; i < possibleMoves.size(); i++) // check for forced block leading to opponent win
        {
            if (moveGivesWinChance(gameBoard, opponentNumber, opponentNumber, i))
            {
                int[][] gameBoardCopy = copyGameBoard(gameBoard);

                if (dropPiece(gameBoardCopy, opponentNumber, possibleMoves.get(i)))
                {
                    move(gameBoardCopy, engineNumber);

                    if (checkPossibleWin(gameBoardCopy, opponentNumber) >= 0)
                    {
                        return possibleMoves.get(i);
                    }
                }
            }
        }

        for (int i = 0; i < possibleMoves.size(); i++) // check if any possible move gives engine a chance
        {
            if (moveGivesWinChance(gameBoard, engineNumber, engineNumber, possibleMoves.get(i)) || moveGivesDoubleChance(gameBoard, engineNumber, engineNumber, possibleMoves.get(i)))
            {
                return possibleMoves.get(i);
            }
        }

        if (possibleMoves.size() > 0)
        {
            if (possibleMoves.contains(3)) // drop in center if available (prioritize center)
            {
                return 3;
            }

            return possibleMoves.get((int)(Math.random() * possibleMoves.size())); // return random move from possibleMoves
        }

        return -1; // give up and return invalid move if every move gives opponent a chance
    }

    private static int checkPossibleWin(int[][] gameBoard, int player)
    {
        int horizontal = CPWHorizontal(gameBoard, player);
        int vertical = CPWVertical(gameBoard, player);
        int positive = CPWPositive(gameBoard, player);
        int negative = CPWNegative(gameBoard, player);
        
        if (horizontal != -1)
        {
            return horizontal;
        }

        if (vertical != -1)
        {
            return vertical;
        }

        if (positive != -1)
        {
            return positive;
        }

        if (negative != -1)
        {
            return negative;
        }

        return -1;
    }

    private static int checkPossibleDouble(int[][] gameBoard, int player)
    {
        int horizontalDouble = CPDHorizontal(gameBoard, player);
        int positiveDouble = CPDPositive(gameBoard, player);
        int negativeDouble = CPDNegative(gameBoard, player);

        if (horizontalDouble != -1)
        {
            return horizontalDouble;
        }

        if (positiveDouble != -1)
        {
            return positiveDouble;
        }

        if (negativeDouble != -1)
        {
            return negativeDouble;
        }

        return -1;
    }

    private static int[][] copyGameBoard(int[][] gameBoard)
    {
        int[][] result = new int[6][7];

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                result[i][j] = gameBoard[i][j];
            }
        }

        return result;
    }

    private static boolean dropPiece(int[][] gameBoard, int player, int column)
    {
        if (column < 0 || column > 6)
        {
            return false;
        }

        if (gameBoard[0][column] != 0)
        {
            return false;
        }

        for (int i = 5; i >= 0; i--)
        {
            if (gameBoard[i][column] == 0)
            {
                gameBoard[i][column] = player;
                return true;
            }
        }

        return false;
    }

    private static boolean moveGivesWinChance(int[][] gameBoard, int dropper, int player, int col)
    {
        int[][] gameBoardCopy = copyGameBoard(gameBoard);
            
        if (dropPiece(gameBoardCopy, dropper, col))
        {
            if (checkPossibleWin(gameBoardCopy, player) == -1)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    private static boolean moveGivesDoubleChance(int[][] gameBoard, int engine, int player, int col)
    {
        int[][] gameBoardCopy = copyGameBoard(gameBoard);
            
        if (dropPiece(gameBoardCopy, engine, col))
        {
            if (checkPossibleDouble(gameBoardCopy, player) == -1)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    private static boolean moveCausesDoubleWin(int[][] gameBoard, int player, int col)
    {
        int initialWins = numberWins(gameBoard, player);

        int[][] gameBoardCopy = copyGameBoard(gameBoard);

        if (dropPiece(gameBoardCopy, player, col))
        {
            if (numberWins(gameBoardCopy, player) - initialWins >= 2)
            {
                return true;
            }
        }

        return false;
    }

    private static int numberWins(int[][] gameBoard, int player)
    {
        int horizontal = CPWHorizontal(gameBoard, player);
        int vertical = CPWVertical(gameBoard, player);
        int positive = CPWPositive(gameBoard, player);
        int negative = CPWNegative(gameBoard, player);

        int winSpots = 0;
        
        if (horizontal != -1)
        {
            winSpots++;
        }

        if (vertical != -1)
        {
            winSpots++;
        }

        if (positive != -1)
        {
            winSpots++;
        }

        if (negative != -1)
        {
            winSpots++;
        }

        return winSpots;
    }

    // check possible win functions
    
    private static int CPWHorizontal(int[][] gameBoard, int player)
    {
        int count = 0;
        int emptySpace = -1;

        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 4; j++)
            {
                for (int k = j; k < j + 4; k++)
                {
                    if (gameBoard[i][k] == player)
                    {
                        count++;
                    }

                    if (gameBoard[i][k] == 0)
                    {
                        emptySpace = k;
                    }
                }

                if (count == 3 && emptySpace >= 0)
                {
                    if (checkWinSpot(gameBoard, i, emptySpace))
                    {
                        return emptySpace;
                    }
                }

                count = 0;
                emptySpace = -1;
            }
        }

        return -1;
    }

    private static int CPWVertical(int[][] gameBoard, int player)
    {
        int count = 0;
        for (int i = 0; i < 7; i++)
        {
            for (int j = 5; j >= 0; j--)
            {
                if (gameBoard[j][i] == player)
                {
                    count++;
                } else {
                    count = 0;
                }

                if (count == 3)
                {
                    if (checkWinSpot(gameBoard, j - 1, i))
                    {
                        return i;
                    }
                }
            }

            count = 0;
        }

        return -1;
    }

    private static int CPWPositive(int[][] gameBoard, int player)
    {
        int count = 0;
        int emptySpaceRow = -1;
        int emptySpaceCol = -1;

        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 7; j++)
            {
                    if (i - 3 >= 0 && j + 3 < 7)
                    {
                        for (int k = 0; k < 4; k++)
                        {
                            if (gameBoard[i - k][j + k] == player)
                            {
                                count++;
                            }

                            if (gameBoard[i - k][j + k] == 0)
                            {
                                emptySpaceRow = i - k;
                                emptySpaceCol = j + k;
                            }
                        }

                        if (count == 3 && emptySpaceRow >= 0)
                        {
                            if (checkWinSpot(gameBoard, emptySpaceRow, emptySpaceCol))
                            {
                                return emptySpaceCol;
                            }
                        }

                        count = 0;
                        emptySpaceRow = -1;
                        emptySpaceCol = -1;
                    }
            }
        }

        return -1;
    }

    private static int CPWNegative(int[][] gameBoard, int player)
    {
        int count = 0;
        int emptySpaceRow = -1;
        int emptySpaceCol = -1;

        for (int i = 5; i >= 0; i--)
        {
            for (int j = 6; j >= 0; j--)
            {
                    if (i - 3 >= 0 && j - 3 >= 0)
                    {
                        for (int k = 0; k < 4; k++)
                        {
                            if (gameBoard[i - k][j - k] == player)
                            {
                                count++;
                            }

                            if (gameBoard[i - k][j - k] == 0)
                            {
                            emptySpaceRow = i - k;
                            emptySpaceCol = j - k;
                            }
                        }

                        if (count == 3 && emptySpaceRow >= 0)
                        {
                            if (checkWinSpot(gameBoard, emptySpaceRow, emptySpaceCol))
                            {
                                return emptySpaceCol;
                            }
                        }

                        count = 0;
                        emptySpaceRow = -1;
                        emptySpaceCol = -1;
                    }
            }
        }

        return -1;
    }

    // check possible double functions

    private static int CPDHorizontal(int[][] gameBoard, int player)
    {
        int count = 0;
        int emptySpace1 = -1;
        int emptySpace2 = -1;
        int emptySpace3 = -1;

        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 3; j++)
            {
                for (int k = j; k < j + 5; k++)
                {
                    if (gameBoard[i][k] == player)
                    {
                        count++;
                    }

                    if (gameBoard[i][k] == 0)
                    {
                        if (emptySpace1 == -1)
                        {
                            emptySpace1 = k;
                        } else if (emptySpace2 == -1) {
                            emptySpace2 = k;
                        } else {
                            emptySpace3 = k;
                        }
                    }
                }

                if (count == 2 && Math.abs(emptySpace1 - emptySpace3) == 4)
                {
                    if (checkWinSpot(gameBoard, i, emptySpace1) && checkWinSpot(gameBoard, i, emptySpace2) && checkWinSpot(gameBoard, i, emptySpace3))
                    {
                        return emptySpace2;
                    }
                }

                count = 0;
                emptySpace1 = -1;
                emptySpace2 = -1;
                emptySpace3 = -1;
            }
        }

        return -1;
    }

    private static int CPDPositive(int[][] gameBoard, int player)
    {
        int count = 0;
        int emptySpace1Row = -1;
        int emptySpace1Col = -1;
        int emptySpace2Row = -1;
        int emptySpace2Col = -1;
        int emptySpace3Row = -1;
        int emptySpace3Col = -1;

        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 7; j++)
            {
                    if (i - 4 >= 0 && j + 4 < 7)
                    {
                        for (int k = 0; k < 5; k++)
                        {
                            if (gameBoard[i - k][j + k] == player)
                            {
                                count++;
                            }

                            if (gameBoard[i - k][j + k] == 0)
                            {
                                if (emptySpace1Row == -1)
                                {
                                    emptySpace1Row = i - k;
                                    emptySpace1Col = j + k;
                                } else if (emptySpace2Row == -1) {
                                    emptySpace2Row = i - k;
                                    emptySpace2Col = j + k;
                                } else {
                                    emptySpace3Row = i - k;
                                    emptySpace3Col = j + k;
                                }
                            }
                        }

                        if (count == 2 && Math.abs(emptySpace1Row - emptySpace3Row) == 4)
                        {
                            if (checkWinSpot(gameBoard, emptySpace1Row, emptySpace1Col) && checkWinSpot(gameBoard, emptySpace2Row, emptySpace2Col) && checkWinSpot(gameBoard, emptySpace3Row, emptySpace3Col))
                            {
                                return emptySpace2Col;
                            }
                        }

                        count = 0;
                        emptySpace1Row = -1;
                        emptySpace1Col = -1;
                        emptySpace2Row = -1;
                        emptySpace2Col = -1;
                        emptySpace3Row = -1;
                        emptySpace3Col = -1;
                    }
            }
        }

        return -1;
    }

    private static int CPDNegative(int[][] gameBoard, int player)
    {
        int count = 0;
        int emptySpace1Row = -1;
        int emptySpace1Col = -1;
        int emptySpace2Row = -1;
        int emptySpace2Col = -1;
        int emptySpace3Row = -1;
        int emptySpace3Col = -1;

        for (int i = 5; i >= 0; i--)
        {
            for (int j = 6; j >= 0; j--)
            {
                    if (i - 4 >= 0 && j - 4 >= 0)
                    {
                        for (int k = 0; k < 5; k++)
                        {
                            if (gameBoard[i - k][j - k] == player)
                            {
                                count++;
                            }

                            if (gameBoard[i - k][j - k] == 0)
                            {
                                if (emptySpace1Row == -1)
                                {
                                    emptySpace1Row = i - k;
                                    emptySpace1Col = j - k;
                                } else if (emptySpace2Row == -1) {
                                    emptySpace2Row = i - k;
                                    emptySpace2Col = j - k;
                                } else {
                                    emptySpace3Row = i - k;
                                    emptySpace3Col = j - k;
                                }
                            }
                        }

                        if (count == 2 && Math.abs(emptySpace1Row - emptySpace3Row) == 4)
                        {
                            if (checkWinSpot(gameBoard, emptySpace1Row, emptySpace1Col) && checkWinSpot(gameBoard, emptySpace2Row, emptySpace2Col) && checkWinSpot(gameBoard, emptySpace3Row, emptySpace3Col))
                            {
                                return emptySpace2Col;
                            }
                        }

                        count = 0;
                        emptySpace1Row = -1;
                        emptySpace1Col = -1;
                        emptySpace2Row = -1;
                        emptySpace2Col = -1;
                        emptySpace3Row = -1;
                        emptySpace3Col = -1;
                    }
            }
        }

        return -1;
    }

    // check win spot

    private static boolean checkWinSpot(int[][] gameBoard, int row, int col)
    {
        if (row >= 0 && row < 6 && col >= 0 && col < 7)
        {
            if (gameBoard[row][col] == 0)
            {
                if (row == 5)
                {
                    return true;
                } else {
                    if (gameBoard[row + 1][col] != 0)
                    {
                    return true;
                    }
                }
            }
        }

        return false;
    }
}