package com.totality.corp.assignment;

import android.os.Bundle;
import com.totality.corp.assignment.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setSupportActionBar(mainBinding.toolbar);


        mainBinding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                update(s.toString());
            }
        });
    }

    private void update(String s){
        float size = getNewSize(s);
        mainBinding.editText.setTextSize(size);
    }

    private float getNewSize(String line){
        float currentSize = 80;
        while (hasLineBreak(line, currentSize)) {
            currentSize -= 1;
        }
        return currentSize;
    }

    private boolean hasLineBreak(String text,float size){
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(size);
        Layout layout =
                new StaticLayout(
                        text,
                        textPaint,
                        350,
                        Layout.Alignment.ALIGN_CENTER,
                        1,
                        0,
                        true);

        return layout.getLineCount()>1;

    }

    private int getLineCount(String text){
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(80);
        Layout layout =
                new StaticLayout(
                        text,
                        textPaint,
                        350,
                        Layout.Alignment.ALIGN_CENTER,
                        1,
                        0,
                        true);

        return layout.getLineCount();
    }

    private String getLine(String text,int lineNumber){
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(80);
        Layout layout =
                new StaticLayout(
                        text,
                        textPaint,
                        350,
                        Layout.Alignment.ALIGN_CENTER,
                        1,
                        0,
                        true);
        return text.substring(layout.getLineStart(lineNumber),layout.getLineEnd(lineNumber));

    }

}
