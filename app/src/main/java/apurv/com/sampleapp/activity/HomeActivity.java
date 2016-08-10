package apurv.com.sampleapp.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import apurv.com.sampleapp.R;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageViewStart;
    private ImageView mImageViewStop;
    private Chronometer mChronometer;
    private long timeWhenStopped = 0;
    private boolean isRecording = false;
    private Button mButtonExit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialiseView();
        initListeners();
    }

    private void initialiseView() {
        mImageViewStart = (ImageView) findViewById(R.id.imageview_start);
        mImageViewStop = (ImageView) findViewById(R.id.imageview_stop);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mButtonExit = (Button) findViewById(R.id.button_exit);

    }

    private void initListeners() {
        mImageViewStart.setOnClickListener(this);
        mImageViewStop.setOnClickListener(this);
        mButtonExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_start:


                if (isRecording) {
                    timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
                    mChronometer.stop();
                    mImageViewStart.setImageResource(R.drawable.record);
                    isRecording = false;
                } else {
                    isRecording = true;
                    mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    mChronometer.start();
                    mImageViewStart.setImageResource(R.drawable.pause);
                    break;
                }

                break;
            case R.id.imageview_stop:
                mChronometer.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
                break;

            case R.id.button_exit:
                finish();
                break;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChronometer.setBase(SystemClock.elapsedRealtime());
        timeWhenStopped = 0;
    }
}
