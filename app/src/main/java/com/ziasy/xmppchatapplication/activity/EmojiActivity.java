package com.ziasy.xmppchatapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.adapter.EmojiAdapter;
import com.ziasy.xmppchatapplication.common.GridItemView;
import com.ziasy.xmppchatapplication.model.EmojiMadel;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;

import java.util.ArrayList;

public class EmojiActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<EmojiMadel> list;
    private ArrayList<Integer> selectedStrings;
    private ArrayList<String> selectValue;

    private EmojiAdapter adapter;
    private ImageView backBtn;
    private TextView sendTxt;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emoji_listview);

        selectedStrings = new ArrayList<>();
        selectValue = new ArrayList<>();
        list = new ArrayList<>();

        list.add(new EmojiMadel("emoji",R.drawable.emoji));
        list.add(new EmojiMadel("emoji2",R.drawable.emoji2));
        list.add(new EmojiMadel("emoji3",R.drawable.emoji3));
        list.add(new EmojiMadel("emoji4",R.drawable.emoji4));
        list.add(new EmojiMadel("emoji5",R.drawable.emoji5));
        list.add(new EmojiMadel("emoji6",R.drawable.emoji6));
        list.add(new EmojiMadel("emoji7",R.drawable.emoji7));
        list.add(new EmojiMadel("emoji8",R.drawable.emoji8));
        list.add(new EmojiMadel("emoji9",R.drawable.emoji9));
        list.add(new EmojiMadel("emoji10",R.drawable.emoji10));
        list.add(new EmojiMadel("emoji11",R.drawable.emoji11));
        list.add(new EmojiMadel("emoji12",R.drawable.emoji12));
        list.add(new EmojiMadel("emoji13",R.drawable.emoji13));
        list.add(new EmojiMadel("emoji14",R.drawable.emoji14));
        list.add(new EmojiMadel("emoji15",R.drawable.emoji15));
        list.add(new EmojiMadel("emoji16",R.drawable.emoji16));
        list.add(new EmojiMadel("emoji17",R.drawable.emoji17));
        list.add(new EmojiMadel("emoji18",R.drawable.emoji18));
        list.add(new EmojiMadel("emoji19",R.drawable.emoji19));
        list.add(new EmojiMadel("emoji20",R.drawable.emoji20));
        list.add(new EmojiMadel("emoji21",R.drawable.emoji21));
        list.add(new EmojiMadel("emoji22",R.drawable.emoji22));
        list.add(new EmojiMadel("emoji23",R.drawable.emoji23));
        list.add(new EmojiMadel("emoji24",R.drawable.emoji24));
        list.add(new EmojiMadel("emoji25",R.drawable.emoji25));
        list.add(new EmojiMadel("emoji26",R.drawable.emoji26));
        list.add(new EmojiMadel("emoji27",R.drawable.emoji27));
        list.add(new EmojiMadel("emoji28",R.drawable.emoji28));
        list.add(new EmojiMadel("emoji29",R.drawable.emoji29));
        list.add(new EmojiMadel("emoji30",R.drawable.emoji30));
        list.add(new EmojiMadel("emoji31",R.drawable.emoji31));
        list.add(new EmojiMadel("emoji32",R.drawable.emoji32));
        list.add(new EmojiMadel("emoji33",R.drawable.emoji33));
        list.add(new EmojiMadel("emoji34",R.drawable.emoji34));
        list.add(new EmojiMadel("emoji35",R.drawable.emoji35));
        list.add(new EmojiMadel("emoji36",R.drawable.emoji36));
        list.add(new EmojiMadel("emoji37",R.drawable.emoji37));
        list.add(new EmojiMadel("emoji38",R.drawable.emoji38));
        list.add(new EmojiMadel("emoji39",R.drawable.emoji39));
        list.add(new EmojiMadel("emoji40",R.drawable.emoji40));
        list.add(new EmojiMadel("emoji41",R.drawable.emoji41));
        list.add(new EmojiMadel("emoji42",R.drawable.emoji42));
        list.add(new EmojiMadel("emoji43",R.drawable.emoji43));
        list.add(new EmojiMadel("emoji44",R.drawable.emoji44));
        list.add(new EmojiMadel("emoji45",R.drawable.emoji45));
        list.add(new EmojiMadel("emoji46",R.drawable.emoji46));
        list.add(new EmojiMadel("emoji47",R.drawable.emoji47));
        list.add(new EmojiMadel("emoji48",R.drawable.emoji48));
        list.add(new EmojiMadel("emoji49",R.drawable.emoji49));
        list.add(new EmojiMadel("emoji50",R.drawable.emoji50));
        list.add(new EmojiMadel("emoji51",R.drawable.emoji51));
        list.add(new EmojiMadel("emoji52",R.drawable.emoji52));
        list.add(new EmojiMadel("emoji53",R.drawable.emoji53));
        list.add(new EmojiMadel("emoji54",R.drawable.emoji54));
        list.add(new EmojiMadel("emoji55",R.drawable.emoji55));
        list.add(new EmojiMadel("emoji56",R.drawable.emoji56));
        list.add(new EmojiMadel("emoji57",R.drawable.emoji57));
        list.add(new EmojiMadel("emoji58",R.drawable.emoji58));
        list.add(new EmojiMadel("emoji59",R.drawable.emoji59));
        list.add(new EmojiMadel("emoji60",R.drawable.emoji60));
        list.add(new EmojiMadel("emoji61",R.drawable.emoji61));
        list.add(new EmojiMadel("emoji62",R.drawable.emoji62));
        list.add(new EmojiMadel("emoji63",R.drawable.emoji63));
        list.add(new EmojiMadel("emoji64",R.drawable.emoji64));
        list.add(new EmojiMadel("emoji65",R.drawable.emoji65));
        list.add(new EmojiMadel("emoji66",R.drawable.emoji66));
        list.add(new EmojiMadel("emoji67",R.drawable.emoji67));
        list.add(new EmojiMadel("emoji68",R.drawable.emoji68));
        list.add(new EmojiMadel("emoji69",R.drawable.emoji69));
        list.add(new EmojiMadel("emoji70",R.drawable.emoji70));
        list.add(new EmojiMadel("emoji71",R.drawable.emoji71));
        list.add(new EmojiMadel("emoji72",R.drawable.emoji72));
        list.add(new EmojiMadel("emoji73",R.drawable.emoji73));
        list.add(new EmojiMadel("emoji74",R.drawable.emoji74));
        list.add(new EmojiMadel("emoji75",R.drawable.emoji75));
        list.add(new EmojiMadel("emoji76",R.drawable.emoji76));
        list.add(new EmojiMadel("emoji77",R.drawable.emoji77));
        list.add(new EmojiMadel("emoji78",R.drawable.emoji78));
        list.add(new EmojiMadel("emoji79",R.drawable.emoji79));


        gridView = (GridView) findViewById(R.id.grid_view_id);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        sendTxt = (TextView) findViewById(R.id.sendTxt);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("selectedStrings",selectedStrings.size()+"");
                if (selectValue.size()<=0){
                    Toast.makeText(EmojiActivity.this,"Please Select Emoji First",Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("image", selectValue);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        adapter = new EmojiAdapter(EmojiActivity.this, R.layout.emojicon_item, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(EmojiActivity.this, "Position " + i  +"EMOJI RESOURCE ID "+list.get(i), Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent();
                intent.putExtra("image", list.get(i));
                setResult(RESULT_OK, intent);
                finish();*/
                int selectedIndex = adapter.selectedPositions.indexOf(i);

                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    ((GridItemView) view).display(false);
                    selectValue.remove( list.get(i).getName());
                } else {
                    adapter.selectedPositions.add(i);
                    ((GridItemView) view).display(true);
                    selectValue.add(list.get(i).getName());

                }
            }
        });

    }
}
