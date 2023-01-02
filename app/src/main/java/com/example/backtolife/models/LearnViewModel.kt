package com.example.backtolife.models

import androidx.lifecycle.ViewModel
import com.example.backtolife.R

class LearnViewModel : ViewModel() {
    var videos = ArrayList<Videos>()

    fun fillData() {
        val video1: Videos = Videos("https://drive.google.com/uc?id=17uL0nsZaopniFrXWB0CXCeYk5lpdoQxh","*How To Meditate For Beginners (Animated)","How To Meditate For Beginners! In this video, I'm going to tell you, where to meditate, how to meditate, how to stop thinking, how long to meditate for, even how long before you start seeing the benefits. ", "05:36", R.drawable.meditation1)
        val video2: Videos = Videos("https://happy.videvo.net/videvo_files/video/premium/partners0211/large_watermarked/BB_baafc586-3959-4a2d-be68-e1c248e390d6_preview.mp4","Alan Watts - Guided Meditation (Awakening The Mind)","This is a 14-minute Alan Watts guided meditation video discussing his method of establishing a meditative state and reaching self-awareness.\n", "14.45", R.drawable.meditation2)
        val video3: Videos = Videos("https://happy.videvo.net/videvo_files/video/premium/partners0211/large_watermarked/BB_baafc586-3959-4a2d-be68-e1c248e390d6_preview.mp4","Meditation For Children (Calming activity)", "Meditation is a powerful practice. Our children today live in a world so full of constant stimulation and entertainment. Learning to sit still and breathe can help kids to calm themselves when they feel stressed or anxious.","06:13", R.drawable.meditation3)
        val video4: Videos = Videos("https://happy.videvo.net/videvo_files/video/premium/partners0211/large_watermarked/BB_baafc586-3959-4a2d-be68-e1c248e390d6_preview.mp4","Meditation: A Beginner's Guide", "Are you new to meditation, and interested in finding out how to start a practice? We'll walk you through the basics! \n","02:00", R.drawable.meditation4)
        val video5: Videos = Videos("https://happy.videvo.net/videvo_files/video/premium/partners0211/large_watermarked/BB_baafc586-3959-4a2d-be68-e1c248e390d6_preview.mp4","The Art of Meditation (Animated video)", "Meditation is probably a bit more complicated than most people think.\n" +
                "\n" +
                "When we meditate we watch our thoughts, while focusing our attention on a certain anchor that keeps us in the present, for example, the breath. While watching the breath we observe how our thoughts come and go, along with our feelings and emotions. And when we get dragged into them, we bring back our attention to our anchor.","06:37", R.drawable.meditation5)
        val video6: Videos = Videos("https://cdn.videvo.net/videvo_files/video/premium/getty_193/large_watermarked/istock-901050306_preview.mp4","Animation movie on Transcendental Meditation", "Idea, Script and voiceover: Moti Shefi\n" +
                "Animation: Ravid Sandlerman\n" +
                "Mr Jones: Roee Berger\n" +
                "Original Music: Ofra Avni and Yitzhak Yona\n" +
                "Sound effects : freesfx.co.uk","07:46", R.drawable.meditation6)
        videos.add(video1)
        videos.add(video2)
        videos.add(video3)
        videos.add(video4)
        videos.add(video5)
        videos.add(video6)
    }

    fun getData(): ArrayList<Videos> {
        fillData()
        return videos
    }

}