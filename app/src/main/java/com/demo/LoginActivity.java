package com.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.utils.BaseService;
import com.demo.utils.Commons;
import com.demo.utils.EmailSyntaxChecker;
import com.demo.utils.EwpSession;
import com.demo.utils.RequestCallback;
import com.demo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements RequestCallback, View.OnClickListener{
    private EditText etLoginID, etPassword;
    private Button btnLogin;
    private ProgressDialog progress;
    private TextView tvEnterAs;
    private ImageView ivUpDownIcon;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        init();
    }

    private void init(){
        tvEnterAs = (TextView)findViewById(R.id.tvEnterAs);
        ivUpDownIcon = (ImageView)findViewById(R.id.ivUpDownIcon);

        tvEnterAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUpDownIcon.setImageResource(R.drawable.show_less);

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, tvEnterAs);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_enteras, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        tvEnterAs.setText(item.getTitle());
                        ivUpDownIcon.setImageResource(R.drawable.show_more);
                        return true;
                    }
                });
                //showing popup menu
                popup.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (!Utils.isNetworkAvailable(LoginActivity.this)) {
                } else if (!EmailSyntaxChecker.check("")) {
                } else if (etPassword.getText().toString().length() < 3) {
                } else {
                    progress = new ProgressDialog(this);
                    progress.setTitle("");
                    progress.setMessage(getResources().getString(R.string.PleaseWait));
                    progress.setCancelable(false);
                    progress.show();
                    checkLogin();
                }
                break;

            default:
                break;
        }
    }

    /**
     * For Check login credential
     */
    private void checkLogin() {
        String email = "";//etLoginID.getText().toString().trim();
        String password = "";//etPassword.getText().toString().trim();
        String url ="";
        new BaseService().callNetworkAPI(url, Commons.POST,"Login",toJsonObject(email,password).toString(),this);

    }
    @Override
    public void onSuccess(String name, Object object) {
        setUserLoginId(object.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
                Utils.toast(LoginActivity.this, "Successful Login");
            }
        });
    }

    @Override
    public void onFailure(String name, Object object) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
                Utils.toast(LoginActivity.this, "Something went wront try again later!");
            }
        });
    }

    @Override
    public void updateProgress(String name, String msg, int progressCount) {

    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public JSONObject toJsonObject(String username,String password) {
        JSONObject dict = new JSONObject();
        try {
            dict.put("email", username);
            dict.put("password", password);
            dict.put("Role", "DSM");
        } catch (JSONException e) {

        }
        return dict;
    }

    /**
     *
     * @param object
     */
    private void setUserLoginId(String object) {
        try {
            JSONObject jsonObject = new JSONObject(object);
            JSONObject jsonObject1 = jsonObject.getJSONObject("loginMessage");
            String userId = jsonObject1.getString("UserId");
            EwpSession.getSharedInstance(this).setUserId(userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}