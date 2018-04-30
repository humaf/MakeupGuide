package strawbericreations.com.makeupguide.userinterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import strawbericreations.com.makeupguide.R;


public class VideoFragment extends Fragment {

    @BindView(R.id.recycler_recipe)
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String strtext = getArguments().getString("searchkey");
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_video, container, false);
    }

}
