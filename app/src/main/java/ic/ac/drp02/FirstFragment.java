package ic.ac.drp02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import java.util.Arrays;
import java.util.List;

import ic.ac.drp02.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        String[] mobileArray = {"Light blue skinny jeans","Gray formal trousers","White t-shirt","Black shirt",
                "Denim jacket","Stripey t-shirt","Cargo trousers","Puffer coat", "Blazer", "White trainers", "Boots", "Burgundy jumper", "Black flared jeans", "Dark blue straight-leg jeans", "Cream turtleneck jumper", "Black high neck jumper", "Gray sweatshirt", "Navy hoodie", "Long-sleeve t-shirt", "Blue t-shirt"};

        View contentView = inflater.inflate(R.layout.fragment_first, container, false);
        ListView listView = binding.getRoot().findViewById(R.id.lv1);

        CustomAdapter listAdapter = new CustomAdapter(Arrays.asList(mobileArray));
        listView.setAdapter(listAdapter);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class CustomAdapter extends BaseAdapter {
        List<String> items;

        public CustomAdapter(List<String> items) {
            super();
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return items.get(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(getContext());
            textView.setText(items.get(i));
            return textView;
        }
    }
}