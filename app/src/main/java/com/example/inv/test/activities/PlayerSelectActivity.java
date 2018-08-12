package com.example.inv.test.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inv.test.R;
import com.example.inv.test.utils.Adapters.PlayerAdapter;
import com.example.inv.test.utils.Elements.Player;
import com.example.inv.test.utils.Network.Rotater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

//Запускается из Лобби, чтобы посмотреть список игроков
public class PlayerSelectActivity extends Activity implements AdapterView.OnItemClickListener {
    //LinearLayout linLayout;
    LayoutInflater ltInflater;
    String [] playersnames = new String[8];// TODO fill
    boolean [] playersstates = new boolean[8]; //TODO fill

    //String[] names;
    //String[] ids;
    //String[] tips;

    ListView lvMain;

    ArrayList<Player> players = new ArrayList<Player>();
    PlayerAdapter playerAdapter;

    Button btnDelete;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerselect_activity);
        playerAdapter = new PlayerAdapter(this, players);

        // настраиваем список
        lvMain = (ListView) findViewById(R.id.lvMain);

        lvMain.setAdapter(playerAdapter);

        lvMain.setOnItemClickListener(this);
        fillData();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
        Player clickedplayer = (Player)playerAdapter.getItem(position);
        ContentValues cv = new ContentValues();

        Intent intent = new Intent();
        intent.putExtra("name", clickedplayer.name);
        intent.putExtra("ready", clickedplayer.ready);
        setResult(RESULT_OK, intent);
        finish();
    }




    private void fillData() { //здесь мы делаем запрос на сервер: 'method':'getplayersinfo' Возвращает словарь "имя":"готовнсоть"
        JSONObject msg = new JSONObject();
        try {
            msg.put("method", "getplayersinfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("PSACT", "Begin1");
        Rotater.addHandler("getplayersinfo", new Rotater.methodHandler(this) {
            @Override
            public void handle(JSONObject json) {
                super.handle(json);

                try {
                    switch(json.getString("status")){
                        case("Ok"):
                            JSONArray injsons = json.getJSONArray("array");
                            JSONObject dictjson = json.getJSONObject("dict");
                            for (int i = 0; i < injsons.length(); i++) {
                                playersnames[i] = injsons.getString(i);
                                playersstates[i] = dictjson.getBoolean(playersnames[i]);
                            }
                            int i = 0;
                            for (i = 0; i < playersnames.length; i++) {
                                players.add(new Player(playersnames[i], playersstates[i], false));
                            }
                            players.add(new Player("All", true, false));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    playerAdapter.notifyDataSetChanged();
                                }
                            });
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Rotater.delHandler("getplayersinfo");
                }
            });
        Rotater.sendMsg(msg);
    }
}