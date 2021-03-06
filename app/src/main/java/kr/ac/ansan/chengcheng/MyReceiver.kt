package kr.ac.ansan.chengcheng

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kr.ac.ansan.chengcheng.Constant.Companion.CHANNEL_ID
import kr.ac.ansan.chengcheng.Constant.Companion.NOTIFICATION_ID

class MyReceiver : BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager
    private var database = FirebaseDatabase.getInstance()

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotifocationChannel()
        deliverNotification(context)


    }

    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, SplashActivity::class.java)
       /* contentIntent.action = Intent.ACTION_MAIN
        contentIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK*/
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID, // requestCode
            contentIntent, // 알림 클릭 시 이동할 인텐트
            PendingIntent.FLAG_CANCEL_CURRENT
            /*
            1. FLAG_UPDATE_CURRENT : 현재 PendingIntent를 유지하고, 대신 인텐트의 extra data는 새로 전달된 Intent로 교체
            2. FLAG_CANCEL_CURRENT : 현재 인텐트가 이미 등록되어있다면 삭제, 다시 등록
            3. FLAG_NO_CREATE : 이미 등록된 인텐트가 있다면, null
            4. FLAG_ONE_SHOT : 한번 사용되면, 그 다음에 다시 사용하지 않음
             */
        )
        val style = NotificationCompat.InboxStyle()
        //아이콘 넣어야함
       // dlgItemsMap
        // 알람 추가하는 창을 옮겨야함
        style.addLine("임시 1")
        style.addLine("임시 2")
        style.addLine("임시 3")
        style.addLine("임시 4")
        style.addLine("임시 5")


        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_camp_15) // 아이콘
            .setContentTitle("리스트제목") // 제목(리스트제목)
            .setContentText("더보기++") // 내용(아이콘들)
            .setStyle(style)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())


    }

    private fun createNotifocationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, // 채널의 아이디
                "채널 이름입니다.", // 채널의 이름
                NotificationManager.IMPORTANCE_HIGH
                /*
                1. IMPORTANCE_HIGH = 알림음이 울리고 헤드업 알림으로 표시
                2. IMPORTANCE_DEFAULT = 알림음 울림
                3. IMPORTANCE_LOW = 알림음 없음
                4. IMPORTANCE_MIN = 알림음 없고 상태줄 표시 X
                 */
            )
            notificationChannel.enableLights(true) // 불빛
            notificationChannel.lightColor = Color.RED // 색상
            notificationChannel.enableVibration(true) // 진동 여부
            notificationChannel.description = "채널의 상세정보입니다." // 채널 정보
            notificationManager.createNotificationChannel(
                notificationChannel)
        }
    }

    /*fun iconAdd(){
       val myRef =  database.getReference("User")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }*/
}

class Constant{
    companion object{
        //아이디 선언
        const val NOTIFICATION_ID = 0
        const val CHANNEL_ID = "notification_channel"

    }
}