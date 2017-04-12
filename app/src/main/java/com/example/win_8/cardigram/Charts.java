package com.example.win_8.cardigram;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Charts extends ActionBarActivity {
	int flag = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charts);
		final BarChart chart = (BarChart) findViewById(R.id.chart);


		BarData data = new BarData(getXAxisValues(), getDataSet());
		chart.setData(data);
		chart.setDescription("Haemoglobin");
		int maxCapacity = 13;
		LimitLine ll = new LimitLine(maxCapacity, "Max Capacity");
		chart.getAxisLeft().addLimitLine(ll);

		chart.animateXY(2000, 2000);

		Button b = (Button)findViewById(R.id.save) ;
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				chart.saveToGallery("mychart.jpg", 85);
//				AlertDialog.Builder builder = new AlertDialog.Builder(getApplication(),R.style.AppCompatAlertDialogStyle);
//				builder.setTitle("Saved!");
//				builder.setMessage("Chart saved Successfully");
//				builder.setPositiveButton("OK", null);
//				builder.show();
				flag=1;
			}
		});
		if(flag==1)
		{




			AlertDialog.Builder builder = new AlertDialog.Builder(getApplication(),R.style.AppCompatAlertDialogStyle);
				builder.setTitle("Saved!");
				builder.setMessage("Chart saved Successfully");

				builder.setPositiveButton("OK", null);
				builder.show();
		}

		chart.invalidate();

	}

	private ArrayList<BarDataSet> getDataSet() {
		ArrayList<BarDataSet> dataSets = null;

		ArrayList<BarEntry> valueSet1 = new ArrayList<>();
		BarEntry v1e1 = new BarEntry(13, 0); // Jan
		valueSet1.add(v1e1);
		BarEntry v1e2 = new BarEntry(13, 1); // Feb
		valueSet1.add(v1e2);
		BarEntry v1e3 = new BarEntry(13, 2); // Mar
		valueSet1.add(v1e3);
		BarEntry v1e4 = new BarEntry(13, 3); // Apr
		valueSet1.add(v1e4);
		BarEntry v1e5 = new BarEntry(13, 4); // May
		valueSet1.add(v1e5);


		ArrayList<BarEntry> valueSet2 = new ArrayList<>();
		BarEntry v2e1 = new BarEntry(12, 0); // Jan
		valueSet2.add(v2e1);
		BarEntry v2e2 = new BarEntry(13, 1); // Feb
		valueSet2.add(v2e2);
		BarEntry v2e3 = new BarEntry(14, 2); // Mar
		valueSet2.add(v2e3);
		BarEntry v2e4 = new BarEntry(11, 3); // Apr
		valueSet2.add(v2e4);
		BarEntry v2e5 = new BarEntry(12, 4); // May
		valueSet2.add(v2e5);


		BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Normal");
		barDataSet1.setColor(Color.rgb(0, 155, 0));
		BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Observed");
		barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

		dataSets = new ArrayList<>();
		dataSets.add(barDataSet1);
		dataSets.add(barDataSet2);
		return dataSets;
	}

	private ArrayList<String> getXAxisValues() {
		ArrayList<String> xAxis = new ArrayList<>();
		xAxis.add("FIRST");
		xAxis.add("SECOND");
		xAxis.add("THIRD");
		xAxis.add("FOURTH");
		xAxis.add("FIFTH");

		return xAxis;
	}
}