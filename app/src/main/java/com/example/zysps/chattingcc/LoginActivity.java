package com.example.zysps.chattingcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by zysps on 2017/6/16 0016.
 */
public class LoginActivity extends Activity{
    private View progress;

    private Button loginbutton;

    private View mInputLayout;

    private float mWidth, mHeight;

    private EditText mName, mPsw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loginactivity_x);
        initView();
    }
//    <!--
//    setcontentview()-给当前活动加载一个布局，一般传入一个布局文件的id
//    任何资源都会在R文件中生成一个相应的资源id-->

    private void initView() {
        progress = findViewById(R.id.layout_progress);
        loginbutton = (Button) findViewById(R.id.login_button);
        mName = (EditText) findViewById(R.id.input_layout_name);
        mPsw = (EditText) findViewById(R.id.input_layout_psw);
//通过findviewbyid（）方法得到edittext和button的实例
        loginbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mWidth = loginbutton.getMeasuredWidth();
                mHeight = loginbutton.getMeasuredHeight();
                // 隐藏输入框
                mName.setVisibility(View.INVISIBLE);
                mPsw.setVisibility(View.INVISIBLE);
                doLogin();
            }
        });
    }
//登陆函数，网易云信
    public void doLogin() {
        LoginInfo info = new LoginInfo(mName.getText().toString().toLowerCase(),mPsw.getText().toString()); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        MyCache.setAccount(mName.getText().toString().toLowerCase());
                        Log.i("tag", "onSuccess");
//                      一般使用log而不使用system.out进行打印
//                      log.v()打印最为琐碎的日志信息
//                      log.d()打印调试信息
//                      log.i()打印比较重要的数据，对应级别info，比debug高一级
//                      log.e()打印错误信息
                        Toast.makeText(LoginActivity.this , "登录成功",
                                Toast.LENGTH_SHORT).show();
//                      toast-将一些短小的信息通知给用户
//                        通过静态放马maketext()创建出一个toast对象，然后调用show将其显示出来
//                        maketext需要传入三个参数
//                        第一个参数context：toast要求的上下文，活动本身即可
//                        第二个参数是toast显示的文本内容
//                        第三个参数是toast显示的时长

                        Intent intent = new Intent(LoginActivity.this , MainActivity_cc.class);
                        startActivity(intent);
                        finish();
//                        使用intent在活动之间穿梭
//                        intent可以指明当前组件想要执行的动作，还可以在不同组件之间传递数据
//                        一般可悲用于启动活动，启动服务，发送广播等场景
//                        分为显式intent和隐式intent
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.i("tag", "onFailed" +code);
                        Toast.makeText(LoginActivity.this , "登陆失败",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.i("tag", "onException");
                        Toast.makeText(LoginActivity.this , "onException",
                                Toast.LENGTH_SHORT).show();
                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
}
