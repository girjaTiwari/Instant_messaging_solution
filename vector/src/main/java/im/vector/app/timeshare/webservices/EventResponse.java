package im.vector.app.timeshare.webservices;

import java.util.ArrayList;
import java.util.List;

import im.vector.app.timeshare.home.model.Event;

public class EventResponse {
    String Status,Msg,ResponseCode;

    public List<Event> get_timelines = new ArrayList<>();

    public EventResponse(String status, String msg, String responseCode, List<Event> get_timelines) {
        Status = status;
        Msg = msg;
        ResponseCode = responseCode;
        this.get_timelines = get_timelines;
    }

    public String getStatus() {
        return Status;
    }

    public String getMsg() {
        return Msg;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public List<Event> getGet_timelines() {
        return get_timelines;
    }
}
