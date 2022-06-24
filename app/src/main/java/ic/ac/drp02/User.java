package ic.ac.drp02;

import java.util.List;

public class User {
    private final int uid;
    private final String name;
    private final String email;
    private final List<Integer> items_liked;
    private final List<Integer> users_following;

    public User(int uid,String name,String email,List<Integer> items_liked,List<Integer> users_following){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.items_liked = items_liked;
        this.users_following = users_following;
    }

    public List<Integer> getUsers_following() {
        return users_following;
    }

    public List<Integer> getItems_liked() {
        return items_liked;
    }

    public Integer getUid() {return uid;}

    public String getName() {
        return name;
    }
}
