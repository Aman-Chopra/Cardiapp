package com.example.win_8.cardigram;

import java.io.Serializable;

/**
 * Created by win-8 on 06-04-2017.
 */

public class ImageUpload implements Serializable {
	public String url;

	public String getUrl() {
		return url;
	}
	public ImageUpload(String url)
	{
		this.url = url;
	}
	public ImageUpload()
	{}
}