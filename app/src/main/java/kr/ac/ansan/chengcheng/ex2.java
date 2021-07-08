package kr.ac.ansan.chengcheng;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ex2 extends AppCompatActivity {

    Button myButton;
    Button myButton2;
    View myView;
    View myView2;
    boolean isUp;
    boolean isUp2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        myView = findViewById(R.id.my_view);
        myView2 = findViewById(R.id.my_view2);

        myButton = findViewById(R.id.my_button);
        myButton2 = findViewById(R.id.my_button2);

        // initialize as invisible (could also do in xml)
        myView.setVisibility(View.INVISIBLE);
        myView2.setVisibility(View.INVISIBLE);

        myButton.setText("Slide up");
        isUp = false;

    }

    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(myView);
            myButton.setText("Slide up");
        } else {
            slideUp(myView);
            myButton.setText("Slide down");
        }
        isUp = !isUp;
    }


    public void onSlideViewButtonClick2(View view) {
        if (isUp2) {
            slideDown(myView2);
            myButton2.setText("Slide up");
        } else {
            slideUp(myView2);
            myButton2.setText("Slide down");
        }
        isUp2 = !isUp2;
    }
}