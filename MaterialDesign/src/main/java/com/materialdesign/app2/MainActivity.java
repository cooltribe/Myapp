package com.materialdesign.app2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    private ImageView imageView;
    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        imageView = (ImageView) findViewById(R.id.image);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(view);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change1(imageView);
            }
        });
    }
    private void change(View view){
        int centerX = imageView.getWidth()/2;
        int centerY = imageView.getHeight() / 2;
        int maxRadius = Math.max(imageView.getWidth(),imageView.getHeight());
        if (imageView.getVisibility() == View.VISIBLE){
            Animator anim = ViewAnimationUtils.createCircularReveal(imageView,centerX,centerY,maxRadius,0);
            anim.setDuration(1000);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    imageView.setVisibility(View.GONE);
                }
            });
            anim.start();
        } else {
            Animator anim = ViewAnimationUtils.createCircularReveal(imageView,centerX,centerY,0,maxRadius);
            anim.setDuration(1000);
            imageView.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

    private void change1(View view){
        Path path = new Path();
        path.moveTo(100,100);
        path.lineTo(600,100);
        path.lineTo(100,600);
        path.lineTo(600,600);
        path.close();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,View.X,View.Y,path);
        animator.setDuration(5000);
        animator.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
