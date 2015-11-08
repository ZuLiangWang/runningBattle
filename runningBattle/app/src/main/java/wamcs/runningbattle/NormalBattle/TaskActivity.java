package wamcs.runningbattle.NormalBattle;

import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import wamcs.runningbattle.R;


public class TaskActivity extends AppCompatActivity {

    ImageView fighter;

    ImageView lightView;

    int duration = 0;

    int deltaTime = duration;

    int lights[];

    int lightIndex = 0;

    Animation animation;

    Animation firstDelay, delay, underAttack;

    ImageView monster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        fighter = (ImageView)findViewById(R.id.fighter);
        AnimationDrawable animationDrawable;
        animationDrawable = (AnimationDrawable)fighter.getDrawable();
        animationDrawable.start();

        lightView = (ImageView)findViewById(R.id.light);

        monster = (ImageView)findViewById(R.id.monster);

        delay = AnimationUtils.loadAnimation(this, R.anim.delay);
        firstDelay = AnimationUtils.loadAnimation(this, R.anim.first_delay);
        underAttack = AnimationUtils.loadAnimation(this, R.anim.under_attack);

        delay.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                monster.startAnimation(underAttack);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        underAttack.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                monster.startAnimation(delay);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        monster.startAnimation(delay);


        for(int i = 0; i < animationDrawable.getNumberOfFrames(); ++i){
            duration += animationDrawable.getDuration(i);
        }

        deltaTime = duration;



    }

    public class AnimationAsyncTask extends AsyncTask<Integer, Void, Object> {



        @Override
        protected Object doInBackground(Integer... params) {
            Integer time = 80*21*params[0];
            try {
                Thread.sleep(time);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  params[0];
        }

        @Override
        protected void onPostExecute(Object o) {
            Integer i = (Integer)o;
            lightView.setImageResource(lights[i]);
            super.onPostExecute(o);
        }
    }

}
