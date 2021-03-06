package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home extends AppCompatActivity {

    VideoView vv;
    String whatbtn = "";
    public SharedPreferences prefs;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        databaseReference.child("index").setValue(0);

        // 신체 기록 최초 저장, 처음 실행시만 bodyprofile 설정하도록
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        checkFirstRun();

        Bundle bundle2 = new Bundle();

        //웨이트 이미지버튼
        //home에서 Weight으로 연결
        ImageButton btn1 = (ImageButton) findViewById(R.id.homeweight);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, fragment_whole.class);

                //번들을 이용해서 어떤 버튼이 눌렸는지 써서 보내줌
                whatbtn = "btn1";
                bundle2.putString("whatbtn", whatbtn);
                intent.putExtras(bundle2);

                startActivity(intent);
                finish();
            }
        });
        //home에서 cardio으로 연결
        ImageButton btn2 = (ImageButton) findViewById(R.id.homecardio);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, fragment_whole.class);

                //번들을 이용해서 어떤 버튼이 눌렸는지 써서 보내줌
                whatbtn = "btn2";
                bundle2.putString("whatbtn", whatbtn);
                intent.putExtras(bundle2);

                startActivity(intent);
                finish();
            }
        });
        //home에서 part으로 연결
        ImageButton btn3 = (ImageButton) findViewById(R.id.homepart);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, fragment_whole.class);

                //번들을 이용해서 어떤 버튼이 눌렸는지 써서 보내줌
                whatbtn = "btn3";
                bundle2.putString("whatbtn", whatbtn);
                intent.putExtras(bundle2);

                startActivity(intent);
                finish();
            }
        });

        //스트레칭 이미지버튼
        //home에서 Stretching으로 연결
        ImageButton stretching = (ImageButton) findViewById(R.id.homestretching);
        stretching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Stretching.class);
                startActivity(intent);
                finish();
            }
        });


        //초보자 루틴
        //home에서 Beginner_Routine으로 연결
        ImageButton beginner = (ImageButton) findViewById(R.id.beginner_routine);
        beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Beginner.class);
                startActivity(intent);
            }
        });


        //하단바
        ImageView girok = (ImageView) findViewById(R.id.scale);
        girok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, BodyProfile.class);
                startActivity(intent);
            }
        });

        ImageView memo = (ImageView) findViewById(R.id.calendar);
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Calendar.class);
                startActivity(intent);
            }
        });

        //home에서 mypage으로 연결
        ImageView mypage = (ImageView) findViewById(R.id.mypage);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Mypage.class);
                startActivity(intent);
            }
        });

        vv = findViewById(R.id.videoView3);
        //Video Uri
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.muscle1);

        //비디오뷰의 재생, 일시정지 등을 할 수 있는 '컨트롤바'를 붙여주는 작업
        vv.setMediaController(new MediaController(this));

        //VideoView가 보여줄 동영상의 경로 주소(Uri) 설정하기
        vv.setVideoURI(videoUri);

        //동영상을 읽어오는데 시간이 걸리므로..
        //비디오 로딩 준비가 끝났을 때 실행하도록..
        //리스너 설정
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //비디오 시작
                vv.start();
            }
        });

    }

    public void checkFirstRun() {
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            Intent newIntent = new Intent(home.this, BodyProfile.class);
            startActivity(newIntent);

            prefs.edit().putBoolean("isFirstRun", false).apply();
        }

    }
}
