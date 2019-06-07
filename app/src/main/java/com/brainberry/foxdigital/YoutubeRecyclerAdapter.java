package com.brainberry.foxdigital;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Abhinav Agarwal
 * @author Shayak Banerjee
 * @version 0.2
 * @since 6th June '19
 */
public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<YoutubeVideo> mYoutubeVideos;
    private DisplayMetrics displayMetrics = new DisplayMetrics();


    /**
     * Constructor
     * @param context The Activity in which the recycler view exists
     * @param youtubeVideos list of YouTube videos
     */
    public YoutubeRecyclerAdapter(List<YoutubeVideo> youtubeVideos, Context context ) {
        this.mYoutubeVideos = youtubeVideos;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mYoutubeVideos != null && mYoutubeVideos.size() > 0) {
            return mYoutubeVideos.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.textViewTitle)
        TextView textWaveTitle;
        @BindView(R.id.btnPlay)
        ImageView playButton;
        @BindView(R.id.imageViewItem)
        ImageView imageViewItems;
        @BindView(R.id.youtube_view)
        YouTubePlayerView youTubePlayerView;
        @BindView(R.id.post_description)
        TextView textViewPostDescription;
        @BindView(R.id.clickableTextViewMore)
        TextView clickableTextViewMore;
        @BindView(R.id.textViewLink1)
        TextView textViewLink1;
        @BindView(R.id.link2)
        TextView textViewLink2;
        @BindView(R.id.textViewOfferLink)
        TextView textViewOfferLink;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            final YoutubeVideo mYoutubeVideo = mYoutubeVideos.get(position);
            
            ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            if (mYoutubeVideo.getTitle() != null)
                textWaveTitle.setText(mYoutubeVideo.getTitle());

            if (mYoutubeVideo.getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(mYoutubeVideo.getImageUrl()).
                        apply(new RequestOptions().override(width - 36, 200))
                        .into(imageViewItems);
            }
            imageViewItems.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);
            youTubePlayerView.setVisibility(View.GONE);

            playButton.setOnClickListener(view -> {
                imageViewItems.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.GONE);
                youTubePlayerView.initialize(initializedYouTubePlayer -> initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        String id = mYoutubeVideo.getVideoId();
                        initializedYouTubePlayer.loadVideo(mYoutubeVideo.getVideoId(), 0);
                    }
                }), true);
            });

            String description = mYoutubeVideo.getDescription();

            /*
             * The description length is dynamically shortened when the number of characters is more than
             * 150. '...' is added to the end of the shortened description. On clicking 'more' a dialog
             * displays the complete description.
             */
            if (description.length() > 150) {
                String shortenedDescription = description.substring(0, 140);
                shortenedDescription += "...";

                textViewPostDescription.setText(shortenedDescription);

                // enabling and implementing the action of more button
                clickableTextViewMore.setVisibility(View.VISIBLE);
                clickableTextViewMore.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setMessage(description)
                            .setTitle("Description")
                            .setCancelable(true)
                            .setNegativeButton(
                                    "OK", (dialog, id) -> {
                                        // do nothing, the alert dialog automatically closes
                                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                });
            } else { // description.length() <= 150
                textViewPostDescription.setText(description);
            }

            /*
             * The offer editTextVideoLink, link1 and link2 lengths are dynamically shortened when the number of characters is more
             * than 33. Also, '...' is added to the end of the shortened editTextVideoLink.
             */
            String offerLink = mYoutubeVideo.getOfferLink();
            String link1 = mYoutubeVideo.getLink1();
            String link2 = mYoutubeVideo.getLink2();

            if(offerLink.length() > 33){
                offerLink = offerLink.substring(0, 29) + "...";
            }
            if(link1.length() > 33){
                link1 = link1.substring(0, 29) + "...";
            }
            if(link2.length() > 33){
                link2 = link2.substring(0, 29) + "...";
            }

            textViewOfferLink.setText(offerLink);
            textViewLink2.setText(link2);
            textViewLink1.setText(link1);

            //textViewLink1 is a clickable textView, which will launch link1 in the installed browser
            textViewLink1.setOnClickListener(v -> {
                Intent openUrl = new Intent(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(mYoutubeVideo.getLink1()));
                context.startActivity(openUrl);
            });

            //textViewLink2 is a clickable textView, which will launch link1 in the installed browser
            textViewLink2.setOnClickListener(v -> {
                Intent openUrl = new Intent(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(mYoutubeVideo.getLink2()));
                context.startActivity(openUrl);
            });

            // textViewOfferLink is a clickable textView, which will launch link1 in the installed browser
            textViewOfferLink.setOnClickListener(v -> {
                Intent openUrl = new Intent(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(mYoutubeVideo.getOfferLink()));
                context.startActivity(openUrl);
            });
        }
    }

}
