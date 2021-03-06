package in.voiceme.app.voiceme.services;

import android.net.Uri;

import java.util.List;

import in.voiceme.app.voiceme.PostsDetails.UserSuperList;
import in.voiceme.app.voiceme.ProfilePage.FollowerUserList;
import in.voiceme.app.voiceme.ProfilePage.ProfileUserList;
import in.voiceme.app.voiceme.contactPage.AddContactResponse;
import in.voiceme.app.voiceme.login.LoginResponse;
import in.voiceme.app.voiceme.userpost.BaseResponse;
import in.voiceme.app.voiceme.userpost.Response;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface WebService {
    @GET("posts.php")
    Observable<List<PostsModel>> getLatestFeed();

    // Todo donot know about this call
    @GET("posts.php")
    Observable<List<PostsModel>> getFollowers(@Query("follower") String user_id);

    @GET("posts.php")
    Observable<List<PostsModel>> getPopulars(@Query("popular") String booleann);

    @GET("posts.php")
    Observable<List<PostsModel>> getTrending(@Query("trending") String booleann);

    @FormUrlEncoded
    @POST("likes.php")
    Observable<LikesResponse> likes(@Field("user_id") String userId,
                                    @Field("post_id") String postId,
                                    @Field("like") int like,
                                    @Field("hug") int hug,
                                    @Field("same") int same,
                                    @Field("listen") int listen);

    @GET("unlikes.php")
    Observable<LikesResponse> unlikes(@Query("user_id") int userId, @Query("post_id") String postId,
                                    @Query("like") int like,
                                    @Query("hug") int hug,
                                    @Query("same") int same,
                                    @Query("listen") int listen);

    @FormUrlEncoded
    @POST("login_new.php")
    Observable<LoginResponse> login(
            @Field("name") String name,
            @Field("email") String email,
            @Field("location") String location,
            @Field("dob") String dateOfBirth,
            @Field("user_id") String userId,
            @Field("profile") Uri profile,
            @Field("gender") String gender
    );

    @FormUrlEncoded
    @POST("postStatus.php")
    Observable<Response> postStatus(
            @Field("user_id") String user_id,
            @Field("post_text") String postStatus,
            @Field("cat_id") String cat_id,
            @Field("feeling_id") String feeling_id,
            @Field("audio") String audio
    );

    @GET("posts.php")
    Observable<List<PostsModel>> getEmotionPosts(@Query("feeling_id") String feeling_id);

    @GET("posts.php")
    Observable<List<PostsModel>> getCategoryPosts(@Query("category_id") String category_id);

    @GET("get_likers.php")
    Observable<UserSuperList> getInteractionPosts(
            @Query("id_posts") String id_posts );

    @FormUrlEncoded
    @POST("register_mobile.php")
    Observable<BaseResponse> registerMobile(
            @Field("id_user_name") String user_id,
            @Field("phone_number") String phone_number
    );

    // Todo adding all contacts mobile left
    @FormUrlEncoded
    @POST("register_user_contacts.php")
    Observable<AddContactResponse> addAllContacts(
            @Field("id_user_name") String user_id,
            @Field("contacts") String contacts
    );

    // Todo adding follower mobile left. Action = add or Action = remove
    @FormUrlEncoded
    @POST("follower.php")
    Observable<Response> addFollower(
            @Field("user_id") String user_id,
            @Field("follower_id") String follower_id,
            @Field("action") String action
    );


    // Todo get list of users who follow the user. meaning I am following these users
    @GET("follower.php")
    Observable<FollowerUserList> getUserFollow(@Query("feeling_id") String feeling_id);

    // Todo get list of users who the user follows. meaning users following me
    @GET("follower.php")
    Observable<FollowerUserList> getUserFollowing(@Query("feeling_id") String feeling_id);


    // Todo show complete User Profile Details
    @GET("get_user.php")
    Observable<ProfileUserList> getUserProfile(@Query("user_id") String user_id);


    @Headers("Accept: multipart/form-data")
    @Multipart
    @POST("audio_upload/index.php")
    Observable<String> uploadFile(@Part MultipartBody.Part file);




}
