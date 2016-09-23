package com.ccma.itri.org.tw.carpediem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.UserData.UserData;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    protected UserData mUserDataManager;
    android.app.FragmentManager fragmentMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.textTest);
        text.setText(getUserDataManager().getUserToken());

//        getUserDataManager().clearUserData();
        pushNewFragment(new CardFragment());
//        Log.d("mainGet",UserData.getInstance().getUserUUID());
    }
    protected UserData getUserDataManager() {
        if(mUserDataManager == null) {
            mUserDataManager = UserData.getInstance();
        }
        return mUserDataManager;
    }

    private void pushNewFragment(android.app.Fragment fragment){
        fragmentMgr = getFragmentManager();
        android.app.FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.replace(R.id.fragment, fragment);
//        fragmentTrans.add(R.id.frameLayout, fragment, "Second");
//        fragmentTrans.replace(R.id.frameLayout, fragment);
        fragmentTrans.commit();
    }
}
