package com.uactiv.utils;

import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by pratikb on 15-05-2017.
 */
public class EndlessRecyclerViewNew extends RecyclerView {

    private final Handler handler = new Handler();
    private final Runnable notifyDataSetChangedRunnable = new Runnable() {
        @Override
        public void run() {
            adapterWrapper.notifyDataSetChanged();
        }
    };

    private EndlessScrollListener endlessScrollListener;
    private LayoutManagerWrapper layoutManagerWrapper;
    private AdapterWrapper adapterWrapper;
    private View progressView;
    private boolean refreshing;
    private int threshold = 1;

    public EndlessRecyclerViewNew(Context context) {
        this(context, null);
    }

    public EndlessRecyclerViewNew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EndlessRecyclerViewNew(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        //noinspection unchecked
        adapterWrapper = new AdapterWrapper(adapter);
        super.setAdapter(adapterWrapper);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        // if FLING_SPEED_FACTOR between [0, 1[ => slowdown
        velocityY *= 0.5;
        Log.d("inside fling","fling");

        return super.fling(velocityX, velocityY);
    }

    @Override
    public Adapter getAdapter() {
        return adapterWrapper.getAdapter();
    }

    /**
     * @param layout instances of {@link LinearLayoutManager} only
     */
    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        layoutManagerWrapper = layout == null ? null : new LayoutManagerWrapper(layout);
        super.setLayoutManager(layout);
    }

    /**
     * Sets {@link Pager} to use with the view.
     *
     * @param pager pager to set or {@code null} to clear current pager
     */
    public void setPager(Pager pager) {
        if (pager != null) {
            endlessScrollListener = new EndlessScrollListener(pager);
            endlessScrollListener.setThreshold(threshold);
            addOnScrollListener(endlessScrollListener);
        } else if (endlessScrollListener != null) {
            removeOnScrollListener(endlessScrollListener);
            endlessScrollListener = null;
        }
    }

    /**
     * Sets threshold to use. Only positive numbers are allowed. This value is used to determine if
     * loading should start when user scrolls the view down. Default value is 1.
     *
     * @param threshold positive number
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
        if (endlessScrollListener != null) {
            endlessScrollListener.setThreshold(threshold);
        }
    }

    /**
     * Sets progress view to show on the bottom of the list when loading starts.
     *
     * @param layoutResId layout resource ID
     */
    public void setProgressView(int layoutResId) {
        setProgressView(LayoutInflater
                .from(getContext())
                .inflate(layoutResId, this, false));
    }

    /**
     * Sets progress view to show on the bottom of the list when loading starts.
     *
     * @param view the view
     */
    public void setProgressView(View view) {
        progressView = view;
    }

    /**
     * If async operation completed you may want to call this method to hide progress view.
     *
     * @param refreshing {@code true} if list is currently refreshing, {@code false} otherwise
     */
    public void setRefreshing(boolean refreshing) {
        if (this.refreshing == refreshing) {
            return;
        }
        this.refreshing = refreshing;
        notifyDataSetChanged();
    }

    /**
     * @return {@code true} if progress view is shown
     */
    public boolean isRefreshing() {
        return refreshing;
    }

    private void notifyDataSetChanged() {
        if (isComputingLayout()) {
            handler.post(notifyDataSetChangedRunnable);
        } else {
            adapterWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void smoothScrollToPosition(int position) {

        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(getContext()) {
                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return this.computeScrollVectorForPosition(targetPosition);
                    }
                };
        linearSmoothScroller.setTargetPosition(position);

        super.smoothScrollToPosition(position);
    }

    private static final class LayoutManagerWrapper {

        @NonNull
        final LayoutManager layoutManager;

        @NonNull
        private final LayoutManagerResolver resolver;

        public LayoutManagerWrapper(@NonNull LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            this.resolver = getResolver(layoutManager);
        }


        @NonNull
        private static LayoutManagerResolver getResolver(@NonNull LayoutManager layoutManager) {
            if (layoutManager instanceof LinearLayoutManagerWithSmoothScroller) {
                return new LayoutManagerResolver() {
                    @Override
                    public int findLastVisibleItemPosition(@NonNull LayoutManager layoutManager) {
                        return ((LinearLayoutManagerWithSmoothScroller) layoutManager).findLastVisibleItemPosition();
                    }
                };
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                return new LayoutManagerResolver() {
                    @Override
                    public int findLastVisibleItemPosition(@NonNull LayoutManager layoutManager) {
                        int[] lastVisibleItemPositions =
                                ((StaggeredGridLayoutManager) layoutManager)
                                        .findLastVisibleItemPositions(null);
                        int lastVisibleItemPosition = lastVisibleItemPositions[0];
                        for (int i = 1; i < lastVisibleItemPositions.length; ++i) {
                            if (lastVisibleItemPosition < lastVisibleItemPositions[i]) {
                                lastVisibleItemPosition = lastVisibleItemPositions[i];
                            }
                        }
                        return lastVisibleItemPosition;
                    }
                };
            } else {
                throw new IllegalArgumentException("unsupported layout manager: " + layoutManager);
            }
        }

        public int findLastVisibleItemPosition() {
            return resolver.findLastVisibleItemPosition(layoutManager);
        }

        private interface LayoutManagerResolver {
            int findLastVisibleItemPosition(@NonNull LayoutManager layoutManager);
        }
    }

    private final class EndlessScrollListener extends OnScrollListener {

        private final Pager pager;

        private int threshold = 1000;

        public EndlessScrollListener(Pager pager) {
            if (pager == null) {
                throw new NullPointerException("pager is null");
            }
            this.pager = pager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int lastVisibleItemPosition = layoutManagerWrapper.findLastVisibleItemPosition();
            int lastItemPosition = getAdapter().getItemCount();

            if (pager.shouldLoad() && lastItemPosition - lastVisibleItemPosition <= threshold) {
                setRefreshing(true);
                pager.loadNextPage();
            }
        }

        public void setThreshold(int threshold) {
            if (threshold <= 0) {
                throw new IllegalArgumentException("illegal threshold: " + threshold);
            }
            this.threshold = threshold;
        }
    }

    private final class AdapterWrapper extends Adapter<ViewHolder> {

        private static final int PROGRESS_VIEW_TYPE = -1;

        private final Adapter<ViewHolder> adapter;

        private ProgressViewHolder progressViewHolder;

        public AdapterWrapper(Adapter<ViewHolder> adapter) {
            if (adapter == null) {
                throw new NullPointerException("adapter is null");
            }
            this.adapter = adapter;
            setHasStableIds(adapter.hasStableIds());
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount() + (refreshing && progressView != null ? 1 : 0);
        }

        @Override
        public long getItemId(int position) {
            return position == adapter.getItemCount() ? NO_ID : adapter.getItemId(position);
        }

        @Override
        public int getItemViewType(int position) {
            return refreshing & position == adapter.getItemCount() ? PROGRESS_VIEW_TYPE :
                    adapter.getItemViewType(position);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position < adapter.getItemCount()) {
                adapter.onBindViewHolder(holder, position);
            }
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position,List<Object> payloads) {
            if (position < adapter.getItemCount()) {
                adapter.onBindViewHolder(holder, position,payloads);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return viewType == PROGRESS_VIEW_TYPE ? progressViewHolder = new ProgressViewHolder() :
                    adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            adapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public boolean onFailedToRecycleView(ViewHolder holder) {
            return holder == progressViewHolder || adapter.onFailedToRecycleView(holder);
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            if (holder == progressViewHolder) {
                return;
            }
            adapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            if (holder == progressViewHolder) {
                return;
            }
            adapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder == progressViewHolder) {
                return;
            }
            adapter.onViewRecycled(holder);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            super.registerAdapterDataObserver(observer);
            adapter.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            super.unregisterAdapterDataObserver(observer);
            adapter.unregisterAdapterDataObserver(observer);
        }

        public Adapter<ViewHolder> getAdapter() {
            return adapter;
        }

        private final class ProgressViewHolder extends ViewHolder {
            public ProgressViewHolder() {
                super(progressView);
            }
        }
    }

    /**
     * Pager interface.
     */
    public interface Pager {
        /**
         * @return {@code true} if pager should load new page
         */
        boolean shouldLoad();

        /**
         * Starts loading operation.
         */
        void loadNextPage();
    }
}