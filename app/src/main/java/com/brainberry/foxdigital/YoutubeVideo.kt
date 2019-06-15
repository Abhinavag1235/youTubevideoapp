package com.brainberry.foxdigital

/**
 * This class contains details of the YouTube videos
 *
 * @author Shayak Banerjee
 * @version 0.2
 * @since 6th June, 2019
 */
data class YoutubeVideo(
    var title: String?,
    var videoId: String?,
    var imageUrl: String?,
    var description: String?,
    var payout: String?,
    var link1: String?,
    var link2: String?
) {

    fun emptyFieldExists(): Boolean {
        return (title!!.isEmpty()
                || videoId!!.isEmpty()
                || imageUrl!!.isEmpty()
                || description!!.isEmpty()
                || link1!!.isEmpty()
                || link2!!.isEmpty())
    }
}
