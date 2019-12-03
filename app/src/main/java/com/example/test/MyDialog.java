package com.example.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;




public class MyDialog extends Dialog {
    word word ;
    TextView textView ;
    String url_play=null;
    int INTERNET_FINISH = 5;

    final Uri uri_user = Uri.parse("content://com.example.word/word");
    public MyDialog(Context context,word word1){
        super(context);
        word = word1;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog);
        final TextView textview_en = findViewById(R.id.mydialog_textview_en);
        final TextView textview_ch = findViewById(R.id.mydialog_textview_ch);
        textView = findViewById(R.id.mydialog_textview);
        textview_en.setText(word.getEn());
        textview_ch.setText(word.getCh());
        Button button_delete = findViewById(R.id.mydialog_delete);
        Button button_change = findViewById(R.id.mydialog_change);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver resolver = getContext().getContentResolver();
                resolver.delete(uri_user,"id=?",new String[]{String.valueOf(word.getId())});
//                DBHelper dbHelper = new DBHelper(getContext());
//                SQLiteDatabase temp = dbHelper.getWritableDatabase();
//                temp.delete(DBHelper.TABLE_NAME_FIRST,"id=?",new String[]{String.valueOf(word.getId())});
                dismiss();
            }
        });
        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ContentResolver resolver = getContext().getContentResolver();
                int id = word.getId();
                final EditText textview_change_en = new EditText(getContext());
                textview_change_en.setText(word.getEn());
                final EditText textview_change_ch = new EditText(getContext());
                textview_change_ch.setText(word.getCh());
                new AlertDialog.Builder(getContext()).setTitle("更改英文").setView(textview_change_en).setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String en = textview_change_en.getText().toString();

                        new AlertDialog.Builder(getContext()).setTitle("更改中文").setView(textview_change_ch).setPositiveButton("确定", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String ch = textview_change_ch.getText().toString();
                                ContentValues values = new ContentValues();
                                values.put("en",en);
                                values.put("ch",ch);
                                resolver.update(uri_user,values,"id = ?",new String[]{String.valueOf(word.getId())});
//                                DBHelper dbHelper = new DBHelper(getContext());
//                                SQLiteDatabase temp = dbHelper.getWritableDatabase();
//                                temp.update(DBHelper.TABLE_NAME_FIRST,values,"id=?",new String[]{String.valueOf(word.getId())});
                                dismiss();
                            }
                        }).setNegativeButton("取消",null).show();
                    }
                }).setNegativeButton("取消",null).show();
            }
        });
    }
}
