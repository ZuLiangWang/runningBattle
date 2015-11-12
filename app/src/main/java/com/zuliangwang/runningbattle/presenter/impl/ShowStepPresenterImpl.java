package com.zuliangwang.runningbattle.presenter.impl;

import com.zuliangwang.runningbattle.presenter.ShowStepPresenter;
import com.zuliangwang.runningbattle.view.ShowStepView;

/**
 * Created by zuliangwang on 15/11/12.
 */
public class ShowStepPresenterImpl implements ShowStepPresenter {
    private ShowStepView showStepView;

    public ShowStepPresenterImpl(ShowStepView showStepView) {
        this.showStepView = showStepView;
    }

    @Override
    public String getTotalStep() {

        return null;
    }
}
