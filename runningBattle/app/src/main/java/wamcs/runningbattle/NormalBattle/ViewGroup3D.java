package wamcs.runningbattle.NormalBattle;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by huyiming on 15/10/24.
 */
public class ViewGroup3D extends FrameLayout {

    Camera camera;
    Matrix matrix;

    public ViewGroup3D(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        float centerx = getWidth()/2;
        float centery = getHeight()/2;

        camera = new Camera();
        matrix = new Matrix();

        canvas.save();
        camera.save();
        camera.rotateX(15);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerx, -2*centery);
        matrix.postTranslate(centerx, 2*centery);
        canvas.concat(matrix);
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restore();
        return  result;

    }


}
