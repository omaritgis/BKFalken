package com.example.omartotangy.myapplication.Matchboxare;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omartotangy.myapplication.R;

import java.util.ArrayList;

/**
 * @author Omar Totangy
 * This class is used to start either a private workout or a public workout.
 */
public class RondTimer extends Activity {
    private Button start;
    private TextView rounds, rest, time, övning;
    private int round = 0;
    private final int ROUND_TIMER = 1;
    private final int REST_TIMER = 2;
    private final int CHANGE_EXCERSICE = 3;
    private CountDownTimer roundTimer;
    private CountDownTimer restTimer;
    private static long timeLeftInRoundInSeconds = 180000; //180000 = 3min
    private static long timeLeftToRestInSeconds = 60000; //60000 = 1 min
    private boolean timePaused;
    private boolean mTimeRunning;
    private ArrayList<String> listan = new ArrayList<>();
    private int j = 0;
    private int decision;
    private String rubrik;
    private final String VILA = "Vila";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rond_timer);
        int i = 0;
        while (getIntent().getStringExtra("träning" + i) != null) {
            listan.add(getIntent().getStringExtra("träning" + i));
            i++;
        }

        //timePaused = false;

        String firstAlmost = listan.get(0);
        String first = firstAlmost.replaceAll("[0-9]", "");
        String str = firstAlmost.replaceAll("\\D+", "");
        rubrik = first;

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //timePaused = false;
                //setStart();

                if (mTimeRunning) {
                    pauseTimer();
                } else {
                    setStart();
                }
            }
        });

        rounds = findViewById(R.id.rounds);
        rounds.setText("" + str);
        rest = findViewById(R.id.rest);
        time = findViewById(R.id.time);
        övning = findViewById(R.id.övning);
        övning.setText(first);

        try {
            round = Integer.parseInt(rounds.getText().toString());
        } catch (NumberFormatException nfe) {

        }

        restTimer = new CountDownTimer(timeLeftToRestInSeconds, 1000) {
            @Override
            public void onTick(long l) {
                if (!timePaused) {
                    timeLeftToRestInSeconds = l;
                    updateRestTime();
                }
            }

            @Override
            public void onFinish() {
                if (round > 0 && !timePaused) {
                    timeLeftInRoundInSeconds = 5000;
                    setStart();
                } else if (round == 0 && !timePaused) {
                    updateWorkout();
                }
            }
        };

        if (savedInstanceState != null) {
            timeLeftInRoundInSeconds = savedInstanceState.getLong("roundSecond");
            timePaused = savedInstanceState.getBoolean("paused");
            timeLeftToRestInSeconds = savedInstanceState.getLong("restSecond");

            round = savedInstanceState.getInt("rounds");
            rounds.setText("" + savedInstanceState.getInt("rounds"));
            övning.setText(savedInstanceState.getString("rubrik"));
            updateTime();
            updateRestTime();
            setStart();
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("roundSecond", timeLeftInRoundInSeconds);
        savedInstanceState.putBoolean("paused", timePaused);
        savedInstanceState.putLong("restSecond", timeLeftToRestInSeconds);
        savedInstanceState.putInt("rounds", round);
        String rubrik = övning.getText().toString();

        savedInstanceState.putString("rubrik", rubrik);
    }

    /**
     * Starts the timer, one round is always 3 minutes, rest is always 30 sec. Rounds can be changed however.
     * Method uses countdowntimer to achive goal.
     */
    public void setStart() {
        övning.setText(rubrik);
        roundTimer = new CountDownTimer(timeLeftInRoundInSeconds, 1000) {
            @Override
            public void onTick(long l) {
                if (timePaused != true) {
                    timeLeftInRoundInSeconds = l;
                    updateTime();
                }
            }

            @Override
            public void onFinish() {
                if (round > 0) {
                    round--;
                    rounds.setText("" + round);
                }
                if (!timePaused) {
                    timeLeftToRestInSeconds = 5000;
                    setRestStart();
                }
            }
        }.start();
        mTimeRunning = true;
        start.setText("pausa");
    }

    /**
     * Method is used to start a countdown for time left to rest. When rest is done, next round starts or next workout.
     */
    public void setRestStart() {
        övning.setText(VILA);
        restTimer.start();
    }


    /**
     * This method is used to update the time and show it on the screen.
     */
    public void updateTime() {
        if (!timePaused) {
            int minTid = (int) timeLeftInRoundInSeconds / 60000;
            int sekundTid = (int) timeLeftInRoundInSeconds % 60000 / 1000;

            String timeLeft;

            timeLeft = "" + minTid;
            timeLeft += ":";
            if (sekundTid < 10) {
                timeLeft += "0";

            }
            timeLeft += sekundTid;
            time.setText(timeLeft);
        }
    }

    public void updateRestTime() {
        int minTid = (int) timeLeftToRestInSeconds / 60000;
        int sekTid = (int) timeLeftToRestInSeconds % 60000 / 1000;

        String timeLeft;

        timeLeft = "" + minTid;
        timeLeft += ":";
        if (sekTid < 10) {
            timeLeft += "0";
        }
        timeLeft += sekTid;
        //rest.setText(timeLeft);
        time.setText(timeLeft);
    }

    /**
     * This method just changes the title to the next workout when the countdown is done.
     */
    public void updateWorkout() {
        if (j < listan.size() - 1) {
            j++;
            String firstAlmost = listan.get(j);
            String first = firstAlmost.replaceAll("[0-9]", "");
            String str = firstAlmost.replaceAll("\\D+", "");

            rubrik = first;
            övning.setText(first);
            rounds.setText("" + str);

            round = Integer.parseInt(str);

            timeLeftInRoundInSeconds = 5000;
            timeLeftToRestInSeconds = 5000;

            setStart();
        } else {
            Toast.makeText(this, "Grymt jobbat!!", Toast.LENGTH_LONG).show();
        }
    }

    public void pauseTimer(){
        decision = REST_TIMER;
        mTimeRunning = false;
        start.setText("Starta");
        roundTimer.cancel();
        restTimer.cancel();

    }
}