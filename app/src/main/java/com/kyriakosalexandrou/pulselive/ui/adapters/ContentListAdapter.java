package com.kyriakosalexandrou.pulselive.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyriakosalexandrou.pulselive.App;
import com.kyriakosalexandrou.pulselive.R;
import com.kyriakosalexandrou.pulselive.models.BasicItem;

import java.util.ArrayList;
import java.util.List;

public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.ContentListViewHolder> {
    private List<BasicItem> mBasicItems = new ArrayList<>();
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
        BasicItem basicItem = mBasicItems.get(position);
        updateViewsContent(holder, basicItem);
        setListeners(holder, basicItem, position);
    }

    private void updateViewsContent(final ContentListViewHolder holder, final BasicItem basicItem) {
        holder.mId.setText(App.getContext().getResources().getString(R.string.single_number, basicItem.getId()));
        holder.mTitle.setText(basicItem.getTitle());
        holder.mSubtitle.setText(basicItem.getSubtitle());
        holder.mDate.setText(basicItem.getDate());
    }

    private void setListeners(final ContentListViewHolder holder, final BasicItem basicItem, final int position) {
        holder.mMainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentListAdapterCallback.onContentListItemClicked(basicItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBasicItems.size();
    }

    public void clear() {
        mBasicItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<BasicItem> basicItems) {
        mBasicItems.addAll(basicItems);
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
        void onContentListItemClicked(BasicItem basicItem);
    }
}
