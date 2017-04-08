package com.example.win_8.cardigram;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Group extends AppCompatActivity {
	private FirebaseListAdapter<GroupMessage> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		FloatingActionButton fab =
				(FloatingActionButton)findViewById(R.id.fab);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				EditText input = (EditText)findViewById(R.id.input);

				// Read the input field and push a new instance
				// of ChatMessage to the Firebase database
				FirebaseDatabase.getInstance()
						.getReference()
						.push()
						.setValue(new GroupMessage(input.getText().toString(),
								FirebaseAuth.getInstance()
										.getCurrentUser()
										.getEmail())
						);

				// Clear the input
				input.setText("");
			}
		});
		//display();
	}

	public void display()
	{
		ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

		adapter = new FirebaseListAdapter<GroupMessage>(this, GroupMessage.class,
				R.layout.message, FirebaseDatabase.getInstance().getReference()) {
			@Override
			protected void populateView(View v, GroupMessage model, int position) {
				// Get references to the views of message.xml
				TextView messageText = (TextView)v.findViewById(R.id.message_text);
				TextView messageUser = (TextView)v.findViewById(R.id.message_user);
				TextView messageTime = (TextView)v.findViewById(R.id.message_time);

				// Set their text
				messageText.setText(model.getMessageText());
				messageUser.setText(model.getMessageUser());

				// Format the date before showing it
				messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
						model.getMessageTime()));
			}
		};

		listOfMessages.setAdapter(adapter);
	}
}
