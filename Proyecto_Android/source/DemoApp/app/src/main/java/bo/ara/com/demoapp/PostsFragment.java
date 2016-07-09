package bo.ara.com.demoapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import bo.ara.com.demoapp.adapter.PostAdapter;
import bo.ara.com.demoapp.network.RequestPostsAsyncTask;
import bo.ara.com.demoapp.model.Post;

public class PostsFragment extends Fragment {

    private ListView listView;
    private List<Post> list;
    private PostAdapter postAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        //Create Adapter
        postAdapter = new PostAdapter(getActivity());//OJO
        listView = (ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(postAdapter);

        RequestPostsAsyncTask taskAsync = new RequestPostsAsyncTask(this);
        taskAsync.execute();

        //Add listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapter, View view, int position, long arg) {
                //Post post = postAdapter.getItem(position);
                //Log.d("PostsFragment", "selected post = " + post.getTitle());
                Post post = (Post)adapter.getItemAtPosition(position);
                Log.d("PostsFragment", "selected post = " + post.getTitle());

                //Toast.makeText(listView.getContext(), "Selected item = " + post.getTitle(), Toast.LENGTH_LONG).show();
                //getActivity()
                Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                intent.putExtra("UserName", post.getUser().getUsername());
                intent.putExtra("UserImageUri", post.getUser().getPicture_url());

                intent.putExtra("Title", post.getTitle());
                intent.putExtra("Content", post.getContent());
                intent.putExtra("PostedDate", post.getPosted_date());
                startActivity(intent);
            }
        });

        //listView.setonit
        //>>

        return view;
    }

    public PostAdapter getPostAdapter() {
        return postAdapter;
    }
}
