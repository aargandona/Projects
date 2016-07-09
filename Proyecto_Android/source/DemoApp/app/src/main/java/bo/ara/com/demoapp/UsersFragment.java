package bo.ara.com.demoapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import bo.ara.com.demoapp.adapter.UserAdapter;
import bo.ara.com.demoapp.network.RequestUsersAsyncTask;

public class UsersFragment extends Fragment {

    private UserAdapter userAdapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        //Create Adapter
        userAdapter = new UserAdapter(getActivity());
        listView = (ListView)view.findViewById(R.id.list_users);
        listView.setAdapter(userAdapter);

        RequestUsersAsyncTask taskAsync = new RequestUsersAsyncTask(this);
        taskAsync.execute();

        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_users);
//
//    }

    public UserAdapter getUserAdapter() {
        return userAdapter;
    }
}
