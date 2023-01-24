package im.vector.app.timeshare.menu;

public class Following {
    String user_uuid,first_name,last_name,profile_name,user_pic;

    public Following(String user_uuid, String first_name, String last_name, String profile_name, String user_pic) {
        this.user_uuid = user_uuid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_name = profile_name;
        this.user_pic = user_pic;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public String getUser_pic() {
        return user_pic;
    }
}
