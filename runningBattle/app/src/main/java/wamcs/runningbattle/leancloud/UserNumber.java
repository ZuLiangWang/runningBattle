package wamcs.runningbattle.leancloud;

/**
 * Created by zuliangwang on 15/10/25.
 */
public class UserNumber {
    private String userName;
    private int userNumber;

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    private int userRank;
}
