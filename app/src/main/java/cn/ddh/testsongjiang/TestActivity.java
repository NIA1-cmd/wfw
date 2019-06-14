package cn.ddh.testsongjiang;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oncloudsoft.sdk.SongJiangSDk;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(TestActivity.this, msg.getData().getString("msg"), Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };
    private String fgbs = "21A000121200079";
    private String cbfgbs = "21A000121200079";
    private String caseId = "21000120190000095367";
    private String ah = "（2019）沪0117执2663号";


    private Button mBtgetUnreadCount, mBtLogout, mBtStart, mBtOpenTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_songjiang);
        mBtgetUnreadCount = findViewById(R.id.bt_getunreadcount);
        mBtLogout = findViewById(R.id.bt_logout);
        mBtStart = findViewById(R.id.bt_opensongjiang);
        mBtOpenTeam = findViewById(R.id.bt_openteam);
        mBtgetUnreadCount.setOnClickListener(this);
        mBtLogout.setOnClickListener(this);
        mBtStart.setOnClickListener(this);
        mBtOpenTeam.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getunreadcount://获取未读数量
                getUnreadCount();
                break;
            case R.id.bt_openteam://打开群聊
                openTeam();
                break;
            case R.id.bt_opensongjiang://打开松江
                startSongjiang();
                break;
            case R.id.bt_logout://退出登录
                logout();
                break;
        }
    }

    private void logout() {
        SongJiangSDk.songJiangLogout(TestActivity.this, new SongJiangSDk.onSongJiangOprationListener() {
            @Override
            public void onSuccess(String msg) {
            }

            @Override
            public void onException(String error) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", error);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

    }

    private void startSongjiang() {
        SongJiangSDk.songJiangLogin(TestActivity.this, fgbs, new SongJiangSDk.onSongJiangOprationListener() {
            @Override
            public void onSuccess(String msg) {
                SongJiangSDk.songjiangStart(TestActivity.this);
            }

            @Override
            public void onException(String error) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", error);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

    }

    private void openTeam() {

        SongJiangSDk.songJiangLogin(TestActivity.this, fgbs, new SongJiangSDk.onSongJiangOprationListener() {
            @Override
            public void onSuccess(String msg) {
                SongJiangSDk.songJiangOpenTeam(TestActivity.this, ah, cbfgbs, caseId, new SongJiangSDk.onSongJiangOprationListener() {
                    @Override
                    public void onSuccess(String msg) {

                    }

                    @Override
                    public void onException(String error) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", error);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    }
                });
            }

            @Override
            public void onException(String error) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", error);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    public void getUnreadCount() {
        SongJiangSDk.songJiangLogin(TestActivity.this, fgbs, new SongJiangSDk.onSongJiangOprationListener() {
            @Override
            public void onSuccess(String msg) {
                SongJiangSDk.songjiangGetUnreadCount(TestActivity.this, ah, new SongJiangSDk.onSongJiangOprationListener() {
                    @Override
                    public void onSuccess(String msg) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", "未读数量" + msg);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onException(String error) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", error);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });            }

            @Override
            public void onException(String error) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", error);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });





    }
}
