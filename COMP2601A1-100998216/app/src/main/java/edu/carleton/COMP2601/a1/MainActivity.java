package edu.carleton.COMP2601.a1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import edu.carleton.COMP2601.R;

public class MainActivity extends AppCompatActivity {
    ImageButton[] tiles;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button)findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup();
                //Start the game
                game = new Game();
            }
        });
    }
    protected void setup(){
        //set up buttons
        ((Button)findViewById(R.id.btnStart)).setText("Running");
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
            //finalize
            final int a = i;
            tiles[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //place for player then update UI
                    if(game.place(a,true)){
                        ((ImageButton)v).setImageResource(R.drawable.button_x);
                    }
                }
            });
        }
    }
    protected void update(){
        AsyncTask a = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }
        };
        a.execute();
    }
}
