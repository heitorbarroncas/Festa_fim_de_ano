package com.example.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.festafimdeano.R;
import com.example.festafimdeano.constante.FimDeAnoConstantes;
import com.example.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static SimpleDateFormat SIMPLE_DATE_FORMATE = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textoToday = findViewById(R.id.texto_today);
        this.mViewHolder.textDaysLeft = findViewById(R.id.text_days_left);
        this.mViewHolder.buttonConfirm = findViewById(R.id.button_Confirm);

        this.mViewHolder.buttonConfirm.setOnClickListener(this);

        this.mViewHolder.textoToday.setText(SIMPLE_DATE_FORMATE.format(Calendar.getInstance().getTime()));
        String daysLeft = String.format("%s %s", String.valueOf(this.getDaysLeft()), getString(R.string.dias));
        this.mViewHolder.textDaysLeft.setText(daysLeft);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_Confirm) {

            String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstantes.PRESENCE_KEY);

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstantes.PRESENCE_KEY,presence);
            startActivity(intent);
        }
    }

    private void verifyPresence() {
        String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstantes.PRESENCE_KEY);
        if (presence.equals("")) {
            this.mViewHolder.buttonConfirm.setText(getString(R.string.nao_confirmado));
        } else if (presence.equals(FimDeAnoConstantes.CONFIRMATION_YES)) {
            this.mViewHolder.buttonConfirm.setText(getString(R.string.sim));
        } else {
            this.mViewHolder.buttonConfirm.setText(getString(R.string.nao));
        }
    }

    private int getDaysLeft() {
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        Calendar calendarLastDay = Calendar.getInstance();
        int dayMax = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return dayMax - today;
    }

    private static class ViewHolder {
        TextView textoToday;
        TextView textDaysLeft;
        Button buttonConfirm;
    }
}