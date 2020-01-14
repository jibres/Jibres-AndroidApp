package com.jibres.android.activity.tiket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jibres.android.R;
import com.jibres.android.activity.tiket.api.TiketApi;
import com.jibres.android.activity.tiket.api.TiketListener;

public class TiketAddActivity extends AppCompatActivity {
    EditText editMassage;
    TextView desc;

    String title_Toolbar = "Send Request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_add);

        if (getIntent().getStringExtra("title")!=null){
            title_Toolbar = getIntent().getStringExtra("title");
        }

        desc = findViewById(R.id.desc);
        editMassage = findViewById(R.id.edit_massage);

        View toolbarView = findViewById(R.id.toolbar);
        Toolbar toolbar = toolbarView.findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);
        mTitle.setText(title_Toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void button_add_tiket(View view) {
        if (getMassage_edt()!=null){
            TiketApi.addTiket(getApplicationContext(), getTitle_edt(), getMassage_edt(),
                    new TiketListener.addTiket() {
                        @Override
                        public void onReceived(String massage, boolean isSend, String id) {
                            Toast.makeText(TiketAddActivity.this, massage, Toast.LENGTH_SHORT).show();
                            if (isSend && id != null){
                                Intent intent = new Intent(getApplicationContext(),
                                        TiketViewActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFiled(boolean hasNet) {

                        }
                    });
        }
    }


    String getTitle_edt(){
        return title_Toolbar;
    }
    String getMassage_edt(){
        if (editMassage.getText().toString().length() >1){
            return editMassage.getText().toString();
        }
        return null;
    }
}
