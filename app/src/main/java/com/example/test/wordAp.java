package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

class word{
    private int id;
    private String en;
    private String ch;
    public int getId(){
        return this.id;
    }
    public String getEn(){
        return this.en;
    }
    public String getCh(){
        return this.ch;
    }
    public void setId(int setid){
        this.id=setid;
    }
    public void setEn(String seten){
        this.en=seten;
    }
    public void setCh(String setch){
        this.ch=setch;
    }
}

public class wordAp extends ArrayAdapter<word> {
    private int resourceId;
    public wordAp(Context context,int textViewResourceId,List<word> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        word word = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView word_item_textview_en = view.findViewById(R.id.word_item_textview_en);
        word_item_textview_en.setText(word.getEn());
        TextView word_item_textview_ch = view.findViewById(R.id.word_item_textview_ch);
        word_item_textview_ch.setText(word.getCh());
        return view;
    }
}
