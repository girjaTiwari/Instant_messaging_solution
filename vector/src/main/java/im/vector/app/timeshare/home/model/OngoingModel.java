package im.vector.app.timeshare.home.model;

public class OngoingModel {
    int image;
    String eventname;

    public OngoingModel(int image, String eventname) {
        this.image = image;
        this.eventname = eventname;
    }

    public int getImage() {
        return image;
    }

    public String getEventname() {
        return eventname;
    }
}
