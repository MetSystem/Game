package edu.cgxy.link.view;

import java.util.List;

import edu.cgxy.link.R;
import edu.cgxy.link.board.GameService;
import edu.cgxy.link.object.LinkInfo;

import android.graphics.Point;
import edu.cgxy.link.util.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description: <br/>
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class GameView extends View
{
	// ��Ϸ�߼���ʵ����
	private GameService gameService;
	// ���浱ǰ�Ѿ���ѡ�еķ���
	private Piece selectedPiece;
	// ������Ϣ����
	private LinkInfo linkInfo;
	private Paint paint;
	// ѡ�б�ʶ��ͼƬ����
	private Bitmap selectImage;

	public GameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.paint = new Paint();
		// ʹ��λͼƽ����Ϊ��������
		this.paint.setShader(new BitmapShader(BitmapFactory
			.decodeResource(context.getResources(), R.drawable.heart)
			, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
		// ���������ߵĴ�ϸ
		this.paint.setStrokeWidth(9);
		this.selectImage = ImageUtil.getSelectImage(context);
	}

	public void setLinkInfo(LinkInfo linkInfo)
	{
		this.linkInfo = linkInfo;
	}

	public void setGameService(GameService gameService)
	{
		this.gameService = gameService;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (this.gameService == null)
			return;
		Piece[][] pieces = gameService.getPieces();
		if (pieces != null)
		{
			// ����pieces��ά����
			for (int i = 0; i < pieces.length; i++)
			{
				for (int j = 0; j < pieces[i].length; j++)
				{
					// �����ά�����и�Ԫ�ز�Ϊ�գ����з��飩������������ͼƬ������
					if (pieces[i][j] != null)
					{
						// �õ����Piece����
						Piece piece = pieces[i][j];
						// ���ݷ������Ͻ�X��Y������Ʒ���
						canvas.drawBitmap(piece.getImage().getImage(),
							piece.getBeginX(), piece.getBeginY(), null);
					}
				}
			}
		}
		// �����ǰ��������linkInfo����, ��������Ϣ
		if (this.linkInfo != null)
		{
			// ����������
			drawLine(this.linkInfo, canvas);
			// ����������linkInfo����
			this.linkInfo = null;
		}
		// ��ѡ�б�ʶ��ͼƬ
		if (this.selectedPiece != null)
		{
			canvas.drawBitmap(this.selectImage, this.selectedPiece.getBeginX(),
				this.selectedPiece.getBeginY(), null);
		}
	}

	// ����LinkInfo���������ߵķ�����
	private void drawLine(LinkInfo linkInfo, Canvas canvas)
	{
		// ��ȡLinkInfo�з�װ���������ӵ�
		List<Point> points = linkInfo.getLinkPoints();
		// ���α���linkInfo�е�ÿ�����ӵ�
		for (int i = 0; i < points.size() - 1; i++)
		{
			// ��ȡ��ǰ���ӵ�����һ�����ӵ�
			Point currentPoint = points.get(i);
			Point nextPoint = points.get(i + 1);
			// ��������
			canvas.drawLine(currentPoint.x , currentPoint.y,
				nextPoint.x, nextPoint.y, this.paint);
		}
	}

	// ���õ�ǰѡ�з���ķ���
	public void setSelectedPiece(Piece piece)
	{
		this.selectedPiece = piece;
	}

	// ��ʼ��Ϸ����
	public void startGame()
	{
		this.gameService.start();
		this.postInvalidate();
	}
}
