package com.soundsofpolaris.timeline.gui;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface StickyRecyclerHeadersAdapter<VH extends RecyclerView.ViewHolder> {
    public long getHeaderId(int position);

    public VH onCreateHeaderViewHolder(ViewGroup parent);

    public void onBindHeaderViewHolder(VH holder, int position);

    public int getItemCount();
}
