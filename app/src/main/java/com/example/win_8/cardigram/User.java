package com.example.win_8.cardigram;

/**
 * Created by win-8 on 11-04-2017.
 */

public class User {
	public String username;
	public String email;
	public String imagemap;


	public User() {
	}

	public User(String username, String email, String imagemap) {
		this.username = username;
		this.email = email;
		this.imagemap = imagemap;
	}
	public String getname()
	{
		return username;
	}
	public String getimage()
	{
		return imagemap;
	}
}
