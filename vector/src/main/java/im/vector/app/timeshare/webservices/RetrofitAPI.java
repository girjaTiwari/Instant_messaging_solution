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

package im.vector.app.timeshare.webservices;



import java.util.List;

import im.vector.app.timeshare.api_request_body.Accept_and_DeclineRequest;
import im.vector.app.timeshare.api_request_body.CreateRespondRequest;
import im.vector.app.timeshare.api_request_body.DeleteActivityRequest;
import im.vector.app.timeshare.api_request_body.GetActivityDetailsRequest;
import im.vector.app.timeshare.api_request_body.GetAttendiesRequest;
import im.vector.app.timeshare.api_request_body.GetProfileRequest;
import im.vector.app.timeshare.api_request_body.GetRespondRequest;
import im.vector.app.timeshare.api_request_body.JoinActivityRequest;
import im.vector.app.timeshare.api_request_body.ResentOtpRequest;
import im.vector.app.timeshare.api_request_body.SendRequest;
import im.vector.app.timeshare.api_request_body.SignupRequest;
import im.vector.app.timeshare.api_request_body.VerifyEmailRequest;
import im.vector.app.timeshare.api_response_body.EventResponse;
import im.vector.app.timeshare.api_response_body.GetActivityDetailsResponse;
import im.vector.app.timeshare.api_response_body.GetActivityJoingingResponse;
import im.vector.app.timeshare.api_response_body.GetFriendListResponse;
import im.vector.app.timeshare.api_response_body.GetFriendRequestResponse;
import im.vector.app.timeshare.api_response_body.GetFriendSuggetionResponse;
import im.vector.app.timeshare.api_response_body.GetMyActivityResponse;
import im.vector.app.timeshare.api_response_body.GetProfileResponse;
import im.vector.app.timeshare.api_response_body.GetRespondResponse;
import im.vector.app.timeshare.api_response_body.GetSentFriendRequestResponse;
import im.vector.app.timeshare.api_response_body.JoinActivityResponse;
import im.vector.app.timeshare.api_response_body.LoginResponse;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.api_response_body.UploadImage;
import im.vector.app.timeshare.api_request_body.LoginRequest;
import im.vector.app.timeshare.api_request_body.CommonRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitAPI {


    @POST("loggedin")
    Call<LoginResponse> login(@Body LoginRequest body);

    @POST("signup")
    Call<CommonResponse> signup(@Body SignupRequest body);

    @POST("verify_email")
    Call<CommonResponse> verifyEmail(@Body VerifyEmailRequest body);

    @POST("resend_otp")
    Call<CommonResponse> resendOtp(@Body ResentOtpRequest body);

    @POST("loggedout")
    Call<CommonResponse> logout(@Body CommonRequest body);

    @POST("delete_account")
    Call<CommonResponse> deleteAccount(@Body CommonRequest body);

    @POST("get-timeline-by-user-uuid")
    Call<EventResponse> getTimelines(@Body CommonRequest body);

    @POST("get-friend-list")
    Call<GetFriendListResponse> getFriends(@Body CommonRequest body);

    @POST("get-friend-request")
    Call<GetFriendRequestResponse> getFriendRequest(@Body CommonRequest body);

    @POST("suggestion-list")
    Call<GetFriendSuggetionResponse> getFriendSuggetion(@Body CommonRequest body);

    @POST("get-sent-friend-request")
    Call<GetSentFriendRequestResponse> getSentFriendRequest(@Body CommonRequest body);

    @POST("send-friend-request")
    Call<CommonResponse> sendFriendRequest(@Body SendRequest body);

    @POST("undo-sent-friend-request")
    Call<CommonResponse> undoFriendRequest(@Body SendRequest body);

    @POST("friend-requests")
    Call<CommonResponse> accept_and_decline(@Body Accept_and_DeclineRequest body);

    @POST("get-activity-by-user-uuid")
    Call<GetMyActivityResponse> getMyActivities(@Body CommonRequest body);

    @POST("delete-activity")
    Call<CommonResponse> deleteActivity(@Body DeleteActivityRequest body);

    @POST("get-profile")
    Call<GetProfileResponse> getProfile(@Body GetProfileRequest body);

    @POST("get-joining-activity")
    Call<GetActivityJoingingResponse> getAttendies(@Body GetAttendiesRequest body);

    @POST("get-respond-by-activity-uuid")
    Call<GetRespondResponse> getResponds(@Body GetRespondRequest body);

    @POST("create-respond")
    Call<CommonResponse> createRespond(@Body CreateRespondRequest body);

    @POST("get-activity-by-activity-uuid")
    Call<GetActivityDetailsResponse> getActvityDetails(@Body GetActivityDetailsRequest body);

    @POST("joining-activity")
    Call<JoinActivityResponse> joinActivity(@Body JoinActivityRequest body);


    @Multipart
   @POST("upload-profile-pic")
    Call<UploadImage>uploadProfileImage(@Part MultipartBody.Part file, @Part("user_uuid") RequestBody user_uuid);


    @Multipart
    @POST("create-activity")
    Call<UploadImage>createPost(@Part MultipartBody.Part file,
                                @Part("activity_name") RequestBody activity_name,
                                @Part("activity_description") RequestBody activity_description,
                                @Part("no_of_files") RequestBody no_of_files,
                                @Part("category_name") RequestBody category_name,
                                @Part("sub_category") RequestBody sub_category,
                                @Part("user_uuid") RequestBody user_uuid,
                                @Part("location") RequestBody location,
                                @Part("start_date_and_time") RequestBody start_date_and_time,
                                @Part("end_date_and_time") RequestBody end_date_and_time);

    @Multipart
    @POST("create-activity")
    Call<UploadImage>createPostImages(@Part List<MultipartBody.Part> files,
                                @Part("activity_name") RequestBody activity_name,
                                @Part("activity_description") RequestBody activity_description,
                                @Part("no_of_files") RequestBody no_of_files,
                                @Part("category_name") RequestBody category_name,
                                @Part("sub_category") RequestBody sub_category,
                                @Part("user_uuid") RequestBody user_uuid,
                                @Part("location") RequestBody location,
                                @Part("start_date_and_time") RequestBody start_date_and_time,
                                @Part("end_date_and_time") RequestBody end_date_and_time);
}
