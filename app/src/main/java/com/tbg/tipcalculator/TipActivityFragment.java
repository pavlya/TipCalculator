package com.tbg.tipcalculator;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;


/**
 * A placeholder fragment containing a simple view.
 */
public class TipActivityFragment extends Fragment {

    // Currencies and percent Formatters
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();
    EditText etAmount;
    SeekBar sbPercent;
    TextView tvCustomTipPercent;
    TextView tvTotal;
    TextView tvTip;
    TextView tvTotalCustom;
    TextView tvTipCustom;
    private double standardPercent = 0.1;
    private double customPercent = 0.1;
    int progress = 0;
    private double billAmount = 0;

    public TipActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_tip_layout, container, false);
        etAmount = (EditText) fragmentView.findViewById(R.id.et_amount);
        tvCustomTipPercent = (TextView) fragmentView.findViewById(R.id.tv_custom_percent);
        tvTotal = (TextView)fragmentView.findViewById(R.id.tv_total_amount);
        tvTip = (TextView)fragmentView.findViewById(R.id.tv_tip_amount);
        tvTotalCustom = (TextView)fragmentView.findViewById(R.id.tv_total_amount_custom);
        tvTipCustom = (TextView)fragmentView.findViewById(R.id.tv_tip_amount_custom);
        sbPercent = (SeekBar)fragmentView.findViewById(R.id.sb_customTip);
        sbPercent.setOnSeekBarChangeListener(tipSeekBarChangeListener);
        // current progress of seekBar
        progress = sbPercent.getProgress();
        tvCustomTipPercent.setText(String.valueOf(progress) + "%");
        etAmount.addTextChangedListener(amountEditText);
        // updateFields
        updateStandard();
        updateCustom();

        return fragmentView;
    }

    private void updateStandard() {
        double tenPercentTip = billAmount * standardPercent;
        double tenPercentTotal = billAmount + tenPercentTip;

        // Show tips and total sum
        tvTip.setText(currencyFormat.format(tenPercentTip));
        tvTotal.setText(currencyFormat.format(tenPercentTotal));
    }

    private void updateCustom() {
        tvCustomTipPercent.setText(String.valueOf(progress) + "%");
        double customPercentTip = billAmount * customPercent;
        double customPercentTotal = billAmount + customPercentTip;

        // Show tips and total sum
        tvTipCustom.setText(currencyFormat.format(customPercentTip));
        tvTotalCustom.setText(currencyFormat.format(customPercentTotal));
    }

    private TextWatcher amountEditText = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // convert to double
            try {
//                billAmount = Double.parseDouble(charSequence.toString()) / 100;
                // dont use cent's
                billAmount = Double.parseDouble(charSequence.toString());
            } catch (Exception e){
                billAmount = 0.0; // For exceptions handling
            }

            // display currency formatted bill amount
            updateCustom();
            updateStandard();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

   SeekBar.OnSeekBarChangeListener tipSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            customPercent = (double)progress / 100;
            TipActivityFragment.this.progress = progress;
            updateCustom();
            updateStandard();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


}
