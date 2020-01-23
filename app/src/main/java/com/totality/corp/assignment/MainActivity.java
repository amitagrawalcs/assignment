package com.totality.corp.assignment;

import android.os.Bundle;
import com.totality.corp.assignment.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;



public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    TextPaint textPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setSupportActionBar(mainBinding.toolbar);
        textPaint = new TextPaint();

        mainBinding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                update(s);
            }
        });
    }

    private void update(final Editable s){
        textPaint.setTextSize(16);

        Layout layout = new StaticLayout(s.toString(),
                        textPaint,
                  350 -  mainBinding.editText.getCompoundPaddingStart() - mainBinding.editText.getCompoundPaddingEnd(),
                        Layout.Alignment.ALIGN_CENTER,
                        1,
                        0,
                        true);


        // remove all the previous spans
        AbsoluteSizeSpan[] span = s.getSpans(0,s.length(),AbsoluteSizeSpan.class);
        for (AbsoluteSizeSpan absoluteSizeSpan : span) {
            s.removeSpan(absoluteSizeSpan);
        }

        // set spans in each line seperately
        for (int i=0;i<layout.getLineCount();i++){

            String line = s.toString().substring(layout.getLineStart(i),layout.getLineEnd(i));
            s.setSpan(new AbsoluteSizeSpan((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,getNewSize(line),getResources().getDisplayMetrics())),
                    layout.getLineStart(i),
                    layout.getLineEnd(i),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        }
    }


    private boolean hasLineBreak(String text,float size){
        textPaint.setTextSize(size);
        Layout layout = new StaticLayout(
                        text,
                        textPaint,
                        350 - mainBinding.editText.getCompoundPaddingStart() - mainBinding.editText.getCompoundPaddingEnd(),
                        Layout.Alignment.ALIGN_NORMAL,
                        1,
                        0,
                        true
        );

        return layout.getLineCount()>1;
    }

    private float getNewSize(String line){
        int lowSize = 16;
        int highSize = 80;
        int currentSize = lowSize + (int) Math.floor((highSize - lowSize) / 2f);
        while (lowSize < currentSize) {
            if (hasLineBreak(line, currentSize)) {
                highSize = currentSize;
            } else {
                lowSize = currentSize;
            }
            currentSize = lowSize + (int) Math.floor((highSize - lowSize) / 2f);
        }

        return currentSize;
    }

}
