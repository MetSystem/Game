package edu.cgxy.link.board;

import edu.cgxy.link.object.LinkInfo;
import edu.cgxy.link.view.Piece;

/**
 * Description: ��Ϸ�߼��ӿ�
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public interface GameService
{
	/**
	 * ������Ϸ��ʼ�ķ���
	 */
	void start();

	/**
	 * ����һ���ӿڷ���, ���ڷ���һ����ά����
	 *
	 * @return ��ŷ������Ķ�ά����
	 */
	Piece[][] getPieces();

	/**
	 * �жϲ���Piece[][]�������Ƿ񻹴��ڷǿյ�Piece����
	 *
	 * @return �����ʣPiece���󷵻�true, û�з���false
	 */
	boolean hasPieces();

	/**
	 * ��������x�����y����, ���ҳ�һ��Piece����
	 *
	 * @param touchX �������x����
	 * @param touchY �������y����
	 * @return ���ض�Ӧ��Piece����, û�з���null
	 */
	Piece findPiece(float touchX, float touchY);

	/**
	 * �ж�����Piece�Ƿ��������, ��������, ����LinkInfo����
	 *
	 * @param p1 ��һ��Piece����
	 * @param p2 �ڶ���Piece����
	 * @return �����������������LinkInfo����, �������Piece����������, ����null
	 */
	LinkInfo link(Piece p1, Piece p2);
}
