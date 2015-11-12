package com.zuliangwang.runningbattle.interactor;

import com.zuliangwang.runningbattle.bean.UserBean;

import java.util.List;

/**
 * Created by zuliangwang on 15/11/9.
 */
public interface UserCloudInteractor {

    public void pushUserInfo(UserBean userBean);

    public UserBean getUserInfo();

    public List<UserBean> getRankUserBeanList();


}
