package kr.hs.dgsw.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kr.hs.dgsw.stopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    // 시간 변수
    private var time = 0
    //
    private var timerTask: Timer? = null
    // 타이머가 시작 여부를 확인하는 변수
    private var isRunning = false
    // 타이머 기록 획수를 표시하는 변수
    private var lap = 1

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 플레이 버튼을 누르면 실행하는 함수
        binding.fab.setOnClickListener{
            // 버튼을 누르면 false는 true, true는 false로 바꿈
            isRunning = !isRunning
            // 만약 isRunning이 true면 start함수 실행
            if(isRunning){
                start()
            }
            // 만약 isRunning이 false면 pause함수 실행
            else{
                pause()
            }
        }

        // lap버튼을 누르면 recordLapTime함수를 실행
        binding.lapBtn.setOnClickListener{
            recordLapTime()
        }
        //resetFab버튼을 누르면 reset함수를 실행
        binding.resetFab.setOnClickListener{
            reset()
        }
    }

    private fun start(){
        // 플레이 이미지를 퍼즈 이미지로 변경됨
        binding.fab.setImageResource(R.drawable.baseline_pause_24)
        timerTask = timer(period = 10){
            time++
            // 시간 계산
            val sec = time/100
            val milli = time%100
            // 계산된 시간을 화면에 표시
            runOnUiThread{
                binding.secTextView.text = "$sec"
                binding.miliTextView.text = "$milli"
            }
        }
    }

    // 타임어를 멈추는 함수
    private fun pause(){
        // 퍼즈 이미지를 플레이 이미지로 변경됨
        binding.fab.setImageResource(R.drawable.baseline_play_arrow_24)
        // timerTask에 시간이 있으면 멈춤
        timerTask?.cancel()
    }

    // 현재 시간을 저장하는 함수
    private fun recordLapTime(){
        // 지금 시간을 lapTime에 저장
        val lapTime = this.time
        // textViiew에 TextView함수 연결
        val textView = TextView(this)
        // textView의 text를 현재 시간으로 저장
        textView.text = "$lap LAP : ${lapTime/100}.${lapTime%100}"
        // lapLayout에 textView를 add(추가)
        binding.lapLayout.addView(textView, 0)
        lap++
    }
    // 모든 것을 초기화하는 함수
    private fun reset(){
        // timerTask에 시간이 있으면 멈춤
        timerTask?.cancel()
        // 시간을 0으로 초기화
        time = 0
        // 타이머 자체 기능을 멈춤
        isRunning = false
        // 퍼즈 이미지를 플레이 이미지로 바꿈
        binding.fab.setImageResource(R.drawable.baseline_play_arrow_24)
        // 시간을 표시하는 text를 0으로 초기화
        binding.secTextView.text = "0"
        binding.miliTextView.text = "00"
        // lapLayout에 있는 값을 삭제
        binding.lapLayout.removeAllViews()
        lap = 1

    }
}