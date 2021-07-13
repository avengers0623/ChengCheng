package kr.ac.ansan.chengcheng;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ex2 extends AppCompatActivity {

    Button myButton;
    ImageButton myButton2;
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
        myButton.setText("Slide up");
        isUp = false;
        isUp2 = true;


    }

    // 슬라이드 열기
    public void slideUp(View view) {
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

    //슬라이드 닫기
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    //1번째 슬라이드 실행
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 슬라이드 열기
    public void slideUp2(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight() / 2,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    //슬라이드 닫기
    public void slideDown2(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight() / 2); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);


    }

//

    //2번째 슬라이드 실행
    public void onSlideViewButtonClick2(View view) {
        if (isUp2) {
            slideDown2(myView2);
            myButton2.setY(myView2.getHeight());

            myButton2.setImageResource(R.drawable.up);
        } else {
            slideUp2(myView2);
            myButton2.setY(myView2.getHeight()/2);
            myButton2.setImageResource(R.drawable.down);
        }
        isUp2 = !isUp2;
    }
}