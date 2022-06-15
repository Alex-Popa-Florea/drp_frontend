package ic.ac.drp02;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GridRecyclerAdapter extends RecyclerView.Adapter<GridRecyclerAdapter.MyViewHolder> {

    ArrayList<WardrobeItem> wardrobeItems;
    Context context;
    Fragment fragment;
    public GridRecyclerAdapter(Context context, ArrayList<WardrobeItem> wardrobeItems, Fragment fragment) {
        this.context = context;
        this.wardrobeItems = wardrobeItems;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wardrobe_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }




    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // set the data in items
        Picasso.get().load(wardrobeItems.get(position).getImageUrl()).into(holder.image);
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click

                Bundle bundle = new Bundle();
                bundle.putString("image", wardrobeItems.get(position).getImageUrl());
                bundle.putString("description", wardrobeItems.get(position).getDescription());
                bundle.putString("tags", wardrobeItems.get(position).getTagString());


                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_profileFragment_to_itemDetails, bundle);
            }
        });
    }
    @Override
    public int getItemCount() {
        return wardrobeItems.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}