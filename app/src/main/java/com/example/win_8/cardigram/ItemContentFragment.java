package com.example.win_8.cardigram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * Provides UI for the view with List.
 */
public class ItemContentFragment extends Fragment {
	private View myFragmentView;
	Button generate;
	public String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};
	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		//View view=inflater.inflate(R.layout.item_list, container, false);
		View view = inflater.inflate(R.layout.chartinput, container,false);
		generate = (Button)view.findViewById(R.id.btn_signup);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
		MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
				view.findViewById(R.id.android_material_design_spinner);
		materialDesignSpinner.setAdapter(arrayAdapter);
		Toast.makeText(getContext(),"yoo",Toast.LENGTH_SHORT).show();
		materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			                           int arg2, long arg3) {
				int select = arg2;
				Log.d("abcd","akkhbfk");
				Toast.makeText(getContext(),"a"+select,Toast.LENGTH_SHORT).show();
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});




		generate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), Charts.class);
				startActivity(intent);

			}
		});







		return view;
		//myFragmentView = inflater.inflate(R.layout.item_list, container, false);
		//TextView a = getView().findViewById(R.id.textView);
		//Typewriter writer = (Typewriter)findViewById(R.id.textView);
		//Add a character every 150ms
		//writer.setCharacterDelay(150);
		//writer.animateText("Sample String");
	}




}
