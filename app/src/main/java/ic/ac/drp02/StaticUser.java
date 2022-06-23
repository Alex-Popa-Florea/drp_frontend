package ic.ac.drp02;

public class StaticUser {

    private static StaticUser staticUser;
    private static String uid;

    private StaticUser(){}

    public static StaticUser getInstance(){
        if(staticUser == null) {
            staticUser = new StaticUser();
        }
        return staticUser;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        StaticUser.uid = uid;
    }
}
