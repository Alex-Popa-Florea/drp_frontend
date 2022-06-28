package ic.ac.drp02;

import java.util.List;
import java.util.Objects;

public class User {
    private final int uid;
    private final String name;
    private final String email;
    private final List<Integer> items_liked;
    private final List<Integer> users_following;
    private final String phone_no;

    public String getPhone_no() {
        return phone_no;
    }

    public User(int uid, String name, String email, List<Integer> items_liked, List<Integer> users_following, String phone_no){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.items_liked = items_liked;
        this.users_following = users_following;
        this.phone_no = phone_no;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid == user.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}
