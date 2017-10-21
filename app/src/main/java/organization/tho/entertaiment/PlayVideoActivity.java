package organization.tho.entertaiment;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayVideoActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    @BindView(R.id.adView1) AdView mAdView1;
    @BindView(R.id.adView2) AdView mAdView2;

    public static final String API_KEY = "AIzaSyDBRvaEaDuSmQHEcQZrHVOOza_qJQic7NI";

    private String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);

        // Initializing YouTube Player View
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);

        // get video link from fragment
        getData();

        // init ads
        // TODO: replace app unit id
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        // loading ads
        // TODO: remove addTestDevice when publish app
        AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView1.loadAd(adRequest);
        mAdView2.loadAd(adRequest);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        // add listener to YouTubePlayer instance
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        // start buffering
        if (!wasRestored) {
            youTubePlayer.loadVideo(videoId, 0);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failured to Initialize", Toast.LENGTH_SHORT).show();
    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    /**
     * Get video link from fragment
     */
    private void getData() {
        if (getIntent() != null) {
            String videoLink = getIntent().getStringExtra("videoLink");
            videoId = videoLink.substring(videoLink.lastIndexOf("=") + 1);
        }
    }
}
