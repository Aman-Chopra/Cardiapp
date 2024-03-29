//package com.example.win_8.cardigram;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
///**
// * Provides UI for the view with List.
// */
//public class ListContentFragment extends Fragment {
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
//                R.layout.recycler_view, container, false);
//        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        return recyclerView;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView avator;
//        public TextView name;
//        public TextView description;
//        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.item_list, parent, false));
//            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
//            name = (TextView) itemView.findViewById(R.id.list_title);
//            description = (TextView) itemView.findViewById(R.id.list_desc);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
//                    context.startActivity(intent);
//                }
//            });
//        }
//    }
//    /**
//     * Adapter to display recycler view.
//     */
//    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
//        // Set numbers of List in RecyclerView.
//        private static final int LENGTH = 18;
//        private final String[] mPlaces;
//        private final String[] mPlaceDesc;
//        private final Drawable[] mPlaceAvators;
//        public ContentAdapter(Context context) {
//            Resources resources = context.getResources();
//            mPlaces = resources.getStringArray(R.array.places);
//            mPlaceDesc = resources.getStringArray(R.array.place_desc);
//            TypedArray a = resources.obtainTypedArray(R.array.place_avator);
//            mPlaceAvators = new Drawable[a.length()];
//            for (int i = 0; i < mPlaceAvators.length; i++) {
//                mPlaceAvators[i] = a.getDrawable(i);
//            }
//            a.recycle();
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.avator.setImageDrawable(mPlaceAvators[position % mPlaceAvators.length]);
//            holder.name.setText(mPlaces[position % mPlaces.length]);
//            holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
//        }
//
//        @Override
//        public int getItemCount() {
//            return LENGTH;
//        }
//    }
//}
//
//
//


package com.example.win_8.cardigram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.win_8.cardigram.R.id.fab;

/**
 * Provides UI for the view with List.
 */
public class ListContentFragment extends Fragment {
    private View myFragmentView;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view=inflater.inflate(R.layout.item_list, container, false);
        View view = inflater.inflate(R.layout.item_list, container,false);




        final Typewriter typewriter = (Typewriter)view.findViewById(R.id.type);
        typewriter.setCharacterDelay(150);
        typewriter.animateText("Hi, What can we do for you?");



        //Typewriter write = (Typewriter)textView;
        //write.setCharacterDelay(150);
        //write.animateText("Sample String");

        FloatingActionButton event = (FloatingActionButton) view.findViewById(fab);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton chat = (FloatingActionButton) view.findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Fire.class);
                startActivity(intent);
            }
        });

        FloatingActionButton download = (FloatingActionButton) view.findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageListActivity.class);
                startActivity(intent);
            }
        });





        return view;
        //myFragmentView = inflater.inflate(R.layout.item_list, container, false);
        //TextView a = getView().findViewById(R.id.textView);
        //Typewriter writer = (Typewriter)findViewById(R.id.textView);
        //Add a character every 150ms
        //writer.setCharacterDelay(150);
        //writer.animateText("Sample String");
    }




}


