/*
 * Copyright (c) 2023 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.timeshare.api_response_body;

import java.util.ArrayList;
import java.util.List;

import im.vector.app.timeshare.friends.FriendModel;
import im.vector.app.timeshare.home.model.Event;

public class GetMyActivityResponse {
    String Status,Msg,ResponseCode;

    public List<Event> get_posts = new ArrayList<>();

    public GetMyActivityResponse(String status, String msg, String responseCode, List<Event> get_posts) {
        Status = status;
        Msg = msg;
        ResponseCode = responseCode;
        this.get_posts = get_posts;
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

    public List<Event> getGet_posts() {
        return get_posts;
    }
}
