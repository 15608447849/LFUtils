package com.lzping.lfutils.core.impls;

import com.lzping.lfutils.R;
import com.lzping.lfutils.interfaces.IFragmentCoreAnimations;

/**
 * Created by user on 2017/1/11.
 */

public class LFAnimImp implements IFragmentCoreAnimations {
    public enum CoreAnim {
        button2top, /*由下到上动画 */
        top2button,/*  由上到下 */
        left2right,/* 从左到右动画 */
        right2left,/* 从右到左*/
        fade,/*渐变 */
        zoom;/*放大 */
    }

    private static LFAnimImp instans;

    private LFAnimImp() {
    }
    public static LFAnimImp getInstans(){
        if (instans==null){
            instans = new LFAnimImp();
        }
        return instans;
    }

    @Override
    public int[] convertAnimations(Object data) {
        try {
            CoreAnim coreAnim = (CoreAnim) data;
            if (coreAnim == CoreAnim.left2right) {
                    return new int[] {R.anim.slide_in_right, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_right};
                }
            if (coreAnim == CoreAnim.right2left) {
                return new int[] {R.anim.slide_in_left, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_left};
            }
            if (coreAnim == CoreAnim.top2button){ //向下
                return new int[] {R.anim.push_in_down, R.anim.push_out_down,R.anim.push_in_down, R.anim.push_out_down};
            }
            if (coreAnim == CoreAnim.button2top){ //向上
                return new int[] {R.anim.push_in_up, R.anim.push_out_up,R.anim.push_in_up, R.anim.push_out_up};
            }

            if (coreAnim == CoreAnim.fade){ //渐变
                return new int[] {R.anim.alpha_in, R.anim.alpha_out,R.anim.alpha_in, R.anim.alpha_out};
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
