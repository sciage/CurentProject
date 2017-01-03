package in.voiceme.app.voiceme.PostsDetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.voiceme.app.voiceme.R;

/**
 * Created by harish on 1/3/2017.
 */

public class UserCardViewHolder extends RecyclerView.ViewHolder {
    TextView personName;
    ImageView personPhoto;

    protected UserListModel dataItem;

    public UserCardViewHolder(View itemView) {
        super(itemView);
        personName = (TextView) itemView.findViewById(R.id.person_name);
        personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);

        personName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameClicked(view);
            }
        });

        personPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfileClicked(view);
            }
        });

    }

    protected void userNameClicked(View view){

    }

    protected void userProfileClicked(View view){

    }


    public void bind(UserListModel dataItem) {
        this.dataItem = dataItem;

        personName.setText(dataItem.getName());

        personPhoto.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
    }
}
