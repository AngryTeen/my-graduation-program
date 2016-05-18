package edu.learning.jonnypeng.graduationproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.learning.jonnypeng.graduationproject.R;
import edu.learning.jonnypeng.graduationproject.service.GetInfoService;
import edu.learning.jonnypeng.graduationproject.utils.ServiceUtil;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        ServiceUtil serviceUtil = new ServiceUtil(this);
        if (false == serviceUtil.isServiceRunning(GetInfoService.class.getName())) {

            Intent mIntent = new Intent(this, GetInfoService.class);
            startService(mIntent);
        }

    }
}
