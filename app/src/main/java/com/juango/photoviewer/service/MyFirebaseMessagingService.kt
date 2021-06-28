package com.juango.photoviewer.service


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.juango.photoviewer.R


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            val data = remoteMessage.data

            val intent = Intent()

            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(data["uri"])
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val args = Bundle()
            val idPost = data["uri"]?.split("/")
            idPost?.get(idPost.size - 1)
                ?.let { args.putInt("idPost", it.toInt()) } // validar si id no existe

            val pendingIntent = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.postDetailFragment)
                .setArguments(args)
                .createPendingIntent()

            val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_baseline_arrow_back_24)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(this)) {
                notify(200, builder.build())
            }
        }
    }

}