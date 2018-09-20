package com.example.watchtube;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watchtube.UI.ChannelVideoListFragment;
import com.example.watchtube.model.data.ChannelVideoPreviewData;

import java.util.ArrayList;

/**
 * Created by Nikita on 06.09.2018.
 */

public class ChannelVideoListCustomAdapter extends RecyclerView.Adapter<ChannelVideoListCustomAdapter.ViewHolder> {

    private ArrayList<ChannelVideoPreviewData> mList;
    private ChannelVideoListFragment mFragment;

    public ChannelVideoListCustomAdapter(ChannelVideoListFragment fragment){
        mList = new ArrayList<ChannelVideoPreviewData>();
        mFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_channel_video_list_preview_item, parent, false);
        return new ChannelVideoListCustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewVideoTitle.setText(mList.get(position).videoTitle);
        holder.imageView.setImageDrawable(mList.get(position).videoImage);
        holder.textViewPublishedAt.setText(mList.get(position).publishedAt);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*ChannelVideoListOfPlaylistFragment fragment = new ChannelVideoListOfPlaylistFragment();
                fragment.setCredentials(mCredential);
                fragment.setPlaylistId(mList.get(position).playlistId);
                fragment.fetchVideoListData();*/

            }
        });
        Log.d("Queue", "= " + position);
        if(position == mList.size() - 3){
            mFragment.fetchVideoListData();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clearList(){
        mList.clear();
    }

    public void addVideosToList(ArrayList<ChannelVideoPreviewData> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewVideoTitle;
        ImageView imageView;
        TextView textViewPublishedAt;
        public ViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewPlaylist);
            textViewVideoTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewPublishedAt = (TextView) itemView.findViewById(R.id.textViewPublishedAt);
        }
    }

}
