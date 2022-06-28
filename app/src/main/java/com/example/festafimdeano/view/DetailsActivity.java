package com.example.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.festafimdeano.R;
import com.example.festafimdeano.constante.FimDeAnoConstantes;
import com.example.festafimdeano.data.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.checkParticipate = findViewById(R.id.check_participate);
        this.mViewHolder.checkParticipate.setOnClickListener(this);

        this.loadDataFromActivity();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.check_participate){

            if (this.mViewHolder.checkParticipate.isChecked()){
                //salvar a presença
                this.mSecurityPreferences.storeString(FimDeAnoConstantes.PRESENCE_KEY, FimDeAnoConstantes.CONFIRMATION_YES);
            }else {
                //salvar a Ausencia
                this.mSecurityPreferences.storeString(FimDeAnoConstantes.PRESENCE_KEY, FimDeAnoConstantes.CONFIRMATION_NO);
            }

        }
    }

    private void loadDataFromActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String presence = extras.getString(FimDeAnoConstantes.PRESENCE_KEY);
            if (presence != null && presence.equals(FimDeAnoConstantes.CONFIRMATION_YES)){
                this.mViewHolder.checkParticipate.setChecked(true);
            }else {
                this.mViewHolder.checkParticipate.setChecked(false);
            }
        }
    }

    private static class ViewHolder {
        CheckBox checkParticipate;
    }
}