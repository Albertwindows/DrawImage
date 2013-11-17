package com.cyendra.drawimage.tool;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.cyendra.drawimage.ImageFrame;
import com.cyendra.drawimage.ImageService;
import com.cyendra.drawimage.image.ExImage;

/**
 * Tool ������
 * @version  1.0
 * @author cyendra
 */
public abstract class AbstractTool implements Tool {
	
	// ����ImageFrame
	private ImageFrame frame = null;
	
	// ���廭��Ŀ����
	public static int drawWidth = 0;
	public static int drawHeight = 0;
	
	// ����Ĭ�����ָ��
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	
	// ������������
	private int pressX = -1;
	private int pressY = -1;
	
	// ��ɫ
	public static Color color = Color.BLACK;
	
	/**
	 * ����һ�� Tool
	 * @param frame ImageFrame
	 */
	public AbstractTool(ImageFrame frame) {
		this.frame = frame;
		AbstractTool.drawWidth = frame.getBufferedImage().getWidth();
		AbstractTool.drawHeight = frame.getBufferedImage().getHeight();
	}
	
	/**
	 * ����һ�������ͼ�ε� Tool
	 * @param frame ImageFrame
	 * @param path String
	 */
	public AbstractTool(ImageFrame frame, String path) {
		this(frame);
		this.defaultCursor = ImageService.createCursor(path);
	}

	/**
	 * ��ȡ��ͼ����
	 * @return ImageFrame
	 */
	public ImageFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * ��ȡĬ�����ָ��
	 * @return Cursor Ĭ�����ָ��
	 */
	public Cursor getDefaultCursor() {
		return this.defaultCursor;
	}
	
	/**
	 * ����Ĭ�����ָ��
	 * @param cursor Cursor
	 */
	public void setDefaultCursor(Cursor cursor) {
		this.defaultCursor = cursor;
	}

	/**
	 * ���ð�������x����
	 * @param x int
	 */
	public void setPressX(int x) {
		this.pressX = x;
	}

	/**
	 * ���ð�������y����
	 * @param y int
	 */
	public void setPressY(int y) {
		this.pressY = y;
	}

	/**
	 * ��ȡ��������x����
	 * @return int pressX
	 */
	public int getPressX() {
		return this.pressX;
	}

	/**
	 * ��ȡ��������y����
	 * @return int pressY
	 */
	public int getPressY() {
		return this.pressY;
	}

	/**
	 * �϶����
	 * @param e MouseEvent
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		dragBorder(e);// �϶�ͼ�α߽�
		Graphics g = getFrame().getDrawSpace().getGraphics();
		createShape(e, g);// ��ͼ
	}
	
	//λ�ó���
	protected static final boolean MID = false;
	protected static final boolean LOW = true;
	//�ж�����Ƿ����϶�ԭ����
	protected boolean mouseOn(int x,int y,boolean locx,boolean locy) {
		if (locx==MID&&!(x>(int)AbstractTool.drawWidth/2-4&&x<(int)AbstractTool.drawWidth/2+4))
			return false;
		if (locx==LOW&&!(x>AbstractTool.drawWidth-4&&x<AbstractTool.drawWidth+4))
			return false;
		if (locy==MID&&!(y>(int)AbstractTool.drawHeight/2-4&&y<(int)AbstractTool.drawHeight/2+4))
			return false;
		if (locy==LOW&&!(y>AbstractTool.drawHeight-4&&y<AbstractTool.drawHeight+4))
			return false;
		return true;
	}
	//�ж�����Ƿ���ָ��������
	protected boolean mouseOn(int x,int y,int dx,int dy) {
		if (x > 0 && x < dx && y > 0 && y < dy) return true;
		return false;
	}
	
	/**
	 * �ƶ����
	 * @param e MouseEvent
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();// ��ȡ������ڵ�����
		int y = e.getY();
		Cursor cursor = getDefaultCursor();// ��ȡĬ�����ָ��
		if (mouseOn(x,y,LOW,LOW)) {// ������ָ��������
			cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);// �����ָ��ı�Ϊ�����϶���״
		}
		if (mouseOn(x,y,LOW,MID)) {// ������ָ��������
			cursor = new Cursor(Cursor.W_RESIZE_CURSOR);// �����ָ��ı�Ϊ���϶���״
		}
		if (mouseOn(x,y,MID,LOW)) {// ������ָ��������
			cursor = new Cursor(Cursor.S_RESIZE_CURSOR);// �����ָ��ı�Ϊ���϶���״
		}
		getFrame().getDrawSpace().setCursor(cursor);// �������ָ��
	}

	/**
	 * �ɿ����
	 * @param e MouseEvent
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Graphics g = getFrame().getBufferedImage().getGraphics();
		createShape(e, g);// ��ͼ
		setPressX(-1);
		setPressY(-1);
		getFrame().getDrawSpace().repaint();// �ػ�
	}
	
	/**
	 * �������
	 * @param e MouseEvent
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();// ���λ����ͼƬ��Χ�ڣ����ð��µ�����
		int y = e.getY();
		if (mouseOn(x,y,AbstractTool.drawWidth,AbstractTool.drawHeight)) {
			setPressX(x);
			setPressY(y);
		}
	}
	
	/**
	 * ������
	 * @param e MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Empty
	}
	
	/**
	 * �϶�ͼ�α߽�
	 * @param e MouseEvent
	 */
	private void dragBorder(MouseEvent e) {
		getFrame().getBufferedImage().setIsSaved(false);
		// ��ȡ������ڵ�x��y����
		int cursorType = getFrame().getDrawSpace().getCursor().getType();
		int x = cursorType == Cursor.S_RESIZE_CURSOR ? AbstractTool.drawWidth : e.getX();
		int y = cursorType == Cursor.W_RESIZE_CURSOR ? AbstractTool.drawHeight : e.getY();
		ExImage img = null;
		// ������ָ�����϶�״̬
		if ((cursorType == Cursor.NW_RESIZE_CURSOR
				|| cursorType == Cursor.W_RESIZE_CURSOR || cursorType == Cursor.S_RESIZE_CURSOR)
				&& (x > 0 && y > 0)) {
			// �ı�ͼ���С
			img = new ExImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			g.setColor(Color.WHITE);
			g.drawImage(getFrame().getBufferedImage(),0,0,AbstractTool.drawWidth,AbstractTool.drawHeight,null);
			getFrame().setBufferedImage(img);
			// ���û����Ĵ�С
			AbstractTool.drawWidth = x;
			AbstractTool.drawHeight = y;
			// ����viewport
			ImageService.setViewport(frame.getScroll(), frame.getDrawSpace(), x, y);
		}
	}
	
	/**
	 * ��ͼ��
	 * @param e MouseEvent
	 * @param g Graphics
	 */
	private void createShape(MouseEvent e, Graphics g) {
		// ���λ���ڻ�����
		if (getPressX() > 0 && getPressY() > 0 
				&& mouseOn(e.getX(),e.getY(),AbstractTool.drawWidth,AbstractTool.drawHeight)) {
			// ������ͼƬ�ػ�
			g.drawImage(getFrame().getBufferedImage(),0,0,AbstractTool.drawWidth,AbstractTool.drawHeight,null);
			// ������ɫ
			g.setColor(AbstractTool.color);
			getFrame().getBufferedImage().setIsSaved(false);
			// ��ͼ��
			draw(g, getPressX(), getPressY(), e.getX(), e.getY());
		}
	}
	
	/**
	 * ��ͼ��
	 * @param g Graphics
	 * @param x1 ���x����
	 * @param y1 ���y����
	 * @param x2 �յ�x����
	 * @param y2 �յ�y����
	 */
	private void draw(Graphics g, int x1, int y1, int x2, int y2) {
		// Empty
	}

}
