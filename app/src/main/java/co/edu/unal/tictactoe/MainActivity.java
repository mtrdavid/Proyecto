package co.edu.unal.tictactoe;


        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.graphics.Color;

        import android.graphics.drawable.AdaptiveIconDrawable;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends Activity  {



    public TicTacToeGame mGame;
    public Button mBoardButtons[];
    public TextView mInfoTextView;

    private TextView mHumanCount;
    private TextView mTieCount;
    private TextView mAndroidCount;

    private int mHumanCounter=0;
    private int mTieCounter=0;
    private int mAndroidCounter=0;
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    private boolean mFirstHuman=true;
    private boolean mGameOver=false;
    private MenuInflater MenuInflaterinflater;


    private class ButtonClickListener implements View.OnClickListener {

        int location;


        public ButtonClickListener(int location) {

            this.location = location;
        }

        @Override
        public void onClick(View view) {

         if(!mGameOver ) {
             if (mBoardButtons[location].isEnabled()) {
                 setMove(TicTacToeGame.HUMAN_PLAYER, location);

                 int winner = mGame.checkForWinner();
                 if (winner == 0) {
                     mInfoTextView.setText(R.string.turn_computer);

                     int move = mGame.getComputerMove();
                     setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                     winner = mGame.checkForWinner();

                 }
                 if (winner == 0)
                     mInfoTextView.setText(R.string.turn_human);

                 else if (winner == 1) {
                     mInfoTextView.setText(R.string.result_tie);
                     mTieCounter++;
                     mGameOver = true;
                     mTieCount.setText(Integer.toString(mTieCounter));
                 }
                 else if (winner == 2) {
                     mGameOver=true;
                     mHumanCounter++;
                     mHumanCount.setText(Integer.toString(mHumanCounter));
                     mInfoTextView.setText(R.string.result_human_wins);
                     for (int i = 0; i < mGame.BOARD_SIZE; i++) {
                         mBoardButtons[i].setSaveEnabled(false);


                     }


                 } else {
                     mGameOver = true;
                     mAndroidCounter++;

                     mInfoTextView.setText(R.string.result_computer_wins);
                     mAndroidCount.setText(Integer.toString(mAndroidCounter));
                     for (int i = 0; i < mGame.BOARD_SIZE; i++) {
                         mBoardButtons[i].setSaveEnabled(false);

                     }

                 }

             }
         }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
            startNewGame();
            return true;
            case R.id.ai_difficulty:
            showDialog(DIALOG_DIFFICULTY_ID);
            return true;
            case R.id.quit:
            showDialog(DIALOG_QUIT_ID);
            return true;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflaterinflater = getMenuInflater();

        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
            case DIALOG_DIFFICULTY_ID:builder.setTitle(R.string.difficulty_choose);
                   final CharSequence[] levels = {
                    getResources().getString(R.string.difficulty_easy),
                    getResources().getString(R.string.difficulty_harder),
                    getResources().getString(R.string.difficulty_expert)};

            builder.setSingleChoiceItems(levels,selected, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(), levels[item],Toast.LENGTH_SHORT).show();
                }
            });
            dialog = builder.create();
            break;
        }
        return dialog;
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

        mInfoTextView = (TextView) findViewById(R.id.information);



            mHumanCount = (TextView) findViewById(R.id.humanCount);
            mTieCount = (TextView) findViewById(R.id.tieCount);
            mAndroidCount = (TextView) findViewById(R.id.androidCount);



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
        if(mFirstHuman){

            mInfoTextView.setText(R.string.first_human);
            mFirstHuman=false;
        }else{
           mInfoTextView.setText(R.string.turn_computer);
            int move = mGame.getComputerMove();
            setMove(mGame.COMPUTER_PLAYER, move);
            mFirstHuman = true;
        }




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
