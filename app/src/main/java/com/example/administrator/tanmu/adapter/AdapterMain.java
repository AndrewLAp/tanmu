package com.example.administrator.tanmu.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.view.MyRecyclerView;

import java.util.List;

/**
 * Created by andrew on 2017/8/9.
 */

public class AdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int typeScollView = 0;
    private int typeYingyong = 1;
    private int typeTheme = 2;
    private int typeVideo = 3;
    private int typeNews = 4;
    private AdapterScrollItem adapterScrollItem;
    private LinearLayoutManager layoutManager;


    private List<Integer> mdatas;
    private int[] mList;

    private Context mContext;

    public AdapterMain(List<Integer> mdatas, Context mContext) {
        super();
        this.mdatas = mdatas;
        this.mContext = mContext;
        mList = new int[]{R.drawable.scroll_1, R.drawable.scroll_2, R.drawable.scroll_3, R.drawable.scroll_4};

        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapterScrollItem = new AdapterScrollItem(mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == typeScollView) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scroll_item, parent, false);
            return new ScollViewHolder(view);
        } else if (viewType == typeYingyong) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_yingyong, parent, false);
            return new YingyongHolder(view);
        } else if (viewType == typeTheme) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_head, parent, false);
            return new HeadViewHolder(view);
        } else if (viewType == typeVideo) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
            return new VideoViewHolder(view);
        } else if (viewType == typeNews) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_text_layout, parent, false);
            return new NewsTextHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ScollViewHolder) {


        } else if (holder instanceof YingyongHolder) {


        } else if (holder instanceof HeadViewHolder) {
            if (position == 3) {
                ((HeadViewHolder) holder).headTitle.setText("热门影视");
            }
            if (position == 8) {
                ((HeadViewHolder) holder).headTitle.setText("今日资讯");
            }

        } else if (holder instanceof VideoViewHolder) {
            switch (position) {
                case 4:
                    ((VideoViewHolder) holder).videologo.setImageResource(R.drawable.remen1);
                    ((VideoViewHolder) holder).videovname.setText("战狼2");
                    break;
                case 5:
                    ((VideoViewHolder) holder).videologo.setImageResource(R.drawable.remen2);
                    ((VideoViewHolder) holder).videovname.setText("秦时丽人明月心");
                    break;
                case 6:
                    ((VideoViewHolder) holder).videologo.setImageResource(R.drawable.remen3);
                    ((VideoViewHolder) holder).videovname.setText("灵域  第五季");
                    break;
                case 7:
                    ((VideoViewHolder) holder).videologo.setImageResource(R.drawable.remen4);
                    ((VideoViewHolder) holder).videovname.setText("中国新歌声  第二季");
                    break;
                case 10:
                    ((VideoViewHolder) holder).videologo.setImageResource(R.drawable.news_image1);
                    ((VideoViewHolder) holder).videovname.setText("男孩浸泡废机油桶玩憋气");
                    ((VideoViewHolder) holder).video_content.setVisibility(View.VISIBLE);
                    ((VideoViewHolder) holder).video_content.setText("为赚打赌的1000元");
                    break;
                case 11:
                    ((VideoViewHolder) holder).videologo.setImageResource(R.drawable.news_image2);
                    ((VideoViewHolder) holder).videovname.setText("广东拟探索共有产权房制度");
                    ((VideoViewHolder) holder).video_content.setVisibility(View.VISIBLE);
                    ((VideoViewHolder) holder).video_content.setText("今日08：30发布");
            }

        } else if (holder instanceof NewsTextHolder) {
            if (position == 9) {
                ((NewsTextHolder) holder).news1.setText("台风天鸽进广西 多地连续降雨");
                ((NewsTextHolder) holder).news2.setText("揭奶奶庙\"生意经\"：年收入千万");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return typeScollView;
        } else if (position == 1) {
            return typeYingyong;

        } else if (position == 2) {
            return typeYingyong;
        } else if (position == 3) {
            return typeTheme;
        } else if (position > 3 && position < 8) {
            return typeVideo;
        } else if (position == 8) {
            return typeTheme;
        } else if (position == 9) {
            return typeNews;
        } else {
            return typeVideo;
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case 0:
                            return gridManager.getSpanCount();
                        case 1:
                            return gridManager.getSpanCount();
                        case 2:
                            return gridManager.getSpanCount();
                        case 4:
                            return gridManager.getSpanCount();
                        default:
                            break;
                    }
                    return 3;
                }
            });
        }
    }

    public class ScollViewHolder extends RecyclerView.ViewHolder {
        private MyRecyclerView recyclerView_scroll;

        public ScollViewHolder(View itemView) {
            super(itemView);
            recyclerView_scroll = (MyRecyclerView) itemView.findViewById(R.id.recycler_scroll);
            new PagerSnapHelper().attachToRecyclerView(recyclerView_scroll);
            recyclerView_scroll.setLayoutManager(layoutManager);
            recyclerView_scroll.setAdapter(adapterScrollItem);
            if (true) //保证itemCount的总个数宽度超过屏幕宽度->自己处理
                recyclerView_scroll.start();
        }
    }

    public class YingyongHolder extends RecyclerView.ViewHolder {

        public YingyongHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        private TextView headTitle;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headTitle = (TextView) itemView.findViewById(R.id.head_title);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public ImageView videologo;
        public TextView videovname;
        public TextView video_content;


        public VideoViewHolder(View itemView) {
            super(itemView);
            videologo = (ImageView) itemView.findViewById(R.id.videologo);
            videovname = (TextView) itemView.findViewById(R.id.videoname);
            video_content = (TextView) itemView.findViewById(R.id.video_content);
        }
    }

    public class NewsTextHolder extends RecyclerView.ViewHolder {
        public TextView news1;
        public TextView news2;

        public NewsTextHolder(View itemView) {
            super(itemView);
            news1 = (TextView) itemView.findViewById(R.id.news1);
            news2 = (TextView) itemView.findViewById(R.id.news2);
        }
    }
}
