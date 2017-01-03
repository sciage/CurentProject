package in.voiceme.app.voiceme.PostsDetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 1/3/2017.
 */

public class UserSuperList implements Parcelable {

    public static final Creator<UserSuperList> CREATOR = new Creator<UserSuperList>() {
        @Override
        public UserSuperList createFromParcel(Parcel in) {
            return new UserSuperList(in);
        }

        @Override
        public UserSuperList[] newArray(int size) {
            return new UserSuperList[size];
        }
    };

    @SerializedName("likes") private List<UserListModel> likes = null;
    @SerializedName("hug") private List<UserListModel> hugs = null;
    @SerializedName("same") private List<UserListModel> same = null;
    @SerializedName("listen") private List<UserListModel> listen = null;

    public List<UserListModel> getLikes() {
        return likes;
    }

    public List<UserListModel> getHugs() {
        return hugs;
    }

    public List<UserListModel> getSame() {
        return same;
    }

    public List<UserListModel> getListen() {
        return listen;
    }

    protected UserSuperList(Parcel in) {
        likes = in.createTypedArrayList(UserListModel.CREATOR);
        hugs = in.createTypedArrayList(UserListModel.CREATOR);
        same = in.createTypedArrayList(UserListModel.CREATOR);
        listen = in.createTypedArrayList(UserListModel.CREATOR);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(likes);
        parcel.writeTypedList(hugs);
        parcel.writeTypedList(same);
        parcel.writeTypedList(listen);
    }
}
