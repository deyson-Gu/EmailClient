package com.exercise.p.emailclient.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exercise.p.emailclient.GlobalInfo;
import com.exercise.p.emailclient.R;
import com.exercise.p.emailclient.databinding.ActivitySignBinding;
import com.exercise.p.emailclient.dto.param.User;
import com.exercise.p.emailclient.presenter.SignPresenter;
import com.exercise.p.emailclient.view.SignView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity implements SignView {

    public static String TAG = "Sign";

    ActivitySignBinding binding;
    @BindView(R.id.sign_edit_email)
    EditText signEditEmail;
    @BindView(R.id.sign_edit_psw)
    EditText signEditPsw;
    @BindView(R.id.sign_edit_check_code)
    EditText signEditCheckCode;
    @BindView(R.id.sign_img_check_code)
    ImageView signImgCheckCode;
    @BindView(R.id.sign_button_reget_code)
    Button signButtonRegetCode;
    @BindView(R.id.sign_button_sign_in)
    Button signButtonSignIn;
    @BindView(R.id.sign_root)
    LinearLayout signRoot;

    Timer timer = null;
    TimerTask task = null;

    SignPresenter presenter;
    User user;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String[] strs = {"正在登录", "正在登录.", "正在登录..", "正在登录..."};
            signButtonSignIn.setText(strs[msg.what]);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign);
        binding.setShow(true);
        ButterKnife.bind(this);
        controlKeyboardLayout(signRoot);
        presenter = new SignPresenter(this);
        user = new User();
        binding.setUser(user);
        binding.setShow(true);
        initCheckCode();
    }

    @OnClick(R.id.sign_button_reget_code)
    public void initCheckCode(){
        presenter.getCheckImg();
    }
    /**
     * 控制键盘布局
     *
     * @param root 根布局控件
     */
    private void controlKeyboardLayout(final LinearLayout root) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
                        int rootInvisibleHeight = root.getRootView()
                                .getHeight() - rect.bottom;
                        // 若rootInvisibleHeight高度大于100，则说明当前视图上移了，说明软键盘弹出了
                        if (rootInvisibleHeight > 100) {
                            binding.setShow(false);
                        } else {
                            // 软键盘没有弹出来的时候
                            binding.setShow(true);
                        }
                    }
                });
    }

    @OnClick(R.id.sign_button_sign_in)
    public void signIn(){
        user.setEmail(user.getEmail().trim());
        user.setCheckCode(user.getCheckCode().trim());
        binding.setUser(user);
        presenter.sign(user);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent();
        intent.setClass(SignActivity.this, MainActivity.class);
        startActivity(intent);
        SignActivity.this.finish();
    }

    @Override
    public void showProgress(boolean show) {
        signButtonSignIn.setText("登录");
        signButtonSignIn.setClickable(!show);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (show) {
            timer = new Timer();
            task = new TimerTask() {
                int i = 0;
                @Override
                public void run() {
                    handler.sendEmptyMessage(i % 4);
                    i++;
                }
            };
            timer.schedule(task, 0, 500);
        }
    }

    @Override
    public void showCheckImg(Bitmap bitmap) {
        signImgCheckCode.setImageBitmap(bitmap);
    }
}