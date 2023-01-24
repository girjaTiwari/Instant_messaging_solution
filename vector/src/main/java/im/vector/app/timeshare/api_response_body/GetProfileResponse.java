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
import im.vector.app.timeshare.webservices.AccountStatus;

public class GetProfileResponse {
    String Status,Msg,ResponseCode;

    ProfileData get_profile;

    public GetProfileResponse(String status, String msg, String responseCode, ProfileData get_profile) {
        Status = status;
        Msg = msg;
        ResponseCode = responseCode;
        this.get_profile = get_profile;
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

    public ProfileData getGet_profile() {
        return get_profile;
    }
}
