package im.vector.app.timeshare.chat;

public class ChatModel {
    int profileImage;
    String name,message,time;

    public ChatModel(int profileImage, String name, String message, String time) {
        this.profileImage = profileImage;
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
