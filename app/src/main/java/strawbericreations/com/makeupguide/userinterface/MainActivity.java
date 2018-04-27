package strawbericreations.com.makeupguide.userinterface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import strawbericreations.com.makeupguide.R;

public class MainActivity extends AppCompatActivity {

    Intent dataIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataIntent = new Intent(this,VideosActivity.class);


    }

    public void eyesClick(View view){
        dataIntent.putExtra("keyword","eyemakeup");
        startActivity(dataIntent);

    }

    public void faceClick(View view){
        dataIntent.putExtra("keyword","facemakeup");
        startActivity(dataIntent);
    }

    public void lipClick(View view){
        dataIntent.putExtra("keyword","lipsmakeup");
        startActivity(dataIntent);
    }
}
