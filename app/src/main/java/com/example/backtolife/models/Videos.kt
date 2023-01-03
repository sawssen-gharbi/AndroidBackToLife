package com.example.backtolife.models

import com.google.android.exoplayer2.MediaItem
import java.io.Serializable

class Videos : Serializable {
    var url: String = ""
    var title: String = ""
    var description: String = ""
    var duration: String = ""
    var image: Int = 0

    constructor()

    constructor(url: String, title: String, description: String, duration: String, image: Int){
        this.url = url
        this.title = title
        this.description = description
        this.image = image
        this.duration = duration
    }
}