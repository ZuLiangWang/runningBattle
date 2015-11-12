package wamcs.runningbattle.NormalBattle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import wamcs.runningbattle.R;
import wamcs.runningbattle.mapDistance.MapApplication;


/**
 * Created by huyiming on 15/10/24.
 */
public class MainScene  {



    TextView journey;
    TextView stone;

    Activity activity;

    Animation trans;

    LinearLayout leftF, rightF;

    int count = 0;

    boolean isruning = false;

    AnimationDrawable animationDrawable;

    private int unitDis = 10;
    private int unitLenght = 20;

    private long currentDis = 0;

    private long lastDis = 0;

    View root;


    public MainScene(final Activity activity, View root, ImageView fighter, ImageView road) {

        this.activity = activity;
        this.root = root;

        journey = (TextView)root.findViewById(R.id.journey);
        stone = (TextView)root.findViewById(R.id.kaluStone);


        final ImageView sword = (ImageView)root.findViewById(R.id.sword);

        currentDis = (long)((MapApplication)activity.getApplication()).getDistance();


        journey.setText("今天走了 " + currentDis + " 米");
        stone.setText("获得卡路石 0 个");



        fighter.setImageResource(R.drawable.walking);
        animationDrawable = (AnimationDrawable)fighter.getDrawable();
        animationDrawable.start();

        final Animation swordAnim = AnimationUtils.loadAnimation(activity, R.anim.sword);
        sword.startAnimation(swordAnim);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               currentDis = (long) ((MapApplication) activity.getApplication()).getDistance();
                                               if (!isruning) {
                                                   scrollToCurrent();
                                                   isruning = true;
                                               }

                                               if(lastDis > currentDis){
                                                   isruning = false;
                                               }

                                               journey.setText("今天走了 " + currentDis + " 米");

                                           }
                                       }

                );

            }
        }, 0, 1000);

        setUpRoadTag((LinearLayout) root.findViewById(R.id.leftFrame), (LinearLayout) root.findViewById(R.id.rightFrame));


    }

    private void resetTag(){

        for(int i = 0; i<4;++i) {

            View leftLast = leftF.getChildAt(i);
            View rightLast = rightF.getChildAt(i);

            leftLast.setTranslationY(200);
            rightLast.setTranslationY(200);
            ((TextView) leftLast.findViewById(R.id.tagTextL)).setText(  (lastDis + (4-i)*10) + " m");
            ((TextView) rightLast.findViewById(R.id.tagTextR)).setText( (lastDis + (4-i)*10) + " m");

        }
    }

    private void scrollToCurrent(){



        animationDrawable.start();
        trans = AnimationUtils.loadAnimation(activity, R.anim.road_line);

        trans.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isruning = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                resetTag();

                count--;

                if(count == 0){
                    lastDis -= 10;
                    count = 4;
                    if (lastDis < currentDis) {
                        isruning = true;
                        for (int i = 0; i < 4; ++i) {
                            leftF.getChildAt(i).startAnimation(animation);
                            rightF.getChildAt(i).startAnimation(animation);
                        }
                    }
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        for(int i = 0; i < 4; ++i) {
            leftF.getChildAt(i).startAnimation(trans);
            rightF.getChildAt(i).startAnimation(trans);
        }


    }


    private void setUpRoadTag(LinearLayout leftF, LinearLayout rightF) {
        this.leftF = leftF;
        this.rightF = rightF;

        WindowManager manager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int height = manager.getDefaultDisplay().getHeight();
        int count = height / unitLenght;
        for (int i = 3; i >= 0; --i) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            LinearLayout left = (LinearLayout) inflater.inflate(R.layout.road_tag_left, null);

            LinearLayout right = (LinearLayout) inflater.inflate(R.layout.road_tag, null);

            String text = (i * 10 + lastDis) + " m";

            TextView leftText = (TextView) left.findViewById(R.id.tagTextL);

            TextView rightText = (TextView) right.findViewById(R.id.tagTextR);

            leftText.setText(text);
            rightText.setText(text);

            leftF.addView(left);
            rightF.addView(right);
        }
    }


    public static Bitmap createReflectedImage(Bitmap paramBitmap, int paramInt)
    {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Matrix localMatrix = new Matrix();
        localMatrix.preScale(1.0F, -1.0F);
        Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap, 0, j - paramInt, i, paramInt, localMatrix, false);
        Bitmap localBitmap2 = Bitmap.createBitmap(i, j + paramInt, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap2);
        Paint localPaint1 = new Paint();
        localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint1);
        localCanvas.drawBitmap(localBitmap1, 0.0F, j, localPaint1);
        Paint localPaint2 = new Paint();
        localPaint2.setShader(new LinearGradient(0.0F, paramBitmap.getHeight(), 0.0F, localBitmap2.getHeight(), 1895825407, 16777215, Shader.TileMode.MIRROR));
        localPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        localCanvas.drawRect(0.0F, j, i, localBitmap2.getHeight(), localPaint2);
        localBitmap1.recycle();
        return localBitmap2;
    }

    public static Bitmap skewImage(Bitmap paramBitmap, int paramInt)
    {
        Bitmap localBitmap1 = createReflectedImage(paramBitmap, paramInt);
        Camera localCamera = new Camera();
        localCamera.save();
        Matrix localMatrix = new Matrix();
        localCamera.rotateY(15.0F);
        localCamera.getMatrix(localMatrix);
        localCamera.restore();
        localMatrix.preTranslate(-localBitmap1.getWidth() >> 1, -localBitmap1.getHeight() >> 1);
        Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, 0, localBitmap1.getWidth(), localBitmap1.getHeight(), localMatrix, true);
        Bitmap localBitmap3 = Bitmap.createBitmap(localBitmap2.getWidth(), localBitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap3);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setFilterBitmap(true);
        localCanvas.drawBitmap(localBitmap2, 0.0F, 0.0F, localPaint);
        localBitmap2.recycle();
        return localBitmap3;
    }



}
