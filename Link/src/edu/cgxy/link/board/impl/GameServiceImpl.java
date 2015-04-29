package edu.cgxy.link.board.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.cgxy.link.board.AbstractBoard;
import edu.cgxy.link.board.GameService;
import edu.cgxy.link.object.GameConf;
import edu.cgxy.link.object.LinkInfo;
import edu.cgxy.link.view.Piece;

import android.graphics.Point;

/**
 * Description: ��Ϸ�߼���ʵ���� <br/>
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class GameServiceImpl implements GameService
{
	// ����һ��Piece[][]���飬ֻ�ṩgetter����
	private Piece[][] pieces;
	// ��Ϸ���ö���
	private GameConf config;

	public GameServiceImpl(GameConf config)
	{
		// ����Ϸ�����ö������ñ�����
		this.config = config;
	}

	@Override
	public void start()
	{
		// ����һ��AbstractBoard����
		AbstractBoard board = null;
		Random random = new Random();
		// ��ȡһ�������, ��ȡֵ0��1��2��3��ֵ��
		int index = random.nextInt(4);
		// �������AbstractBoard������ʵ��
		switch (index)
		{
			case 0:
				// 0����VerticalBoard(����)
				board = new VerticalBoard();
				break;
			case 1:
				// 1����HorizontalBoard(����)
				board = new HorizontalBoard();
				break;
			default:
				// Ĭ�Ϸ���FullBoard
				board = new FullBoard();
				break;
		}
		// ��ʼ��Piece[][]����
		this.pieces = board.create(config);
	}

	// ֱ�ӷ��ر������Piece[][]����
	@Override
	public Piece[][] getPieces()
	{
		return this.pieces;
	}

	// ʵ�ֽӿڵ�hasPieces����
	@Override
	public boolean hasPieces()
	{
		// ����Piece[][]�����ÿ��Ԫ��
		for (int i = 0; i < pieces.length; i++)
		{
			for (int j = 0; j < pieces[i].length; j++)
			{
				// ֻҪ����һ������Ԫ�ز�Ϊnull��Ҳ���ǻ�ʣ�зǿյ�Piece����
				if (pieces[i][j] != null)
				{
					return true;
				}
			}
		}
		return false;
	}

	// ���ݴ������λ�ò�����Ӧ�ķ���
	@Override
	public Piece findPiece(float touchX, float touchY)
	{
		// �����ڴ���Piece�����ʱ��, ��ÿ��Piece�Ŀ�ʼ�������
		// GameConf�����õ�beginImageX/beginImageYֵ, �������Ҫ��ȥ���ֵ
		int relativeX = (int) touchX - this.config.getBeginImageX();
		int relativeY = (int) touchY - this.config.getBeginImageY();
		// ���������ĵط���board�е�һ��ͼƬ�Ŀ�ʼx����Ϳ�ʼy����ҪС, ��û���ҵ���Ӧ�ķ���
		if (relativeX < 0 || relativeY < 0)
		{
			return null;
		}
		// ��ȡrelativeX������Piece[][]�����еĵ�һά������ֵ
		// �ڶ�������Ϊÿ��ͼƬ�Ŀ�
		int indexX = getIndex(relativeX, GameConf.PIECE_WIDTH);
		// ��ȡrelativeY������Piece[][]�����еĵڶ�ά������ֵ
		// �ڶ�������Ϊÿ��ͼƬ�ĸ�
		int indexY = getIndex(relativeY, GameConf.PIECE_HEIGHT);
		// �������������������С������С, ����null
		if (indexX < 0 || indexY < 0)
		{
			return null;
		}
		// ����������������������������(���ߵ���), ����null
		if (indexX >= this.config.getXSize()
			|| indexY >= this.config.getYSize())
		{
			return null;
		}
		// ����Piece[][]�����ָ��Ԫ��
		return this.pieces[indexX][indexY];
	}

	// ���߷���, ����relative������������Piece[][]����ĵ�һά
	// ��ڶ�ά������ֵ ��sizeΪÿ��ͼƬ�ߵĳ����߿�
	private int getIndex(int relative, int size)
	{
		// ��ʾ����relative���ڸ�������
		int index = -1;
		// ��������Ա߳�, û������, ������1
		// �������x����Ϊ20, �߿�Ϊ10, 20 % 10 û������,
		// indexΪ1, ���������е�����Ϊ1(�ڶ���Ԫ��)
		if (relative % size == 0)
		{
			index = relative / size - 1;
		}
		else
		{
			// ������, �������x����Ϊ21, �߿�Ϊ10, 21 % 10������, indexΪ2
			// ���������е�����Ϊ2(������Ԫ��)
			index = relative / size;
		}
		return index;
	}
	
	// ʵ�ֽӿڵ�link����
	@Override
	public LinkInfo link(Piece p1, Piece p2)
	{
		// ����Piece��ͬһ��, ��ѡ����ͬһ������, ����null
		if (p1.equals(p2))
			return null;
		// ���p1��ͼƬ��p2��ͼƬ����ͬ, �򷵻�null
		if (!p1.isSameImage(p2))
			return null;
		// ���p2��p1�����, ����Ҫ����ִ�б�����, ������������
		if (p2.getIndexX() < p1.getIndexX())
			return link(p2, p1);
		// ��ȡp1�����ĵ�
		Point p1Point = p1.getCenter();
		// ��ȡp2�����ĵ�
		Point p2Point = p2.getCenter();
		// �������Piece��ͬһ��
		if (p1.getIndexY() == p2.getIndexY())
		{
			// ������ͬһ�в���������
			if (!isXBlock(p1Point, p2Point, GameConf.PIECE_WIDTH))
			{
				return new LinkInfo(p1Point, p2Point);
			}
		}
		// �������Piece��ͬһ��
		if (p1.getIndexX() == p2.getIndexX())
		{
			if (!isYBlock(p1Point, p2Point, GameConf.PIECE_HEIGHT))
			{
				// ����֮��û������ϰ�, û��ת�۵�
				return new LinkInfo(p1Point, p2Point);
			}
		}
		// ��һ��ת�۵�����
		// ��ȡ�������ֱ�������ĵ�, ��ֻ��һ��ת�۵�
		Point cornerPoint = getCornerPoint(p1Point, p2Point,
			GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT);
		if (cornerPoint != null)
		{
			return new LinkInfo(p1Point, cornerPoint, p2Point);
		}
		// ��map��key��ŵ�һ��ת�۵�, value��ŵڶ���ת�۵�,
		// map��size()˵���ж����ֿ������ķ�ʽ
		Map<Point, Point> turns = getLinkPoints(p1Point, p2Point,
			GameConf.PIECE_WIDTH, GameConf.PIECE_WIDTH);
		if (turns.size() != 0)
		{
			return getShortcut(p1Point, p2Point, turns,
				getDistance(p1Point, p2Point));
		}
		return null;
	}

	/**
	 * ��ȡ����ת�۵�����
	 * 
	 * @param point1
	 * @param point2
	 * @return Map�����ÿ��key-value�Դ���һ�����ӷ�ʽ��
	 *   ����key��value�ֱ�����1������2�����ӵ�
	 */
	private Map<Point, Point> getLinkPoints(Point point1, Point point2,
		int pieceWidth, int pieceHeight)
	{
		Map<Point, Point> result = new HashMap<Point, Point>();
		// ��ȡ��point1Ϊ���ĵ�����, ����, ���µ�ͨ��
		List<Point> p1UpChanel = getUpChanel(point1, point2.y, pieceHeight);
		List<Point> p1RightChanel = getRightChanel(point1, point2.x, pieceWidth);
		List<Point> p1DownChanel = getDownChanel(point1, point2.y, pieceHeight);
		// ��ȡ��point2Ϊ���ĵ�����, ����, ���ϵ�ͨ��
		List<Point> p2DownChanel = getDownChanel(point2, point1.y, pieceHeight);
		List<Point> p2LeftChanel = getLeftChanel(point2, point1.x, pieceWidth);
		List<Point> p2UpChanel = getUpChanel(point2, point1.y, pieceHeight);
		// ��ȡBoard�����߶�
		int heightMax = (this.config.getYSize() + 1) * pieceHeight
			+ this.config.getBeginImageY();
		// ��ȡBoard�������
		int widthMax = (this.config.getXSize() + 1) * pieceWidth
			+ this.config.getBeginImageX();
		// ��ȷ��������Ĺ�ϵ
		// point2��point1�����Ͻǻ������½�
		if (isLeftUp(point1, point2) || isLeftDown(point1, point2))
		{
			// ������λ, ���ñ�����
			return getLinkPoints(point2, point1, pieceWidth, pieceHeight);
		}
		// p1��p2λ��ͬһ�в���ֱ������
		if (point1.y == point2.y)
		{
			// ��ͬһ��
			// ���ϱ���
			// ��p1�����ĵ����ϱ�����ȡ�㼯��
			p1UpChanel = getUpChanel(point1, 0, pieceHeight);
			// ��p2�����ĵ����ϱ�����ȡ�㼯��
			p2UpChanel = getUpChanel(point2, 0, pieceHeight);
			Map<Point, Point> upLinkPoints = getXLinkPoints(p1UpChanel,
				p2UpChanel, pieceHeight);
			// ���±���, ������Board(�з���ĵط�)�ı߿�
			// ��p1���ĵ����±�����ȡ�㼯��
			p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
			// ��p2���ĵ����±�����ȡ�㼯��
			p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);
			Map<Point, Point> downLinkPoints = getXLinkPoints(p1DownChanel,
				p2DownChanel, pieceHeight);
			result.putAll(upLinkPoints);
			result.putAll(downLinkPoints);
		}
		// p1��p2λ��ͬһ�в���ֱ������
		if (point1.x == point2.x)
		{
			// ��ͬһ��
			// �������
			// ��p1�����ĵ����������ȡ�㼯��
			List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
			// ��p2�����ĵ����������ȡ�㼯��
			p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);
			Map<Point, Point> leftLinkPoints = getYLinkPoints(p1LeftChanel,
				p2LeftChanel, pieceWidth);
			// ���ұ���, ���ó���Board�ı߿��з���ĵط���
			// ��p1�����ĵ����ұ�����ȡ�㼯��
			p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
			// ��p2�����ĵ����ұ�����ȡ�㼯��
			List<Point> p2RightChanel = getRightChanel(point2, widthMax,
				pieceWidth);
			Map<Point, Point> rightLinkPoints = getYLinkPoints(p1RightChanel,
				p2RightChanel, pieceWidth);
			result.putAll(leftLinkPoints);
			result.putAll(rightLinkPoints);
		}
		// point2λ��point1�����Ͻ�
		if (isRightUp(point1, point2))
		{		
			// ��ȡpoint1���ϱ���, point2���±���ʱ����������ӵĵ�
			Map<Point, Point> upDownLinkPoints = getXLinkPoints(p1UpChanel,
				p2DownChanel, pieceWidth);
			// ��ȡpoint1���ұ���, point2�������ʱ����������ӵĵ�
			Map<Point, Point> rightLeftLinkPoints = getYLinkPoints(
				p1RightChanel, p2LeftChanel, pieceHeight);
			// ��ȡ��p1Ϊ���ĵ�����ͨ��
			p1UpChanel = getUpChanel(point1, 0, pieceHeight);
			// ��ȡ��p2Ϊ���ĵ�����ͨ��
			p2UpChanel = getUpChanel(point2, 0, pieceHeight);
			// ��ȡpoint1���ϱ���, point2���ϱ���ʱ����������ӵĵ�
			Map<Point, Point> upUpLinkPoints = getXLinkPoints(p1UpChanel,
				p2UpChanel, pieceWidth);
			// ��ȡ��p1Ϊ���ĵ�����ͨ��
			p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
			// ��ȡ��p2Ϊ���ĵ�����ͨ��
			p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);
			// ��ȡpoint1���±���, point2���±���ʱ����������ӵĵ�
			Map<Point, Point> downDownLinkPoints = getXLinkPoints(p1DownChanel,
				p2DownChanel, pieceWidth);
			// ��ȡ��p1Ϊ���ĵ�����ͨ��
			p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
			// ��ȡ��p2Ϊ���ĵ�����ͨ��
			List<Point> p2RightChanel = getRightChanel(point2, widthMax,
				pieceWidth);
			// ��ȡpoint1���ұ���, point2���ұ���ʱ����������ӵĵ�
			Map<Point, Point> rightRightLinkPoints = getYLinkPoints(
				p1RightChanel, p2RightChanel, pieceHeight);
			// ��ȡ��p1Ϊ���ĵ�����ͨ��
			List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
			// ��ȡ��p2Ϊ���ĵ�����ͨ��
			p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);
			// ��ȡpoint1�������, point2���ұ���ʱ����������ӵĵ�
			Map<Point, Point> leftLeftLinkPoints = getYLinkPoints(p1LeftChanel,
				p2LeftChanel, pieceHeight);
			result.putAll(upDownLinkPoints);
			result.putAll(rightLeftLinkPoints);
			result.putAll(upUpLinkPoints);
			result.putAll(downDownLinkPoints);
			result.putAll(rightRightLinkPoints);
			result.putAll(leftLeftLinkPoints);
		}
		// point2λ��point1�����½�
		if (isRightDown(point1, point2))
		{
			// ��ȡpoint1���±���, point2���ϱ���ʱ��������ӵĵ�
			Map<Point, Point> downUpLinkPoints = getXLinkPoints(p1DownChanel,
				p2UpChanel, pieceWidth);
			// ��ȡpoint1���ұ���, point2�������ʱ��������ӵĵ�
			Map<Point, Point> rightLeftLinkPoints = getYLinkPoints(
				p1RightChanel, p2LeftChanel, pieceHeight);
			// ��ȡ��p1Ϊ���ĵ�����ͨ��
			p1UpChanel = getUpChanel(point1, 0, pieceHeight);
			// ��ȡ��p2Ϊ���ĵ�����ͨ��
			p2UpChanel = getUpChanel(point2, 0, pieceHeight);
			// ��ȡpoint1���ϱ���, point2���ϱ���ʱ��������ӵĵ�
			Map<Point, Point> upUpLinkPoints = getXLinkPoints(p1UpChanel,
				p2UpChanel, pieceWidth);
			// ��ȡ��p1Ϊ���ĵ�����ͨ��
			p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
			// ��ȡ��p2Ϊ���ĵ�����ͨ��
			p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);
			// ��ȡpoint1���±���, point2���±���ʱ��������ӵĵ�
			Map<Point, Point> downDownLinkPoints = getXLinkPoints(p1DownChanel,
				p2DownChanel, pieceWidth);
			// ��ȡ��p1Ϊ���ĵ�����ͨ��
			List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
			// ��ȡ��p2Ϊ���ĵ�����ͨ��
			p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);
			// ��ȡpoint1�������, point2�������ʱ��������ӵĵ�
			Map<Point, Point> leftLeftLinkPoints = getYLinkPoints(p1LeftChanel,
				p2LeftChanel, pieceHeight);
			// ��ȡ��p1Ϊ���ĵ�����ͨ��
			p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
			// ��ȡ��p2Ϊ���ĵ�����ͨ��
			List<Point> p2RightChanel = getRightChanel(point2, widthMax,
				pieceWidth);
			// ��ȡpoint1���ұ���, point2���ұ���ʱ����������ӵĵ�
			Map<Point, Point> rightRightLinkPoints = getYLinkPoints(
				p1RightChanel, p2RightChanel, pieceHeight);
			result.putAll(downUpLinkPoints);
			result.putAll(rightLeftLinkPoints);
			result.putAll(upUpLinkPoints);
			result.putAll(downDownLinkPoints);
			result.putAll(leftLeftLinkPoints);
			result.putAll(rightRightLinkPoints);
		}
		return result;
	}
	
	/**
	 * ��ȡp1��p2֮����̵�������Ϣ
	 * 
	 * @param p1
	 * @param p2
	 * @param turns ��ת�۵��map
	 * @param shortDistance ����֮�����̾���
	 * @return p1��p2֮����̵�������Ϣ
	 */
	private LinkInfo getShortcut(Point p1, Point p2, Map<Point, Point> turns,
		int shortDistance)
	{
		List<LinkInfo> infos = new ArrayList<LinkInfo>();
		// �������Map,
		for (Point point1 : turns.keySet())
		{
			Point point2 = turns.get(point1);
			// ��ת�۵���ѡ����װ��LinkInfo����, �ŵ�List������
			infos.add(new LinkInfo(p1, point1, point2, p2));
		}
		return getShortcut(infos, shortDistance);
	}
	
	/**
	 * ��infos�л�ȡ��������̵��Ǹ�LinkInfo����
	 * 
	 * @param infos
	 * @return ��������̵��Ǹ�LinkInfo����
	 */
	private LinkInfo getShortcut(List<LinkInfo> infos, int shortDistance)
	{
		int temp1 = 0;
		LinkInfo result = null;
		for (int i = 0; i < infos.size(); i++)
		{
			LinkInfo info = infos.get(i);
			// �������������ܾ���
			int distance = countAll(info.getLinkPoints());
			// ��ѭ����һ���Ĳ����temp1����
			if (i == 0)
			{
				temp1 = distance - shortDistance;
				result = info;
			}
			// �����һ��ѭ����ֵ��temp1�Ļ�С, ���õ�ǰ��ֵ��Ϊtemp1
			if (distance - shortDistance < temp1)
			{
				temp1 = distance - shortDistance;
				result = info;
			}
		}
		return result;
	}
	
	/**
	 * ����List<Point>�����е�ľ����ܺ�
	 * 
	 * @param points ��Ҫ��������ӵ�
	 * @return ���е�ľ�����ܺ�
	 */
	private int countAll(List<Point> points)
	{
		int result = 0;
		for (int i = 0; i < points.size() - 1; i++)
		{
			// ��ȡ��i����
			Point point1 = points.get(i);
			// ��ȡ��i + 1����
			Point point2 = points.get(i + 1);
			// �����i�������i + 1����ľ��룬����ӵ��ܾ�����
			result += getDistance(point1, point2);
		}
		return result;
	}
	
	/**
	 * ��ȡ����LinkPoint֮�����̾���
	 * 
	 * @param p1 ��һ����
	 * @param p2 �ڶ�����
	 * @return ������ľ�������ܺ�
	 */
	private int getDistance(Point p1, Point p2)
	{
		int xDistance = Math.abs(p1.x - p2.x);
		int yDistance = Math.abs(p1.y - p2.y);
		return xDistance + yDistance;
	}
	
	/**
	 * ������������, ���жϵ�һ�����ϵ�Ԫ�ص�x��������һ�������е�Ԫ��x������ͬ(����), 
	 * �����ͬ, ����ͬһ��, ���ж��Ƿ����ϰ�, û����ӵ������Map��ȥ
	 * 
	 * @param p1Chanel
	 * @param p2Chanel
	 * @param pieceHeight
	 * @return
	 */
	private Map<Point, Point> getYLinkPoints(List<Point> p1Chanel,
		List<Point> p2Chanel, int pieceHeight)
	{
		Map<Point, Point> result = new HashMap<Point, Point>();
		for (int i = 0; i < p1Chanel.size(); i++)
		{
			Point temp1 = p1Chanel.get(i);
			for (int j = 0; j < p2Chanel.size(); j++)
			{
				Point temp2 = p2Chanel.get(j);
				// ���x������ͬ(��ͬһ��)
				if (temp1.x == temp2.x)
				{
					// û���ϰ�, �ŵ�map��ȥ
					if (!isYBlock(temp1, temp2, pieceHeight))
					{
						result.put(temp1, temp2);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * ������������, ���жϵ�һ�����ϵ�Ԫ�ص�y��������һ�������е�Ԫ��y������ͬ(����),
	 * �����ͬ, ����ͬһ��, ���ж��Ƿ����ϰ�, û�� ��ӵ������map��ȥ
	 * 
	 * @param p1Chanel
	 * @param p2Chanel
	 * @param pieceWidth
	 * @return ��ſ��Ժ���ֱ�����ӵ����ӵ�ļ�ֵ��
	 */
	private Map<Point, Point> getXLinkPoints(List<Point> p1Chanel,
		List<Point> p2Chanel, int pieceWidth)
	{
		Map<Point, Point> result = new HashMap<Point, Point>();
		for (int i = 0; i < p1Chanel.size(); i++)
		{
			// �ӵ�һͨ����ȡһ����
			Point temp1 = p1Chanel.get(i);
			// �ٱ����ڶ���ͨ��, ���µڶ�ͨ�����Ƿ��е������temp1��������
			for (int j = 0; j < p2Chanel.size(); j++)
			{
				Point temp2 = p2Chanel.get(j);
				// ���y������ͬ(��ͬһ��), ���ж�����֮���Ƿ���ֱ���ϰ�
				if (temp1.y == temp2.y)
				{
					if (!isXBlock(temp1, temp2, pieceWidth))
					{
						// û���ϰ���ֱ�Ӽӵ������map��
						result.put(temp1, temp2);
					}
				}
			}
		}
		return result;
	}

	/**
	 * �ж�point2�Ƿ���point1�����Ͻ�
	 * 
	 * @param point1
	 * @param point2
	 * @return p2λ��p1�����Ͻ�ʱ����true�����򷵻�false
	 */
	private boolean isLeftUp(Point point1, Point point2)
	{
		return (point2.x < point1.x && point2.y < point1.y);
	}
	
	/**
	 * �ж�point2�Ƿ���point1�����½�
	 * 
	 * @param point1
	 * @param point2
	 * @return p2λ��p1�����½�ʱ����true�����򷵻�false
	 */
	private boolean isLeftDown(Point point1, Point point2)
	{
		return (point2.x < point1.x && point2.y > point1.y);
	}
	
	/**
	 * �ж�point2�Ƿ���point1�����Ͻ�
	 * 
	 * @param point1
	 * @param point2
	 * @return p2λ��p1�����Ͻ�ʱ����true�����򷵻�false
	 */
	private boolean isRightUp(Point point1, Point point2)
	{
		return (point2.x > point1.x && point2.y < point1.y);
	}
	
	/**
	 * �ж�point2�Ƿ���point1�����½�
	 * 
	 * @param point1
	 * @param point2
	 * @return p2λ��p1�����½�ʱ����true�����򷵻�false
	 */
	private boolean isRightDown(Point point1, Point point2)
	{
		return (point2.x > point1.x && point2.y > point1.y);
	}

	/**
	 * ��ȡ��������ͬһ�л���ͬһ�е�������ֱ�����ӵ�, ��ֻ��һ��ת�۵�
	 * 
	 * @param point1 ��һ����
	 * @param point2 �ڶ�����
	 * @return ��������ͬһ�л���ͬһ�е�������ֱ�����ӵ�
	 */
	private Point getCornerPoint(Point point1, Point point2, int pieceWidth,
		int pieceHeight)
	{
		// ���ж����������λ�ù�ϵ
		// point2��point1�����Ͻ�, point2��point1�����½�
		if (isLeftUp(point1, point2) || isLeftDown(point1, point2))
		{
			// ������λ, ���µ��ñ�����
			return getCornerPoint(point2, point1, pieceWidth, pieceHeight);
		}
		// ��ȡp1����, ����, ���µ�����ͨ��
		List<Point> point1RightChanel = getRightChanel(point1, point2.x,
			pieceWidth);
		List<Point> point1UpChanel = getUpChanel(point1, point2.y, pieceHeight);
		List<Point> point1DownChanel = getDownChanel(point1, point2.y,
			pieceHeight);
		// ��ȡp2����, ����, ���µ�����ͨ��
		List<Point> point2DownChanel = getDownChanel(point2, point1.y,
			pieceHeight);
		List<Point> point2LeftChanel = getLeftChanel(point2, point1.x,
			pieceWidth);
		List<Point> point2UpChanel = getUpChanel(point2, point1.y, pieceHeight);
		if (isRightUp(point1, point2))
		{
			// point2��point1�����Ͻ�
			// ��ȡp1���Һ�p2���µĽ���
			Point linkPoint1 = getWrapPoint(point1RightChanel, point2DownChanel);
			// ��ȡp1���Ϻ�p2����Ľ���
			Point linkPoint2 = getWrapPoint(point1UpChanel, point2LeftChanel);
			// ��������һ������, ���û�н���, �򷵻�null
			return (linkPoint1 == null) ? linkPoint2 : linkPoint1;
		}
		if (isRightDown(point1, point2))
		{
			// point2��point1�����½�
			// ��ȡp1���º�p2����Ľ���
			Point linkPoint1 = getWrapPoint(point1DownChanel, point2LeftChanel);
			// ��ȡp1���Һ�p2���µĽ���
			Point linkPoint2 = getWrapPoint(point1RightChanel, point2UpChanel);
			return (linkPoint1 == null) ? linkPoint2 : linkPoint1;
		}
		return null;
	}

	/**
	 * ��������ͨ��, ��ȡ���ǵĽ���
	 * 
	 * @param p1Chanel ��һ�����ͨ��
	 * @param p2Chanel �ڶ������ͨ��
	 * @return ����ͨ���н��㣬���ؽ��㣬���򷵻�null
	 */
	private Point getWrapPoint(List<Point> p1Chanel, List<Point> p2Chanel)
	{
		for (int i = 0; i < p1Chanel.size(); i++)
		{
			Point temp1 = p1Chanel.get(i);
			for (int j = 0; j < p2Chanel.size(); j++)
			{
				Point temp2 = p2Chanel.get(j);
				if (temp1.equals(temp2))
				{
					// �������List����Ԫ����ͬһ��, ����������ͨ���н���
					return temp1;
				}
			}
		}
		return null;
	}

	/**
	 * �ж�����y������ͬ�ĵ����֮���Ƿ����ϰ�, ��p1Ϊ�������ұ���
	 * 
	 * @param p1
	 * @param p2
	 * @param pieceWidth
	 * @return ����Piece֮�����ϰ�����true�����򷵻�false
	 */
	private boolean isXBlock(Point p1, Point p2, int pieceWidth)
	{
		if (p2.x < p1.x)
		{
			// ���p2��p1���, ��������λ�õ��ñ�����
			return isXBlock(p2, p1, pieceWidth);
		}
		for (int i = p1.x + pieceWidth; i < p2.x; i = i + pieceWidth)
		{
			if (hasPiece(i, p1.y))
			{// ���ϰ�
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж�����x������ͬ�ĵ����֮���Ƿ����ϰ�, ��p1Ϊ�������±���
	 * 
	 * @param p1
	 * @param p2
	 * @param pieceHeight
	 * @return ����Piece֮�����ϰ�����true�����򷵻�false
	 */
	private boolean isYBlock(Point p1, Point p2, int pieceHeight)
	{
		if (p2.y < p1.y)
		{
			// ���p2��p1������, ��������λ�����µ��ñ�����
			return isYBlock(p2, p1, pieceHeight);
		}
		for (int i = p1.y + pieceHeight; i < p2.y; i = i + pieceHeight)
		{
			if (hasPiece(p1.x, i))
			{
				// ���ϰ�
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж�GamePanel�е�x, y�������Ƿ���Piece����
	 * 
	 * @param x
	 * @param y
	 * @return true ��ʾ�и�������piece���� false ��ʾû��
	 */
	private boolean hasPiece(int x, int y)
	{
		if (findPiece(x, y) == null)
			return false;
		return true;
	}

	/**
	 * ��һ��Point����,�����������ͨ��
	 * 
	 * @param p
	 * @param pieceWidth pieceͼƬ�Ŀ�
	 * @param min �������ʱ��С�Ľ���
	 * @return ����Point��ߵ�ͨ��
	 */
	private List<Point> getLeftChanel(Point p, int min, int pieceWidth)
	{
		List<Point> result = new ArrayList<Point>();
		// ��ȡ����ͨ��, ��һ�����������, ����ΪPieceͼƬ�Ŀ�
		for (int i = p.x - pieceWidth; i >= min
			; i = i - pieceWidth)
		{
			// �����ϰ�, ��ʾͨ���Ѿ�����ͷ, ֱ�ӷ���
			if (hasPiece(i, p.y))
			{
				return result;
			}
			result.add(new Point(i, p.y));
		}
		return result;
	}
	
	/**
	 * ��һ��Point����, ���������ұ�ͨ��
	 * 
	 * @param p
	 * @param pieceWidth
	 * @param max ����ʱ�����ҽ���
	 * @return ����Point�ұߵ�ͨ��
	 */
	private List<Point> getRightChanel(Point p, int max, int pieceWidth)
	{
		List<Point> result = new ArrayList<Point>();
		// ��ȡ����ͨ��, ��һ�������ұ���, ����ΪPieceͼƬ�Ŀ�
		for (int i = p.x + pieceWidth; i <= max
			; i = i + pieceWidth)
		{
			// �����ϰ�, ��ʾͨ���Ѿ�����ͷ, ֱ�ӷ���
			if (hasPiece(i, p.y))
			{
				return result;
			}
			result.add(new Point(i, p.y));
		}
		return result;
	}
	
	/**
	 * ��һ��Point����, ������������ͨ��
	 * 
	 * @param p
	 * @param min ���ϱ���ʱ��С�Ľ���
	 * @param pieceHeight
	 * @return ����Point�����ͨ��
	 */
	private List<Point> getUpChanel(Point p, int min, int pieceHeight)
	{
		List<Point> result = new ArrayList<Point>();
		// ��ȡ����ͨ��, ��һ�������ұ���, ����ΪPieceͼƬ�ĸ�
		for (int i = p.y - pieceHeight; i >= min
			; i = i - pieceHeight)
		{
			// �����ϰ�, ��ʾͨ���Ѿ�����ͷ, ֱ�ӷ���
			if (hasPiece(p.x, i))
			{
				// ��������ϰ�, ֱ�ӷ���
				return result;
			}
			result.add(new Point(p.x, i));
		}
		return result;
	}
	
	/**
	 * ��һ��Point����, ������������ͨ��
	 * 
	 * @param p
	 * @param max ���ϱ���ʱ��������
	 * @return ����Point�����ͨ��
	 */
	private List<Point> getDownChanel(Point p, int max, int pieceHeight)
	{
		List<Point> result = new ArrayList<Point>();
		// ��ȡ����ͨ��, ��һ�������ұ���, ����ΪPieceͼƬ�ĸ�
		for (int i = p.y + pieceHeight; i <= max
			; i = i + pieceHeight)
		{
			// �����ϰ�, ��ʾͨ���Ѿ�����ͷ, ֱ�ӷ���
			if (hasPiece(p.x, i))
			{
				// ��������ϰ�, ֱ�ӷ���
				return result;
			}
			result.add(new Point(p.x, i));
		}
		return result;
	}
}
