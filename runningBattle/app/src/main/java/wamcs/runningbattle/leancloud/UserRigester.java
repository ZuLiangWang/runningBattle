package wamcs.runningbattle.leancloud;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

/**
 * Created by zuliangwang on 15/10/25.
 */
public class UserRigester {
    public void rigester(String userName,String password){
        this.rigester(userName, password, null);
    }

    public void rigester(String userName,String password,String email){
        AVUser user = new AVUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("Register", "succeful");
                } else {
                    Log.d("Register", "failed");
                    e.printStackTrace();
                }
            }
        });
    }


    public void login(String userName,String password){
        AVUser.logInInBackground(userName, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (avUser == null) {
                    Log.d("login", "failed");
                } else {
                    Log.d("login", "succeful");
                }
            }
        });
    }

    public boolean hasCurrentUser(AVUser currentUser){
        currentUser = AVUser.getCurrentUser();
        if (currentUser!=null)
            return true;
        else {
            return false;
        }
    }

    public AVUser removeCurrentUser(){
        AVUser.logOut();
        AVUser currentUser = AVUser.getCurrentUser();
        return currentUser;
    }



}
