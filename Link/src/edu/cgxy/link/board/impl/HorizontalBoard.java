package edu.cgxy.link.board.impl;

import java.util.ArrayList;
import java.util.List;

import edu.cgxy.link.board.AbstractBoard;
import edu.cgxy.link.object.GameConf;
import edu.cgxy.link.view.Piece;

/**
 * Description: ���������Ϸ����
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class HorizontalBoard extends AbstractBoard
{
	protected List<Piece> createPieces(GameConf config,
		Piece[][] pieces)
	{
		// ����һ��Piece����, �ü��������ų�ʼ����Ϸʱ�����Piece����
		List<Piece> notNullPieces = new ArrayList<Piece>();
		for (int i = 0; i < pieces.length; i++)
		{
			for (int j = 0; j < pieces[i].length; j++)
			{
				// �����ж�, ����һ��������ȥ����Piece����, ���ӵ�������
				if (j % 2 == 0)
				{
					// ���x�ܱ�2����, �������в��ᴴ������
					// �ȹ���һ��Piece����, ֻ��������Piece[][]�����е�����ֵ��
					// ����Ҫ��PieceImage���丸�ฺ�����á�
					Piece piece = new Piece(i, j);
					// ��ӵ�Piece������
					notNullPieces.add(piece);
				}
			}
		}
		return notNullPieces;
	}
}
