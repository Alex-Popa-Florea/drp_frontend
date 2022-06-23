package ic.ac.drp02;

import ic.ac.drp02.analytics.TimeToLike;

public class User {

    private static User user;
    private static String uid;

    private User(){}

    public static User getInstance(){
        if(user == null) {
            user = new User();
        }
        return user;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        User.uid = uid;
    }
}
