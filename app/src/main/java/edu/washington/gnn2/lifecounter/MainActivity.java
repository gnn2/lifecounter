package edu.washington.gnn2.lifecounter;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AbsListView;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View.OnClickListener;
import java.util.*;

import static edu.washington.gnn2.lifecounter.R.layout;
import edu.washington.gnn2.lifecounter.R.layout.*;

public class MainActivity extends ActionBarActivity implements OnClickListener {
    private ArrayList<Integer> playersSums = new ArrayList<Integer>();
    private Integer playerCount = 4;
    private Integer startCount = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        for(Integer i = 1; i <= playerCount; i++){
            initPlayer(i);
        }
        if(savedInstanceState != null){
            for(Integer i = 0; i <= savedInstanceState.size(); i++){
                Integer yes = i+1;
                String key = yes.toString();
                Integer sum = (Integer) savedInstanceState.getInt(key);
//                playersSums.set(i, sum);

                View v = (View) findViewById(R.id.person);

               View current =  v.findViewById(yes);
                System.out.println("Current view id = " + current.getId());
                TextView total = (TextView) current.findViewById(R.id.lifetotal);
                total.setText("Life total = " + sum.toString());
            }

        }
    }

    private void initPlayer(Integer player){
        playersSums.add(startCount);
        LinearLayout out = (LinearLayout) findViewById(R.id.outerLayout);
       //LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = LayoutInflater.from(this).inflate(layout.custom, null);
        view.setId(player);
        EditText name = (EditText) view.findViewById(R.id.player);
        String hintText = name.getHint().toString() + player.toString();
        System.out.println(hintText);
        name.setHint("Name of player " + player.toString());
        TextView sum = (TextView) view.findViewById(R.id.lifetotal);
      //  String total = sum.getText().toString();
        sum.setText("Life total = " + startCount.toString());
        out.addView(view);
    }

    @Override
    protected void onSaveInstanceState(Bundle toSave){
        super.onSaveInstanceState(toSave);
        Integer numOfPlayers = 0;
        for(Integer sum : playersSums) {
            numOfPlayers++;
            toSave.putInt((numOfPlayers.toString()), sum);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v) {
        Button b = (Button)v;
        String text = b.getText().toString();

     //  Integer buttonid = b.getId();
     //   System.out.println("button id = " + buttonid);
        if(text.contains("Add Player +")){
            if(playerCount < 8){
                playerCount++;
                initPlayer(playerCount);
            }
        } else {
            View temp = (View)v.getParent();
            View parent = (View)temp.getParent();
            int layoutId = parent.getId();
            System.out.println("Parent id " + layoutId);
            int personId = layoutId - 1;

            EditText name = (EditText) parent.findViewById(R.id.player);
            String personName = name.getText().toString();

            TextView textV = (TextView)parent.findViewById(R.id.lifetotal);

            int playerSum = playersSums.get(personId);

            if (text.equals("+")){
                  playerSum++;
            } else if (text.equals("-")) {
                playerSum--;
            } else if (text.contains("+5")) {
                playerSum = playerSum + 5;
            } else {
                playerSum = playerSum - 5;
            }

            if (playerSum > 0){

                textV.setText("Life total = " + playerSum);
            } else {
                textV.setText("LOSES!!!");
            }
            playersSums.set(personId,playerSum);

        }
    }
}
