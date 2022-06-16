package ic.ac.drp02;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

import ic.ac.drp02.databinding.FeedBinding;


public class FeedFragment extends Fragment {
    private FeedBinding binding;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FeedBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = (RecyclerView) binding.getRoot().findViewById(R.id.feed_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        WardrobeItem item1 = new WardrobeItem("https://lp.stories.com/app005prod?set=source[/39/bf/39bf9cb4d4f277a63570377db257604d8ecb0950.jpg],origin[dam],type[DESCRIPTIVESTILLLIFE],device[hdpi],quality[80],ImageVersion[1]&call=url[file:/product/main]", "Blue jumper", Arrays.asList("woolen", "blue"));
        WardrobeItem item2 = new WardrobeItem("https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2Fba%2F9d%2Fba9d9c48e4847ae9e18b3ed23d10878f6c758e38.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]", "Blue jeans", Arrays.asList("jeans", "lightwash"));
        WardrobeItem item3 = new WardrobeItem("https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2Fba%2F9d%2Fba9d9c48e4847ae9e18b3ed23d10878f6c758e38.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]", "Blue jeans", Arrays.asList("jeans", "lightwash"));
        ArrayList<WardrobeItem> wardrobeItems = new ArrayList<>(Arrays.asList(item1, item2, item3));

        FeedRecyclerAdapter adapter = new FeedRecyclerAdapter(getActivity().getApplicationContext(), wardrobeItems, this);

        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        Log.e("adhithi", "lkfjklfj");
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        Log.e("adhithi", "bottomNavigationView.toString()");
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.profile) {
//                NavHostFragment.findNavController(FeedFragment.this)
//                        .navigate(R.id.action_feedFragment_to_profileFragment);
//                bottomNavigationView.setSelectedItemId(R.id.profile);
//                return true;
//            }
//            return false;
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    }
