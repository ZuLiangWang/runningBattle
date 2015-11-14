package com.zuliangwang.runningbattle.interactor.impl;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.zuliangwang.runningbattle.RunningBattleApp;
import com.zuliangwang.runningbattle.bean.UserBean;
import com.zuliangwang.runningbattle.interactor.UserCloudInteractor;

import java.util.List;

/**
 * Created by zuliangwang on 15/11/9.
 */
public class UserInfoLeanCloudInteractorImpl implements UserCloudInteractor{

    private RunningBattleApp app;

    public UserInfoLeanCloudInteractorImpl() {
        this.app = RunningBattleApp.getRuningBattleApp();
    }


    @Override
    public void pushUserInfo(UserBean userBean) {
        AVUser avUser = new AVUser();
        avUser.setUsername();
    }


    @Override
    public UserBean getUserInfo() {
        return null;
    }

    @Override
    public List<UserBean> getRankUserBeanList() {
        return null;
    }
}
