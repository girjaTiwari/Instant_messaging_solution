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



import org.json.JSONObject;

import java.util.List;

import im.vector.app.timeshare.home.model.Event;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitAPI {


    @POST("loggedin")
    Call<LoginResponse> postJson(@Body LoginRequest body);

    @POST("get-timeline-by-user-uuid")
    Call<EventResponse> postJson(@Body TimeLineRequest body);


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
