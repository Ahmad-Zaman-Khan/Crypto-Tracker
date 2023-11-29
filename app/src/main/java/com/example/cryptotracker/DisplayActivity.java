package com.example.cryptotracker;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DisplayActivity extends AppCompatActivity {

    private BigDecimal million = new BigDecimal(1000000);
    private MathContext mathContext = new MathContext(0, RoundingMode.HALF_UP);


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Note workorder = (Note) extras.getSerializable("Key");
            if (workorder != null) {
                int id = workorder.getId();
                Data data = workorder.getMeanings().get(id);

                if (data != null) {
                    USD jsonData = data.getQuote().getUSD();
                    setTitle(data.getName());
                    TextView textView7 = findViewById(R.id.textView7);
                    TextView textView2 = findViewById(R.id.textView2);
                    TextView textView4 = findViewById(R.id.textView4);
                    TextView tvVolumeQuantity = findViewById(R.id.tvVolumeQuantity);
                    TextView Volume24 = findViewById(R.id.tvAvgVolumeAmount);
                    TextView tvAvgMarketCapAmount = findViewById(R.id.tvAvgMarketCapAmount);
                    TextView tvSupplyNumber = findViewById(R.id.tvSupplyNumber);
                    TextView change1h = findViewById(R.id.Change1h);
                    TextView change24h = findViewById(R.id.Change24h);
                    TextView change7d = findViewById(R.id.Change7d);
                    TextView change30d = findViewById(R.id.Change30d);

                    double price = jsonData.getPrice();
                    double percentChange = jsonData.getPercentChange24h();
                    double numericChange = (percentChange / 100) * price;

                    textView4.setTextColor(
                            Color.parseColor(percentChange < 0 ? "#ff0000" : "#32CD32")
                    );
                    DecimalFormat str3 = (DecimalFormat) DecimalFormat.getInstance();
                    str3.setMaximumFractionDigits(2);

                    NumberFormat str = NumberFormat.getCurrencyInstance();
                    NumberFormat strp = NumberFormat.getInstance();
                    strp.setMaximumFractionDigits(2);

                    textView2.setText(str.format(price));
                    textView4.setText(str3.format(numericChange) + "    " + "(" +str3.format(percentChange) + "%)");
                    textView7.setText("Past day");

                    BigDecimal volume = new BigDecimal(jsonData.getVolume24h());
                    BigDecimal volume24 = new BigDecimal(jsonData.getVolumeChange24h());
                    BigDecimal mktcap = new BigDecimal(jsonData.getMarketCap());
                    BigDecimal supply = new BigDecimal(data.getTotalSupply());

                    BigDecimal rvolume = volume.divide(million, mathContext);
                    BigDecimal rmktcap = mktcap.divide(million, mathContext);
                    BigDecimal rsupply = supply.divide(million, mathContext);

                    double volume24digits = Double.parseDouble(String.format("%.1f", volume24));
                    double supply2digits = Double.parseDouble(String.format("%.1f", rsupply));
                    double volume2digits = Double.parseDouble(String.format("%.1f", rvolume));
                    double mktcap2digits = Double.parseDouble(String.format("%.1f", rmktcap));



                    Volume24.setText(String.format("%.1f%%", volume24digits));
                    tvVolumeQuantity.setText(volume2digits + "M");
                    tvAvgMarketCapAmount.setText(mktcap2digits + "M");
                    tvSupplyNumber.setText(supply2digits + "M");

                    change1h.setText(strp.format(jsonData.getPercentChange1h()) + "%");
                    change24h.setText(strp.format(jsonData.getPercentChange24h()) + "%");
                    change7d.setText(strp.format(jsonData.getPercentChange7d()) + "%");
                    change30d.setText(strp.format(jsonData.getPercentChange30d()) + "%");
                }
            }
        }
    }
}
