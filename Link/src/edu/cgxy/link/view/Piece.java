package edu.cgxy.link.view;

import android.graphics.Point;

/**
 * Description: ��������Ϸ�еķ������
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class Piece
{
	// ���淽����������Ӧ��ͼƬ
	private PieceImage image;
	// �÷�������Ͻǵ�x����
	private int beginX;
	// �÷�������Ͻǵ�y����
	private int beginY;
	// �ö�����Piece[][]�����е�һά������ֵ
	private int indexX;
	// �ö�����Piece[][]�����еڶ�ά������ֵ
	private int indexY;

	// ֻ���ø�Piece�����������е�����ֵ
	public Piece(int indexX , int indexY)
	{
		this.indexX = indexX;
		this.indexY = indexY;
	}

	public int getBeginX()
	{
		return beginX;
	}

	public void setBeginX(int beginX)
	{
		this.beginX = beginX;
	}

	public int getBeginY()
	{
		return beginY;
	}

	public void setBeginY(int beginY)
	{
		this.beginY = beginY;
	}

	public int getIndexX()
	{
		return indexX;
	}

	public void setIndexX(int indexX)
	{
		this.indexX = indexX;
	}

	public int getIndexY()
	{
		return indexY;
	}

	public void setIndexY(int indexY)
	{
		this.indexY = indexY;
	}


	public PieceImage getImage()
	{
		return image;
	}

	public void setImage(PieceImage image)
	{
		this.image = image;
	}

	// ��ȡ��Piece������
	public Point getCenter()
	{
		return new Point(getImage().getImage().getWidth() / 2
			+ getBeginX(), getBeginY()
			+ getImage().getImage().getHeight() / 2);
	}
	// �ж�����Piece�ϵ�ͼƬ�Ƿ���ͬ
	public boolean isSameImage(Piece other)
	{
		if (image == null)
		{
			if (other.image != null)
				return false;
		}
		// ֻҪPiece��װͼƬID��ͬ��������Ϊ����Piece��ȡ�
		return image.getImageId() == other.image.getImageId();
	}
}
