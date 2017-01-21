package edu.carleton.COMP2601.a1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.carleton.COMP2601.R;

public class MainActivity extends AppCompatActivity {
    ImageButton[] tiles;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fill in buttons to array and setup listeners
        setup();
        Button start = (Button)findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Button)v).getText().toString().equals("Start")) {
                    //Start the game
                    game = new Game();
                    ((Button) v).setText("Running");
                    changeGameState(true);
                }else{
                    ((Button) v).setText("Start");
                    ((TextView)findViewById(R.id.txtResult)).setText("Game ended.");
                    changeGameState(false);
                }
            }
        });
    }
    //setup the board
    private void setup(){
        //set up buttons
        tiles = new ImageButton[9];
        tiles[0] = (ImageButton)findViewById(R.id.ibn1);
        tiles[1] = (ImageButton)findViewById(R.id.ibn2);
        tiles[2] = (ImageButton)findViewById(R.id.ibn3);
        tiles[3] = (ImageButton)findViewById(R.id.ibn4);
        tiles[4] = (ImageButton)findViewById(R.id.ibn5);
        tiles[5] = (ImageButton)findViewById(R.id.ibn6);
        tiles[6] = (ImageButton)findViewById(R.id.ibn7);
        tiles[7] = (ImageButton)findViewById(R.id.ibn8);
        tiles[8] = (ImageButton)findViewById(R.id.ibn9);
        for(int i=0;i<tiles.length;i++){
            tiles[i].setClickable(true);
            tiles[i].setEnabled(false);
            //make i final so it can be used within runnable
            final int a = i;
            tiles[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //place for player then update UI
                    if(game.place(a,true)){
                        ((ImageButton)v).setImageResource(R.drawable.button_x);
                        update();
                        v.setClickable(false);
                        v.setEnabled(false);
                    }
                }
            });
        }
    }
    //either starts the game again or ends the game
    private void changeGameState(boolean clickable){
        for (int i = 0; i < tiles.length; i++) {
            tiles[i].setClickable(clickable);
            tiles[i].setEnabled(clickable);
            //if starting the game again reset images
            if(clickable){
                tiles[i].setImageResource(R.drawable.button_empty);
            }
        }
        //refresh result textview
        if(clickable){
            ((TextView)findViewById(R.id.txtResult)).setText("");
        }
    }
    //update textfield and button if game is over
    protected void update(){
        TextView result = (TextView)findViewById(R.id.txtResult);
        if(!game.getResult().equals("")){
            ((Button)findViewById(R.id.btnStart)).setText("Start");
            result.setText("Game is Over " + game.getResult() + " won!");
            changeGameState(false);
        }else{
            result.setText(game.getLastMove());
        }
    }
}
