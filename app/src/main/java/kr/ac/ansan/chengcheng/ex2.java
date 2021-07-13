package kr.ac.ansan.chengcheng;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ex2 extends AppCompatActivity {


    Button myButton;
    View myView;
    boolean isUp;
    boolean isUp2;


    RecyclerVierAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        init();
        getData();
        myView = findViewById(R.id.my_view);

        myButton = findViewById(R.id.my_button);


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


    private void init(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerVierAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        DataMovie data = new DataMovie(R.drawable.iron_man, "의류");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.spider_man, "세면도구");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.black_panther, "예시1");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.doctor, "예시2");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.hulk, "예시3");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.thor, "예시4");
        adapter.addItem(data);
    }
}