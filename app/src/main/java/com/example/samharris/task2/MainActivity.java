package com.example.samharris.task2;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    final static int confirm = 97;

    RelativeLayout.LayoutParams mparam;

    ImageView i;

    RelativeLayout myLayout;

    Button bVoice;

    TextView tv;

    private int topMargin,bottomMargin,leftMargin,rightMargin;

    private static int dispHt;

    private static int dispWd;

    private int imgDim = 100;

    private void changeImg(int key)
    {
        if(key==0)
        {
            i.setImageResource(R.drawable.sq_blue);
        }
        if(key==1)
        {
            i.setImageResource(R.drawable.circle_blue);
        }
        if(key==2)
        {
            i.setImageResource(R.drawable.triangle_blue);
        }
    }

    private void scaleImg(boolean ifZoom)
    {

        if(ifZoom==true&&mparam.width+10<dispWd*.7)
        {
            mparam.width +=200;
            imgDim+=200;
        }

        if(ifZoom==false&&mparam.width-10>25)
        {
            mparam.width -=100;
            imgDim-=100;
        }

        i.setLayoutParams(mparam);

    }

    private void moveImg(boolean ifDefault,int top,int bottom,int left, int right)
    {
        if(ifDefault==true)
        {
            topMargin = top = dispHt/2 - 2*imgDim ;
            bottomMargin = bottom = 0;
            leftMargin = left =dispWd/2 - imgDim;
            rightMargin = right = 0;

            setImgParams(top, bottom, left, right);
        }

        else

        {


            if(top>dispHt-420)
               topMargin = top  = dispHt/2  -2*imgDim;
            if(top<dispHt/2-2*imgDim)
                topMargin = top  = dispHt - 400 -imgDim;
            if(left>dispWd-40)
                leftMargin = left  = 50;
            if(left<20)
                leftMargin = left  = dispWd - 50 - imgDim;

            setImgParams(top, bottom, left, right);

        }
    }

    private void setImgParams(int top,int bottom,int left, int right)
    {
        mparam = new RelativeLayout.LayoutParams(imgDim,imgDim);

        mparam.leftMargin = left;

        mparam.topMargin = top;

        mparam.bottomMargin = bottom;

        mparam.rightMargin = right;

        i.setLayoutParams(mparam);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        dispHt=metrics.heightPixels;
        dispWd=metrics.widthPixels;

        myLayout = (RelativeLayout)findViewById(R.id.mainRelLayout);

        bVoice=(Button)findViewById(R.id.bVoice);

        i = new ImageView(this);

        i.setImageResource(R.drawable.sq_blue);

        myLayout.addView(i);

        moveImg(true,0,0,0,0);

        bVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Listening");
                startActivityForResult(i, confirm);
            }
        });

    }

    //To display about page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.aboutMenu) {
            Intent settingsIntent = new Intent(this, AboutPage.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        tv = (TextView)findViewById(R.id.tv);

        String str;

        if(requestCode==confirm&&resultCode==RESULT_OK)
        {
            myLayout = (RelativeLayout)findViewById(R.id.mainRelLayout);

            ArrayList<String> textList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            str = textList.get(0);

            tv.setText(str);

            if((Objects.equals("up", str))||(Objects.equals("Up", str)))
            {
                topMargin-=100;
                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }
            if((Objects.equals("down", str))||(Objects.equals("Down", str)))
            {
               topMargin+=100;
                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }
            if((Objects.equals("left", str))||(Objects.equals("Left", str)))
            {
                leftMargin-=100;
                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }
            if((Objects.equals("right", str))||(Objects.equals("Right", str)))
            {
                leftMargin+=100;
                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }

            if((Objects.equals("grow", str))||(Objects.equals("Grow", str)))
            {
                scaleImg(true);

                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }

            if((Objects.equals("shrink", str))||(Objects.equals("Shrink", str)))
            {
                scaleImg(false);
                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }

            if((Objects.equals("circle", str))||(Objects.equals("Circle", str)))
            {
                changeImg(1);
                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }

            if((Objects.equals("triangle", str))||(Objects.equals("Triangle", str)))
            {
                changeImg(2);
                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }

            if((Objects.equals("square", str))||(Objects.equals("Square", str)))
            {
                changeImg(0);
                moveImg(false, topMargin, bottomMargin, leftMargin, rightMargin);
            }

        }
    }
}
