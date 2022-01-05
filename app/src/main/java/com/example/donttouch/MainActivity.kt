package com.example.donttouch

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.donttouch.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    //뷰바인딩
    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private var time = 180000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //리스너 불러오기
        btnListener()
    }

    //버튼 리스너
    private fun btnListener() {
        binding.timerStartBtn.setOnClickListener {
            startTimer()
        }
        //타이머 멈추기 셋온클릭 리스너
        binding.timerStopBtn.setOnClickListener {
            stopTimer()
            dialog()
        }
        binding.stopBtn.setOnClickListener {
            binding.countNum.setText(R.string.resetNum)
        }

    }

    private fun dialog() {
        val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage(binding.timerStopBtn.toString() + "분 참으셨습니다!")
            .setTitle("결과!")
                .setNegativeButton("나가기", DialogInterface.OnClickListener {_, _ -> })
        val alert = dialogBuilder.create()
        alert.setTitle("결과")
        alert.show()

    }


    private val countDownTimer = object : CountDownTimer(time.toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            // 텍스트 업데이트
            binding.countNum.text = getString(R.string.formatted_time,
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60)
        }

        override fun onFinish() {
            binding.countNum.text = (R.string.resetNum.toString())
        }

    }
    private fun startTimer() {
        countDownTimer.start()
    }
    private fun stopTimer() {
        countDownTimer.cancel()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

}

