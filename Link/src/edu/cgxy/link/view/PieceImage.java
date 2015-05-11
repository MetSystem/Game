package edu.cgxy.link.view;

import android.graphics.Bitmap;

public class PieceImage
{
	private Bitmap image;
	private int imageId;
	public PieceImage(Bitmap image, int imageId)
	{
		super();
		this.image = image;
		this.imageId = imageId;
	}
	public Bitmap getImage()
	{
		return image;
	}
	public void setImage(Bitmap image)
	{
		this.image = image;
	}
	public int getImageId()
	{
		return imageId;
	}
	public void setImageId(int imageId)
	{
		this.imageId = imageId;
	}
    public int sub(int a, int b) {
        return a - b;
    }
}
