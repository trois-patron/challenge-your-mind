package com.BlueN.challengeyourmind;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

public class Lev6Activity extends AppCompatActivity {

    private ImageView Lev6Iv;
    private EditText Lev6Et;
    private Button Lev6SubmitButton, Lev6HintButton;
    private String value;
    private int FinalValue;
    private Typeface font;
    private SoundPool sp;
    private int soundId;
    private int soundIdcorrect;
    private int soundIdwrong;
    private Animation drop_down;
    private Animation blink;
    private Animation fade_in;
    private AlphaAnimation buttonClick;
    final VunglePub vunglePub = VunglePub.getInstance();
    private Lev6HintActivity frag;
    private FragmentManager fm;
    final EventListener vungleListener = new EventListener(){
        @Override
        public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) {
            if(isCompletedView==true)
                frag.show(fm, "Lev6Hint");
        }
        @Override
        public void onAdStart() {

            frag.show(fm, "Lev6Hint");
        }

        @Override
        public void onAdEnd(boolean wasCallToActionClicked) {

        }

        @Override
        public void onAdPlayableChanged(boolean isAdPlayable) {

        }

        @Override
        public void onAdUnavailable(String reason) {

        }

    };
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "206917462", true);
        vunglePub.init(this, "57a7db86fea8acdf7e00001a");
        setContentView(R.layout.activity_lev6);

        frag= new Lev6HintActivity();
        frag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        fm= getFragmentManager();
        vunglePub.setEventListeners(vungleListener);

        Lev6Iv=(ImageView) findViewById(R.id.Lev6Iv);
        Lev6Et=(EditText) findViewById(R.id.Lev6Et);
        Lev6SubmitButton=(Button) findViewById(R.id.Lev6SubmitButton);
        Lev6HintButton=(Button) findViewById(R.id.Lev6HintButton);

        font=Typeface.createFromAsset(getAssets(), "fonts/beyond_the_mountains.ttf");
        Lev6SubmitButton.setTypeface(font);
        Lev6HintButton.setTypeface(font);

        sp= new SoundPool(5, AudioManager.STREAM_MUSIC,0 );
        soundId = sp.load(getBaseContext(), R.raw.bsound, 1);
        soundIdcorrect = sp.load(getBaseContext(), R.raw.correctanswer, 1);
        soundIdwrong = sp.load(getBaseContext(), R.raw.wronganswer, 1);

        drop_down= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.drop_down_logo);

        blink= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        fade_in= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        Lev6Iv.startAnimation(drop_down);
        Lev6SubmitButton.startAnimation(blink);
        Lev6HintButton.startAnimation(blink);
        Lev6Et.startAnimation(blink);
        buttonClick = new AlphaAnimation(10F, 0.1F);

        vunglePub.init(this, "57a7db86fea8acdf7e00001a");
        vunglePub.setEventListeners(vungleListener);
    }
    public void Submit (View v) {

        Lev6SubmitButton.startAnimation(fade_in);
        Lev6SubmitButton.startAnimation(buttonClick);

        value= Lev6Et.getText().toString();

        if(value.equals(""))

        {

        }
        else

            FinalValue=Integer.parseInt(value);

        if(FinalValue==2)

        {
            StartAppAd.showAd(this);
            sp.play(soundIdcorrect,1,1,0,0,1);
            Toast.makeText(this, "Well done.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent (this, RateApp.class);
            startActivity(i);
            FirstActivity.savelevstate(7, this);
            this.finish();

        }

        else{
            Lev6Iv.startAnimation(blink);
            sp.play(soundIdwrong,1,1,0,0,1);
        }
    }
    public void Hint (View v){
        Lev6HintButton.startAnimation(fade_in);
        sp.play(soundId, 1, 1, 0, 0, 1);
        Lev6HintButton.startAnimation(buttonClick);
        vunglePub.isAdPlayable();
        vunglePub.playAd();

        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
        } else {
            Toast.makeText(this, "Network error!, please check your internet conection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        vunglePub.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        vunglePub.onResume();
    }
}
