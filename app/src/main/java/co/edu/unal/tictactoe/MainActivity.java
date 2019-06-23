package co.edu.unal.tictactoe;


        import android.app.Activity;
        import android.graphics.Color;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

public class MainActivity extends Activity  {



    public TicTacToeGame mGame;
    public Button mBoardButtons[];
    public TextView mInfoTextView;



    private class ButtonClickListener implements View.OnClickListener {

        int location;


        public ButtonClickListener(int location) {

            this.location = location;
        }

        @Override
        public void onClick(View view) {


            if (mBoardButtons[location].isEnabled()) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText("Es el turno de android");
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();

                }
                if (winner == 0)
                    mInfoTextView.setText("Es tu turno");
                else if (winner == 1)
                    mInfoTextView.setText("Es un empate!");
                else if (winner == 2) {
                    mInfoTextView.setText("tu ganaste!");

                } else
                    mInfoTextView.setText("Android gano!");


            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = findViewById(R.id.one);
        mBoardButtons[1] = findViewById(R.id.two);
        mBoardButtons[2] = findViewById(R.id.three);
        mBoardButtons[3] = findViewById(R.id.four);
        mBoardButtons[4] = findViewById(R.id.five);
        mBoardButtons[5] = findViewById(R.id.six);
        mBoardButtons[6] = findViewById(R.id.seven);
        mBoardButtons[7] = findViewById(R.id.eight);
        mBoardButtons[8] = findViewById(R.id.nine);
        mInfoTextView = findViewById(R.id.information);

        mGame = new TicTacToeGame();
        startNewGame();


    }

    private void startNewGame() {

        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {

            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));

        }


        mInfoTextView.setText("Tu inicias.");
    }


    private void setMove(char player, int location) {


        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }



}
