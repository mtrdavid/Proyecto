package co.edu.unal.tictactoe;







import java.util.Random;

public class TicTacToeGame {



       public static final int BOARD_SIZE = 9;
       private char mBoard[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};


       public static final char HUMAN_PLAYER = 'X';
       public static final char COMPUTER_PLAYER = 'O';
       public static final char OPEN_SPOT = ' ';
       private Random mRand;

    public enum DifficultyLevel {Easy, Harder, Expert};

    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;
    public TicTacToeGame() {
        mBoard = new char[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = OPEN_SPOT;
            mRand = new Random();
        }
    }


    public int checkForWinner() {
           // Check horizontal wins
           for (int i = 0; i <= 6; i += 3) {
               if (mBoard[i] == HUMAN_PLAYER &&
                       mBoard[i + 1] == HUMAN_PLAYER &&
                       mBoard[i + 2] == HUMAN_PLAYER)
                   return 2;
               if (mBoard[i] == COMPUTER_PLAYER &&
                       mBoard[i + 1] == COMPUTER_PLAYER &&
                       mBoard[i + 2] == COMPUTER_PLAYER)
                   return 3;
           }

           // Check vertical wins
           for (int i = 0; i <= 2; i++) {
               if (mBoard[i] == HUMAN_PLAYER &&
                       mBoard[i + 3] == HUMAN_PLAYER &&
                       mBoard[i + 6] == HUMAN_PLAYER)
                   return 2;
               if (mBoard[i] == COMPUTER_PLAYER &&
                       mBoard[i + 3] == COMPUTER_PLAYER &&
                       mBoard[i + 6] == COMPUTER_PLAYER)
                   return 3;
           }

           // Check for diagonal wins
           if ((mBoard[0] == HUMAN_PLAYER &&
                   mBoard[4] == HUMAN_PLAYER &&
                   mBoard[8] == HUMAN_PLAYER) ||
                   (mBoard[2] == HUMAN_PLAYER &&
                           mBoard[4] == HUMAN_PLAYER &&
                           mBoard[6] == HUMAN_PLAYER))
               return 2;
           if ((mBoard[0] == COMPUTER_PLAYER &&
                   mBoard[4] == COMPUTER_PLAYER &&
                   mBoard[8] == COMPUTER_PLAYER) ||
                   (mBoard[2] == COMPUTER_PLAYER &&
                           mBoard[4] == COMPUTER_PLAYER &&
                           mBoard[6] == COMPUTER_PLAYER))
               return 3;

           // Check for tie
           for (int i = 0; i < BOARD_SIZE; i++) {
               // If we find a number, then no one has won yet
               if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                   return 0;
           }

           // If we make it through the previous loop, all places are taken, so it's a tie
           return 1;

       }

       public int getComputerMove() {


           int move = -1;
           if (mDifficultyLevel == DifficultyLevel.Easy)
               move = getRandomMove();
           else if (mDifficultyLevel == DifficultyLevel.Harder) {
               move = getWinningMove();
               if (move == -1)
                   move = getRandomMove();
           }
           else if (mDifficultyLevel == DifficultyLevel.Expert) {

               move = getWinningMove();
               if (move == -1)
                   move = getBlockingMove();
               if (move == -1)
                   move = getRandomMove();
           }
           return move;
       }




       public void clearBoard() {

           for (int i = 0; i < BOARD_SIZE; i++) {
               mBoard[i] = OPEN_SPOT;
           }

       }

    public void setMove(char player, int location) {
        mBoard[location]=player;

    }
    public int getWinningMove(){


        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {

                    return i;
                } else
                    mBoard[i] = curr;
            }
        }
       return getBlockingMove();

    }

    public int getBlockingMove(){

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];   // Save the current number
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    return i;


                } else
                    mBoard[i] = curr;

            }
        }
        return getRandomMove();
    }

    public int getRandomMove(){
        int move;
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);



        return move;
    }
}

