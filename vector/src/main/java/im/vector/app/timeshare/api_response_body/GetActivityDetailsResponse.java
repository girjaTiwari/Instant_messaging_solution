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

import im.vector.app.timeshare.home.model.JoiningUser;

public class GetActivityDetailsResponse {
    String Status,Msg,ResponseCode;

    TimelineDetails get_activities;

    public GetActivityDetailsResponse(String status, String msg, String responseCode, TimelineDetails get_activities) {
        Status = status;
        Msg = msg;
        ResponseCode = responseCode;
        this.get_activities = get_activities;
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

    public TimelineDetails getGet_activities() {
        return get_activities;
    }
}
