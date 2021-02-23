package com.example.githubrepositories.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubrepositories.R;


/**
 * This class responsible for making custom Toast
 *
 * Ref: https://developer.android.com/guide/topics/ui/notifiers/toasts#CustomToastView
 * @author Tonmoy Chandra Ray
 * */
public class CustomToast {

    /**
     * This method is used to making Custom Toast.
     * How to use?
     * CustomToast.makeText(context, text, duration).show();
     * @param context the application context
     * @param text the string to be shows
     * @param duration the duration of the toast
     * @return {@link Toast}
     */
    public static Toast makeText(Context context, CharSequence text, int duration) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_custom_toast, null);

        TextView textView = view.findViewById(R.id.toast_text);
        textView.setText(text);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.setDuration(duration);
        toast.setView(view);

        return toast;
    }

    /**
     * This method is used to making Custom Toast.
     * How to use?
     * CustomToast.makeText(context, text, duration, background, color).show();
     * @param context the application context
     * @param text the string to be shows
     * @param duration the duration of the toast
     * @param background the background of the toast
     * @param colorId the color
     * @return {@link Toast}
     */
    public static Toast makeText(Context context, CharSequence text, int duration, Drawable background, int colorId) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_custom_toast, null);
        view.setBackground(background);

        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(text);
        textView.setTextColor(colorId);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.setDuration(duration);
        toast.setView(view);

        return toast;
    }

}
