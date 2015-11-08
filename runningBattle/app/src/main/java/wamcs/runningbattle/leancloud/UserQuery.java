package wamcs.runningbattle.leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuliangwang on 15/10/25.
 */
public class UserQuery {

    public ArrayList<UserNumber> getUserNumbers(){
        final ArrayList<UserNumber> numberList = new ArrayList<UserNumber>();
        AVQuery<AVObject> query = new AVQuery<AVObject>("_User");
        query.orderByAscending("number");
        query.setLimit(50);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (int i=0;i<list.size();i++){
                    UserNumber userNumber = new UserNumber();
                    userNumber.setUserName(list.get(i).getString("username"));
                    userNumber.setUserNumber(list.get(i).getInt("number"));
                    userNumber.setUserRank(i);
                    numberList.add(userNumber);
                }
            }
        });
        return numberList;
    }
}
