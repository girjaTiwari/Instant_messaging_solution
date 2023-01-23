package im.vector.app.timeshare.friends;

public class RequestSentModel {
    String friend_request_uuid,reciever_uuid,reciever_name,reciever_pic,mutual_friends,created_at;

    public RequestSentModel(String friend_request_uuid, String reciever_uuid, String reciever_name, String reciever_pic, String mutual_friends, String created_at) {
        this.friend_request_uuid = friend_request_uuid;
        this.reciever_uuid = reciever_uuid;
        this.reciever_name = reciever_name;
        this.reciever_pic = reciever_pic;
        this.mutual_friends = mutual_friends;
        this.created_at = created_at;
    }

    public String getFriend_request_uuid() {
        return friend_request_uuid;
    }

    public String getReciever_uuid() {
        return reciever_uuid;
    }

    public String getReciever_name() {
        return reciever_name;
    }

    public String getReciever_pic() {
        return reciever_pic;
    }

    public String getMutual_friends() {
        return mutual_friends;
    }

    public String getCreated_at() {
        return created_at;
    }
}
