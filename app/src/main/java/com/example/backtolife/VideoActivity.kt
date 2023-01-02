package com.example.backtolife
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.R
import com.example.backtolife.models.Videos

import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity: AppCompatActivity()  {
    private var TAG = "VideoPlayer"
    var mediaController: MediaController? = null
    lateinit var videos: Videos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videos = intent.getSerializableExtra("videos") as Videos

        textVideoType.text = "${videos.title}"
        configureVideoView()
    }
    private fun configureVideoView() {

        videoView.setVideoPath(videos.url)
        mediaController = MediaController(this)

        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            Log.i(TAG, "Duration = " + videoView.duration)
        }
        videoView.start()
    }

    override fun onPause() {
        super.onPause()
        videoView.stopPlayback()
    }
}