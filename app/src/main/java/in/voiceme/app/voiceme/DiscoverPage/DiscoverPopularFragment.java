package in.voiceme.app.voiceme.DiscoverPage;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.like.LikeButton;

import java.util.List;

import in.voiceme.app.voiceme.R;
import in.voiceme.app.voiceme.infrastructure.BaseFragment;
import in.voiceme.app.voiceme.infrastructure.BaseSubscriber;
import in.voiceme.app.voiceme.infrastructure.VoicemeApplication;
import in.voiceme.app.voiceme.services.PostsModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * A simple {@link BaseFragment} subclass.
 */
public class DiscoverPopularFragment extends BaseFragment {
    public static final String ARG_POPULAR_PAGE = "ARG_YOUR_FEED_PAGE";

    private int mPage;
    private RecyclerView recyclerView;
    private PopularListAdapter popularListAdapter;

    public DiscoverPopularFragment() {
        // Required empty public constructor
    }

    public static DiscoverPopularFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_POPULAR_PAGE, page);
        DiscoverPopularFragment fragment2 = new DiscoverPopularFragment();
        fragment2.setArguments(args);
        return fragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_POPULAR_PAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover_popular, container, false);
        try {
            initUiView(view);
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void initUiView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_discover_popular_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    @Override
    public String toString() {
        return "documentary";
    }


    private void getData() throws Exception {
        ((VoicemeApplication) getActivity().getApplication()).getWebService()
                .getPopulars("true")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<PostsModel>>() {
                    @Override
                    public void onNext(List<PostsModel> response) {
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        showRecycleWithDataFilled(response);
                    }
                });
    }


    private void showRecycleWithDataFilled(final List<PostsModel> myList) {
        popularListAdapter = new PopularListAdapter(myList, getActivity());
        popularListAdapter.setOnItemClickListener(new LikeUnlikeClickListener() {
            @Override
            public void onItemClick(PostsModel model, View v) {
                String name = model.getIdUserName();
            }

            @Override
            public void onLikeUnlikeClick(PostsModel model, LikeButton v) {

            }
        });
        recyclerView.setAdapter(popularListAdapter);
    }
}
