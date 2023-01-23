package im.vector.app.timeshare.home.model;

public class JoiningUser {
    String activity_uuid,first_name,last_name,user_name,profile_pic;

    public JoiningUser(String activity_uuid, String first_name, String last_name, String user_name, String profile_pic) {
        this.activity_uuid = activity_uuid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.profile_pic = profile_pic;
    }

    public String getActivity_uuid() {
        return activity_uuid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }
}
