package com.example.zysps.chattingcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;


public class MainActivity_cc extends AppCompatActivity implements View.OnClickListener {

    private ListviewAdapter adapter = null;
//适配器
    private ListView listview;
//    Android中最难用最常用的控件之一，listview允许用户通过手指上下滑动的方式将屏幕外的数据滚动到屏幕内，同时屏幕上原有的数据则会滚出屏幕
    private Button btn_left;
    private EditText et_meg;
    private Button btn_right;
//定义button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        初始化布局
//oncreate（）方法用来初始化activity实例对象
        adapter = new ListviewAdapter(this);
        listview.setAdapter(adapter);
//        调用listview的setadapter方法，将构建好的适配器对象传递进去，建立listview和数据之间的关联
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }
//适配器和网易云信的代码
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
    }
//销毁mainactivity，网易云信的指令
    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    for (IMMessage message : messages) {
                        adapter.addDataToAdapter(new MsgInfo(message.getContent(), null));
                        adapter.notifyDataSetChanged();
                        listview.smoothScrollToPosition(listview.getCount() - 1);
                    }
                }
            };
//网易云信与适配器
    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
//        btn_left = (Button) findViewById(R.id.btn_left);
        et_meg = (EditText) findViewById(R.id.et_meg);
        btn_right = (Button) findViewById(R.id.btn_right);
//通过findviewbyid（）方法得到edittext和button的实例
//        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }
//    初始化，显示基本页面

    @Override
    public void onClick(View v) {
//注册监听器，每当点击按钮时，就会执行监听器中的onclick方法

        String msg = et_meg.getText().toString().trim();
//使用gettext（）方法获取到输入的内容，再调用tostring（）方法转换成字符串
        switch (v.getId()) {
//            case R.id.btn_left:
//                adapter.addDataToAdapter(new MsgInfo(msg,null));
//                adapter.notifyDataSetChanged();
//                break;
            case R.id.btn_right:
                adapter.addDataToAdapter(new MsgInfo(null, msg));
                adapter.notifyDataSetChanged();
                break;
        }

        listview.smoothScrollToPosition(listview.getCount() - 1);

        et_meg.setText("");
//清空输入框

        IMMessage message = MessageBuilder.createTextMessage(
                "zysps1", // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                msg // 文本内容
        );
//        IMMessage message = MessageBuilder.createTextMessage(
//                "A_a", // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
//                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
//                msg // 文本内容
//        );
// 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        NIMClient.getService(MsgService.class).sendMessage(message, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Toast.makeText(MainActivity_cc.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int code) {
                Toast.makeText(MainActivity_cc.this, "failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable exception) {

            }
        });

    }

    public void loginout() {
        NIMClient.getService(AuthService.class).logout();
        startActivity(new Intent(MainActivity_cc.this, LoginInfo.class));
        finish();
    }
//网易云信，登出
}
