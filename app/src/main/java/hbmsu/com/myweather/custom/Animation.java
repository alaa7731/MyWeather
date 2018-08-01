package hbmsu.com.myweather.custom;



import hbmsu.com.myweather.R;

/**
 * Created by asalah on 3/5/2018.
 */

public enum Animation {
    BOUNCE_IN(R.anim.bounce_in),
    FADE_IN(R.anim.fade_in),
    FADE_IN_DOWN(R.anim.fade_in_down),
    FADE_IN_UP(R.anim.fade_in_up),
    FADE_IN_LEFT(R.anim.fade_in_left),
    FADE_IN_RIGHT(R.anim.fade_in_right),
    ROTATE_IN(R.anim.rotate_in),;

    private int animId;

    Animation(int animId) {
        this.animId = animId;
    }

    public int getAnimId() {
        return this.animId;
    }
}
