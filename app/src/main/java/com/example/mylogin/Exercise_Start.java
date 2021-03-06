package com.example.mylogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.util.ArrayList;

public class Exercise_Start extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    ImageView back;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseDatabase mDatabase2;
    private DatabaseReference mReference2;
    private ChildEventListener mChild2;
    VideoView vw;
    ArrayList<Integer> videolist = new ArrayList<>();
    ArrayList<Integer> nextExList=new ArrayList<>();
    ArrayList<String> SetList = new ArrayList<>();
    ArrayList<String> koreanName = new ArrayList<>();
    int currvideo = 0;
    int count=0;
    int count2=0;
    private MediaPlayer mediapalyer;
    int set_int=0;
    CountDownTimer timer;
    String stringSec = "5";
    int videocount=0;
    private boolean mTimerRunning;
    private Intent intent;
    String date;
    String packName;
    int i=0;
    ImageView nextex;
    int nextcount=0;
    int restres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        packName = this.getPackageName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_start);

        intent = getIntent();// ????????? ????????????
        date = intent.getStringExtra("date");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
        String uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                        .child("set");
                mPostReference.removeValue();
                onBackPressed();
            }
        });
        nextex=(ImageView)findViewById(R.id.nextex);

        //String[] ex_list = new String[100];

        //????????????????????? ?????? ?????? ?????? ????????????
        initDatabase();
        initDatabase2();
        vw = (VideoView)findViewById(R.id.startEx_vv);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);


  /*      mReference = mDatabase.getReference("ex_name"); // ???????????? ????????? child ??????
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("ordertest",str);
                }
            }

            //????????? ??????

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        DatabaseReference mReference7 = mDatabase.getReference("next_ex"); // ???????????? ????????? child ??????
        mReference7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("nextex",str);
                    int res = getResources().getIdentifier("@drawable/" + str, "drawable", packName);
                    nextExList.add(res);
                    if(videocount==0)
                    {nextex.setImageResource(nextExList.get(0));}
                }
            }

            //????????? ??????

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("next_ex").child("100").setValue("white");
        reference.child("next_ex").child("111").setValue("white");



        //?????? ???????????? ????????????
        DatabaseReference mReferenceName = mDatabase.getReference("fragment_ExList2"); // ???????????? ????????? child ??????
        mReferenceName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("fragment_ExList2",str);
                    koreanName.add(str);
                    koreanName.add("");
                }
            }

            //????????? ??????

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mReference = mDatabase.getReference("ex_name"); // ???????????? ????????? child ??????
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    //????????? ????????? ???????????? ?????????????????? ???????????????...?
                    int resID = getResources().getIdentifier(str, "raw", getPackageName());
                    videolist.add(resID);

                    if(count==0)
                    {
                       setVideo(videolist.get(0), koreanName.get(currvideo));
                       count++;
                    }
                }
            }

            //????????? ??????

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //????????? ?????? ???????????????????????? ????????????
        //????????? ?????? ??? ???????????? ????????? ????????????... ?????? ????????????
       mReference2 = mDatabase2.getReference("User_Ex_list").child(uid).child(date); // ???????????? ????????? child ??????
        mReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("test",str);
                    SetList.add(str);
                    if(count2==0)
                    {
                        countDown(stringSec, SetList.get(videocount));
                        count2++;
                    }
                }
            }

            //????????? ??????

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








    } //onCreate end

    public void setVideo(int id, String exkoreanName)
    {


        TextView exname1 = (TextView) findViewById(R.id.exname);

        exname1.setText(exkoreanName);
        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.start();
        vw.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override

            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }

        });

        //????????????


    }




    //????????? ???????????? ????????? ??????
    @Override
    public void onCompletion(MediaPlayer mediapalyer) {

        Log.i("videolist",Integer.toString(videolist.size()));
        Log.i("videocount",Integer.toString(videocount));
        this.mediapalyer = mediapalyer;
        if(videolist.size()==videocount){
            Intent intent = new Intent(Exercise_Start.this, Exercise_End.class);
            startActivity(intent);
            finish();
        }
        else{
            countDown(stringSec, SetList.get(videocount));
            ++currvideo;
            nextex.setImageResource(nextExList.get(videocount));
            //String s=Integer.toString(currvideo);
            //Log.i("butter",s);
            if (currvideo == videolist.size())
                currvideo = 0;


            setVideo(videolist.get(currvideo), koreanName.get(currvideo)); }//currvideo??? ????????? ???????????? ?????? index?????? ???????????? ??????
    }

    public void countDown(String time, String set) {

        set_int = 0;
        long conversionTime = 0;
        String originalTime = time;
        int setCount = Integer.parseInt(set);

        // 1000 ????????? 1???
        // 60000 ????????? 1???
        // 60000 * 3600 = 1??????

        TextView textview_second = (TextView) findViewById(R.id.second);
        TextView textview_set = (TextView) findViewById(R.id.setCount);
        TextView secondtxt = (TextView) findViewById(R.id.secondtxt);
        TextView settxt = (TextView) findViewById(R.id.settxt);
        TextView fighting = (TextView) findViewById(R.id.youcandoit);
        TextView exname = (TextView) findViewById(R.id.exname);
        ImageView nextbtn=(ImageView)findViewById(R.id.nextbtn);
        ImageView rest=(ImageView)findViewById(R.id.rest);

       // nextex.setImageResource(nextExList.get(videocount));

        textview_second.setVisibility(View.VISIBLE);
        textview_set.setVisibility(View.VISIBLE);
        secondtxt.setVisibility(View.VISIBLE);
        settxt.setVisibility(View.VISIBLE);
        fighting.setVisibility(View.VISIBLE);
        exname.setVisibility(View.VISIBLE);
        nextbtn.setVisibility(View.VISIBLE);
        rest.setVisibility(View.INVISIBLE);
        textview_set.setText(set);


        if(videocount%2==1) {
            textview_second.setVisibility(View.INVISIBLE);
            textview_set.setVisibility(View.INVISIBLE);
            secondtxt.setVisibility(View.INVISIBLE);
            settxt.setVisibility(View.INVISIBLE);
            fighting.setVisibility(View.INVISIBLE);
            exname.setVisibility(View.INVISIBLE);
            rest.setVisibility(View.VISIBLE);
            nextbtn.setVisibility(View.INVISIBLE);
        }

        // "00"??? ?????????, ????????? ????????? 0 ?????? ??????
        if (time.substring(0, 1) == "0") {
            time = time.substring(1, 2);
        }

        // ????????????
        conversionTime = Long.valueOf(time) * 1000;

        // ????????? ?????? : ????????? ?????? (???????????? 30?????? 30 x 1000(??????))
        // ????????? ?????? : ??????( 1000 = 1???)
        // ?????? ???????????? ??? ??????
        // ?????????
        // ?????????
        // ?????? ???????????? 0??? ?????????
        // ???????????? ?????????
        // ?????? ???
        //?????? ?????? ???????????? ????????? (????????? 0??? ?????? ?????? ???????????? ????????????)
        //????????? 0??? ?????? ?????????????????? ????????????
        timer = new CountDownTimer(conversionTime, 1000) {

            // ?????? ???????????? ??? ??????
            @Override
            public void onTick(long time) {

                // ?????????
                String second = String.valueOf((time % (60 * 1000)) / 1000); // ?????????

                // ?????? ???????????? 0??? ?????????
                if (second.length() == 1) {
                    second = "0" + second;
                }
                textview_second.setText(second);
            }

            // ???????????? ?????????
            @Override
            public void onFinish() {

                // ?????? ???
                //?????? ?????? ???????????? ????????? (????????? 0??? ?????? ?????? ???????????? ????????????)
                textview_set.setText(String.valueOf(setCount - 1));

                if ((setCount - 1) == 0) { //????????? 0??? ?????? ?????????????????? ????????????
                    pauseTimer();
                    videocount++;
                    onCompletion(mediapalyer);

                }
                else{countDown(originalTime, String.valueOf(setCount - 1));}

            }
        }.start();

    }
    private void pauseTimer() {
        timer.cancel();
        mTimerRunning = false;
    }
    private int done()
    {
        return 1;
    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("log");
        mReference.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }
    private void initDatabase2() {

        mDatabase2 = FirebaseDatabase.getInstance();

        mReference2 = mDatabase2.getReference("log");
        mReference2.child("log").setValue("check");

        mChild2 = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference2.addChildEventListener(mChild2);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    private class next {
    }
}