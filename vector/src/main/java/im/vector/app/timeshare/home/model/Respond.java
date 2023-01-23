package im.vector.app.timeshare.home.model;

public class Respond {
    String respond_uuid,activity_uuid,user_name,user_pic,respond,created_at,EpochTime;

    public Respond(String respond_uuid, String activity_uuid, String user_name, String user_pic, String respond, String created_at, String epochTime) {
        this.respond_uuid = respond_uuid;
        this.activity_uuid = activity_uuid;
        this.user_name = user_name;
        this.user_pic = user_pic;
        this.respond = respond;
        this.created_at = created_at;
        EpochTime = epochTime;
    }

    public String getRespond_uuid() {
        return respond_uuid;
    }

    public String getActivity_uuid() {
        return activity_uuid;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public String getRespond() {
        return respond;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getEpochTime() {
        return EpochTime;
    }
}
