package com.example.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Main_activity extends AppCompatActivity{

    final Uri uri_user = Uri.parse("content://com.example.word/word");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    private void init(){
        List<word> wordList = new ArrayList<word>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri_user,new String[]{"id","en","ch"},null,null,null);
        while (cursor.moveToNext()){
            word word = new word();
            word.setId(cursor.getInt(0));
            word.setEn(cursor.getString(1));
            word.setCh(cursor.getString(2));
            wordList.add(word);
        }
        wordAp wordAdapter = new wordAp(Main_activity.this,R.layout.word_item,wordList);
        ListView listView = findViewById(R.id.layout_listview);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word word=(word)adapterView.getItemAtPosition(i);
                MyDialog myDialog=new MyDialog(Main_activity.this,word);
                myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        init();
                    }
                });
                myDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.find){
            final EditText edittext_find = new EditText(Main_activity.this);
            new AlertDialog.Builder(Main_activity.this).setTitle("查找").setView(edittext_find).setPositiveButton("查找", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ContentResolver resolver = getContentResolver();
//                    DBHelper db=new DBHelper(MainActivity.this);
//                    SQLiteDatabase resolver = db.getWritableDatabase();
                    Cursor cursor =  resolver.query(uri_user,new String[]{"id","en","ch"},"en like ?",new String[]{edittext_find.getText().toString()+"%"},null);
//                    Cursor cursor = resolver.query(DBHelper.TABLE_NAME_FIRST,new String[]{"id","en","ch"},"en like ?",new String[]{edittext_find.getText()+"%"},null,null,null);
                    List<word> wordList = new ArrayList<word>();
                    while (cursor.moveToNext()){
                        word word = new word();
                        word.setId(cursor.getInt(0));
                        word.setEn(cursor.getString(1));
                        word.setCh(cursor.getString(2));
                        wordList.add(word);
                    }
                    wordAp wordAdapter = new wordAp(Main_activity.this,R.layout.word_item,wordList);
                    ListView listView = findViewById(R.id.layout_listview);
                    listView.setAdapter(wordAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            word word=(word)adapterView.getItemAtPosition(i);
                            Toast.makeText(Main_activity.this, word.getEn(),Toast.LENGTH_SHORT).show();
                            MyDialog myDialog=new MyDialog(Main_activity.this,word);
                            myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    init();
                                }
                            });
                            myDialog.show();
                        }
                    });
                }
            }).show();
        }
        if(item.getItemId()==R.id.add_word){


            final ContentResolver resolver = getContentResolver();
            final EditText editText_input_en = new EditText(Main_activity.this);
            new AlertDialog.Builder(Main_activity.this).setTitle("请输入英文").setView(editText_input_en).setPositiveButton("添加", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final String en = editText_input_en.getText().toString();
                    final EditText edittext_input_ch = new EditText(Main_activity.this);
                    new AlertDialog.Builder(Main_activity.this).setTitle("请输入中文").setView(edittext_input_ch).setPositiveButton("添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String ch = edittext_input_ch.getText().toString();
                            ContentValues values = new ContentValues();
                            values.put("en",en);
                            values.put("ch",ch);
//                            DBHelper db = new DBHelper(MainActivity.this);
//                            SQLiteDatabase temp = db.getWritableDatabase();
//                            temp.insert(DBHelper.TABLE_NAME_FIRST,null,values);
                            resolver.insert(uri_user,values);
                            init();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        }
        if(item.getItemId()==R.id.layout_fresh){
            init();
        }
        return true;
    }
}
