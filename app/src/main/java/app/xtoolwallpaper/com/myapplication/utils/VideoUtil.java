package app.xtoolwallpaper.com.myapplication.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.VideoView;



/**
 * @author XuChuanting
 * on 2018/10/30-16:07
 */
public class VideoUtil {

    /**
     * 修正视频显示比例
     * @param mediaPlayer
     * @param videoView
     */
    public static void modifyVideoDisplaySize(MediaPlayer mediaPlayer, VideoView videoView) {
        if(mediaPlayer==null||videoView==null){
            return;
        }
        int videoHeight = mediaPlayer.getVideoHeight();
        int videoWidth = mediaPlayer.getVideoWidth();
        int displayHeight = getScreenHeight(videoView.getContext());
        int displayWidth = (int) ((float)displayHeight*videoWidth/videoHeight);
        ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
        layoutParams.width = displayWidth;
        layoutParams.height = displayHeight;
        videoView.setLayoutParams(layoutParams);
    }
    public static int getScreenHeight(Context context) {
        if (null == context)
            return 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getHeight();
    }
}
