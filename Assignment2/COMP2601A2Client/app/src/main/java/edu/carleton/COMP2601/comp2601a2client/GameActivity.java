package edu.carleton.COMP2601.comp2601a2client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import edu.carleton.COMP2601.communication.Event;
import edu.carleton.COMP2601.communication.EventHandler;
import edu.carleton.COMP2601.communication.Fields;
import edu.carleton.COMP2601.communication.Reactor;
import edu.carleton.COMP2601.comp2601a2client.R;

public class GameActivity extends AppCompatActivity {
    ImageButton[] tiles;
    NetworkingService ns;
    Reactor reactor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ns = MainActivity.getInstance().ns;
        reactor = MainActivity.getInstance().reactor;
        setContentView(R.layout.activity_game);
        setup();
        Button start = (Button)findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Button)v).getText().toString().equals(getString(R.string.Start))) {
                    //Start the game
                    try {
                        ns.startGame();
                        ((Button) v).setText(getString(R.string.Stop));
                        changeGameState(true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
//                    changeGameState(true);
                }else{
                    try {
                        ((Button) v).setText(getString(R.string.Start));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //send end to server
//                    ((TextView)findViewById(R.id.txtResult)).setText(getString(R.string.GameEnded));
//                    changeGameState(false);
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
                    try {
                        ns.place(a);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        reactor.register("GAME_ON", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                changeGameState(true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((Button)findViewById(R.id.btnStart)).setText(R.string.Stop);
                    }
                });
            }
        });
        reactor.register("MOVE_MESSAGE", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), MainActivity.getInstance().userid + " has updated",Toast.LENGTH_LONG).show();
                    }
                });
                updateTile((int)event.get("location"),(char)event.get("symbol"),(String)event.get(Fields.ID));
            }
        });
        reactor.register("GAME_OVER", new EventHandler() {
            @Override
            public void handleEvent(Event event) {

            }
        });
    }



    //either starts the game again or ends the game
    private void changeGameState(final boolean clickable){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < tiles.length; i++) {
                    tiles[i].setClickable(clickable);
                    tiles[i].setEnabled(clickable);
                }
            }
        });
    }
    //update textfield and button if game is over
    synchronized protected void update(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                TextView result = (TextView)findViewById(R.id.txtResult);
//                if(!game.getResult().equals("")){
//                    ((Button)findViewById(R.id.btnStart)).setText(getString(R.string.Start));
//                    result.setText("Game is Over " + game.getResult() + " won!");
//                    changeGameState(false);
//                }
            }
        });
    }
    public void updateTile(final int i, final char c, final String player){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tiles[i].setClickable(false);
                tiles[i].setEnabled(false);
                if(c=='x'){
                    tiles[i].setImageResource(R.drawable.button_x);
                }else{
                    tiles[i].setImageResource(R.drawable.button_o);
                }
                ((TextView)findViewById(R.id.txtResult)).setText("Button " + i + " clicked by " + player);
            }
        });
    }
}

