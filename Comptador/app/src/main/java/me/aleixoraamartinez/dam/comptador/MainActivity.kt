package me.aleixoraamartinez.dam.comptador

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.aleixoraamartinez.dam.comptador.ui.theme.ComptadorTheme

class MainActivity : ComponentActivity() {

    private val INITIAL_TIME=5

    internal lateinit var tapMeButton : Button
    internal lateinit var timeTextView : TextView
    internal lateinit var counterTextView : TextView
    internal var counter = 0
    internal var time = INITIAL_TIME

    internal var appStarted = false
    internal lateinit var countdownTimer : CountDownTimer
    //internal val initialCountDownTimer: Long = 60000
    internal val initialCountDownTimer: Long = time.toLong()*1000
    internal val intervalCountDownTimer: Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        initCountdown()

        tapMeButton = findViewById(R.id.tapMeButton)
        timeTextView = findViewById(R.id.timeTextView)
        counterTextView = findViewById(R.id.counterTextView)


        val infoButton = findViewById<ImageButton>(R.id.infoButton)
        infoButton.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Informació")
                .setMessage("Aquesta aplicació android ha estat feta per Aleix Oraà Martínez")
                .setPositiveButton("Tancar") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            dialog.show()
        }

        tapMeButton.setOnClickListener{
            view ->
            if(!appStarted){
                startGame()
            }
            val bounceAnimation=AnimationUtils.loadAnimation(this,R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementCounter()
        }

        timeTextView.text=getString(R.string.timeText, time)
        counterTextView.text=getString(R.string.punts,counter)
    }

    private fun startGame() {
        countdownTimer.start()
        appStarted= true
    }

    private fun initCountdown(){
        countdownTimer = object : CountDownTimer(initialCountDownTimer,intervalCountDownTimer){
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeTextView.text=getString(R.string.timeText, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }
    }
    private fun incrementCounter(){
        counter = counter+1
        counterTextView.text = getString(R.string.punts,counter)
    }

    private fun endGame(){
        Toast.makeText(this,getString(R.string.endGame, counter), Toast.LENGTH_LONG).show()
        resetGame()
    }




    private fun resetGame(){
        counter = 0
        counterTextView.text = getString(R.string.punts,counter)


        time = INITIAL_TIME
        timeTextView.text=getString(R.string.timeText,time)

        initCountdown()

        appStarted = false
    }
}
