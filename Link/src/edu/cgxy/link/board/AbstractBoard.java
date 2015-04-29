package edu.cgxy.link.board;

import java.util.List;

import edu.cgxy.link.object.GameConf;
import edu.cgxy.link.util.ImageUtil;
import edu.cgxy.link.view.Piece;
import edu.cgxy.link.view.PieceImage;

/**
 * Description: ��Ϸ����ĳ�����
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public abstract class AbstractBoard
{
	// ����һ�����󷽷�, ������ȥʵ��
	protected abstract List<Piece> createPieces(GameConf config,
		Piece[][] pieces);

	public Piece[][] create(GameConf config)
	{
		// ����Piece[][]����
		Piece[][] pieces = new Piece[config.getXSize()][config.getYSize()];
		// ���طǿյ�Piece����, �ü���������ȥ����
		List<Piece> notNullPieces = createPieces(config, pieces);
		// ���ݷǿ�Piece����ļ��ϵĴ�С��ȡͼƬ
		List<PieceImage> playImages = ImageUtil.getPlayImages(config.getContext(),
			notNullPieces.size());
		// ����ͼƬ�Ŀ��߶�����ͬ��
		int imageWidth = playImages.get(0).getImage().getWidth();
		int imageHeight = playImages.get(0).getImage().getHeight();
		// �����ǿյ�Piece����
		for (int i = 0; i < notNullPieces.size(); i++)
		{
			// ���λ�ȡÿ��Piece����
			Piece piece = notNullPieces.get(i);
			piece.setImage(playImages.get(i));
			// ����ÿ���������Ͻǵ�X��Y����
			piece.setBeginX(piece.getIndexX() * imageWidth
				+ config.getBeginImageX());
			piece.setBeginY(piece.getIndexY() * imageHeight
				+ config.getBeginImageY());
			// ���÷��������뷽���������Ӧλ�ô�
			pieces[piece.getIndexX()][piece.getIndexY()] = piece;
		}
		return pieces;
	}
}
