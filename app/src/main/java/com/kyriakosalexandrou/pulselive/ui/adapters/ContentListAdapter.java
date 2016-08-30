package com.kyriakosalexandrou.pulselive.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyriakosalexandrou.pulselive.App;
import com.kyriakosalexandrou.pulselive.R;
import com.kyriakosalexandrou.pulselive.models.ContentListItem;

import java.util.ArrayList;
import java.util.List;

public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.ContentListViewHolder> {
    private ArrayList<ContentListItem> mItems = new ArrayList<>();
    private ContentListAdapterCallback mContentListAdapterCallback;

    public ContentListAdapter(ContentListAdapterCallback contentListAdapterCallback) {
        mContentListAdapterCallback = contentListAdapterCallback;
    }

    @Override
    public ContentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_list, null);
        return new ContentListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContentListViewHolder holder, final int position) {
        ContentListItem item = mItems.get(position);
        updateViewsContent(holder, item);
        setListeners(holder, item, position);
    }

    private void updateViewsContent(final ContentListViewHolder holder, final ContentListItem item) {
        holder.mId.setText(App.getContext().getResources().getString(R.string.single_number, item.getId()));
        holder.mTitle.setText(item.getTitle());
        holder.mSubtitle.setText(item.getSubtitle());
        holder.mDate.setText(item.getDate());
    }

    private void setListeners(final ContentListViewHolder holder, final ContentListItem item, final int position) {
        holder.mMainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentListAdapterCallback.onContentListItemClicked(position, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ContentListItem> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public static class ContentListViewHolder extends RecyclerView.ViewHolder {
        public View mMainContainer;
        public TextView mId;
        public TextView mTitle;
        public TextView mSubtitle;
        public TextView mDate;

        public ContentListViewHolder(View itemView) {
            super(itemView);
            mMainContainer = itemView.findViewById(R.id.main_container);
            mId = (TextView) itemView.findViewById(R.id.id_value);
            mTitle = (TextView) itemView.findViewById(R.id.title_value);
            mSubtitle = (TextView) itemView.findViewById(R.id.subtitle_value);
            mDate = (TextView) itemView.findViewById(R.id.date_value);
        }
    }

    public interface ContentListAdapterCallback {
        void onContentListItemClicked(int position, ContentListItem item);
    }
}
