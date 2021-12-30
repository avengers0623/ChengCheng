package kr.ac.ansan.chengcheng

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.datetime_dialog.*
import kr.ac.ansan.chengcheng.databinding.DatetimeDialogBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CustomDialog : DialogFragment() {
    private var _binding: DatetimeDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        var year: Int? = null
        var monthOfYear: Int? = null
        var dayOfMonth: Int? = null
        var hourOfDay: Int? = null
        var minute: Int? = null
        var check : Boolean = false
        var pendingIntent : PendingIntent? = null
        var alarmManager : AlarmManager? = null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DatetimeDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        val c: Calendar = Calendar.getInstance()
        val mYear: Int = c.get(Calendar.YEAR)
        val mMonth: Int = c.get(Calendar.MONTH)
        val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
        Log.d("날짜", "연도: $mYear 월 : $mMonth 일 $mDay")

        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MyReceiver::class.java)
         pendingIntent = PendingIntent.getBroadcast(
            context, Constant.NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        binding.dateDlg.init(mYear, mMonth, mDay, object : DatePicker.OnDateChangedListener {
            override fun onDateChanged(
                view: DatePicker?,
                year: Int,
                monthOfYear: Int,
                dayOfMonth: Int
            ) {
                CustomDialog.year = year
                CustomDialog.monthOfYear = monthOfYear+1 // 월이 0부터 시작해서 1 더해준다
                CustomDialog.dayOfMonth = dayOfMonth
                resultDateText.text = "${year}년 ${CustomDialog.monthOfYear}월 ${dayOfMonth}일"
                Log.d("DialogFragment", "$year")
                Log.d("DialogFragment", "$monthOfYear")

            }
        })

        binding.timeDlg.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
            override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                CustomDialog.hourOfDay = hourOfDay
                CustomDialog.minute = minute

                resultTimeText.text = "${hourOfDay}시 ${minute}분"
                Log.d("DialogFragment", "$hourOfDay")
            }

        })



        binding.backBtn.setOnClickListener {
            buttonClickListener.onButtonClicked1()
            val animation: Animation = AlphaAnimation(0f, 1f)
            animation.duration = 200
            dateDlg.animation = animation
            timeDlg.visibility = View.INVISIBLE
            dateDlg.visibility = View.VISIBLE
            nextBtn.visibility = View.VISIBLE
            saveBtn.visibility = View.INVISIBLE
            backBtn.visibility = View.INVISIBLE
            //dismiss()    // 대화상자를 닫는 함수
        }

        binding.nextBtn.setOnClickListener {
            buttonClickListener.onButtonClicked2()
            val animation: Animation = AlphaAnimation(0f, 1f)
            animation.duration = 200
            timeDlg.animation = animation
            dateDlg.visibility = View.INVISIBLE
            timeDlg.visibility = View.VISIBLE
            nextBtn.visibility = View.INVISIBLE
            saveBtn.visibility = View.VISIBLE
            backBtn.visibility = View.VISIBLE
            //dismiss()    // 대화상자를 닫는 함수
        }

        binding.saveBtn.setOnClickListener {
            if (year == null || monthOfYear == null || dayOfMonth == null || hourOfDay == null || minute == null){
                Toast.makeText(context, "날짜/시간을 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                check = true
                dismiss()    // 대화상자를 닫는 함수
            }

        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnButtonClickListener {
        fun onButtonClicked1()
        fun onButtonClicked2()
    }

    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    private lateinit var buttonClickListener: OnButtonClickListener

   /* @RequiresApi(Build.VERSION_CODES.O)
    fun setAlarm(
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int,
        hourOfDay: Int,
        minute: Int,
        alarmManager: AlarmManager,
        pendingIntent: PendingIntent
    ) {
        //alarm.start("알람")

        val from = "$year-$monthOfYear-$dayOfMonth $hourOfDay:$minute:00"
        Log.d("datetest",from)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var datetime: Date? = null

        try {
            datetime = dateFormat.parse(from)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val calendar = Calendar.getInstance()
        calendar.time = datetime


        alarmManager[AlarmManager.RTC, calendar.timeInMillis] = pendingIntent
        Log.d("datetest2",calendar.timeInMillis.toString())

       // val triggerTime = (SystemClock.elapsedRealtime() // 기기가 부팅된 후 경과한 시간 사용
             //   + 2000) // ms 이기 때문에 초단위로 변환 (*1000)

  *//*      alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            pendingIntent
        ) // set : 일회성 알림*//*
        Toast.makeText(context, "${hourOfDay}시 : ${minute}분에 알람 설정 완료!", Toast.LENGTH_SHORT).show()


        *//*      1. ELAPSED_REALTIME : ELAPSED_REALTIME 사용. 절전모드에 있을 때는 알람을 발생시키지 않고 해제되면 발생시킴.
              2. ELAPSED_REALTIME_WAKEUP : ELAPSED_REALTIME 사용. 절전모드일 때도 알람을 발생시킴.
              3. RTC : Real Time Clock 사용. 절전모드일 때는 알람을 발생시키지 않음.
              4. RTC_WAKEUP : Real Time Clock 사용. 절전모드 일 때도 알람을 발생시킴.*//*

        //            alarm.start("알람") //${SimpleDateFormat("HH:mm").format(cal.time)}

    }*/


}

