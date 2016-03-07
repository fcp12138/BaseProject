package com.fcp.baseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fcp.baseproject.album.AlbumActivity;
import com.fcp.baseproject.base.activity.ProgressActivity;

public class MainActivity extends ProgressActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithToolbar(R.layout.activity_main);
        getRightBtn().setText("相册");
        getRightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AlbumActivity.class),1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Log.d("fcp",data.getStringArrayListExtra(AlbumActivity.EXTRA_RESULT).toString());
        }

    }
}
