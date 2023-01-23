package im.vector.app.timeshare.friends;

public class Suggetion {
    String user_id,first_name,last_name,name,profile_pic,mutual_friends;

    public Suggetion(String user_id, String first_name, String last_name, String name, String profile_pic, String mutual_friends) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.name = name;
        this.profile_pic = profile_pic;
        this.mutual_friends = mutual_friends;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getName() {
        return name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getMutual_friends() {
        return mutual_friends;
    }
}
