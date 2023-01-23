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

import im.vector.app.timeshare.friends.RequestSentModel;
import im.vector.app.timeshare.friends.Suggetion;

public class GetSentFriendRequestResponse {
    String Status,Msg,ResponseCode;

    public List<RequestSentModel> get_sent_friend_request = new ArrayList<>();

    public GetSentFriendRequestResponse(String status, String msg, String responseCode, List<RequestSentModel> get_sent_friend_request) {
        Status = status;
        Msg = msg;
        ResponseCode = responseCode;
        this.get_sent_friend_request = get_sent_friend_request;
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

    public List<RequestSentModel> getGet_sent_friend_request() {
        return get_sent_friend_request;
    }
}
