package com.ruiyi.butternife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ruiyi.annotaten.BundView;
import com.ruiyi.base.ButterNifer;


public class MainActivity extends AppCompatActivity {
    @BundView(R.id.text1)
    public TextView text1;
    @BundView(R.id.text2)
    public TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterNifer.bund(this);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "绑定成功", Toast.LENGTH_SHORT).show();
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "绑定成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
