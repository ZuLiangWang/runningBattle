package com.zuliangwang.runningbattle.presenter.impl;

import com.zuliangwang.runningbattle.presenter.LoginPresenter;
import com.zuliangwang.runningbattle.view.LoginView;

/**
 * Created by zuliangwang on 15/11/9.
 */
public class LoginPresenterImpl implements LoginPresenter {

    LoginView loginView ;

    public LoginPresenterImpl(LoginView view) {
        loginView = view;
    }


}
