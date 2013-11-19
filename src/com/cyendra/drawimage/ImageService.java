package com.cyendra.drawimage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cyendra.drawimage.file.ImageFileChooser;
import com.cyendra.drawimage.image.ExImage;

/**
 * ��ͼ���ߴ����߼���(�ǹ���)
 * @author cyendra
 * @version 1.0
 */
public class ImageService {
	
	private ImageFileChooser fileChooser = new ImageFileChooser();

	/**
	 * ��ȡ��Ļ�ķֱ��� 
	 * @return Dimension
	 */
	public Dimension getScreenSize() {
		Toolkit dt = Toolkit.getDefaultToolkit();
		return dt.getScreenSize();
	}
	
	/**
	 * ��ʼ���� drawSpace
	 * @param frame ImageFrame
	 * @return void
	 */
	public void initDrawSpace(ImageFrame frame) {
		Graphics g = frame.getBufferedImage().getGraphics();// ��ȡ��ͼ����
		Dimension d = frame.getDrawSpace().getPreferredSize();// ��ȡ�����Ĵ�С
		int drawWidth = (int) d.getWidth();// ��ȡ��
		int drawHeight = (int) d.getHeight();// ��ȡ��
		g.setColor(Color.WHITE);// �滭��
		g.fillRect(0, 0, drawWidth, drawHeight);
	}
	
	/**
	 * �������ͼ��
	 * @param path String ͼ��·��
	 * @return Cursor
	 */
	public static Cursor createCursor(String path) {
		Image cursorImage = Toolkit.getDefaultToolkit().createImage(path);
		return Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,
				new Point(10, 10), "mycursor");
	}

	public static void setViewport(Object scroll, JPanel drawSpace, int x, int y) {
		// TODO Auto-generated method stub
		
	}


	public void menuDo(ImageFrame imageFrame, String actionCommand) {
		// TODO Auto-generated method stub
		
	}

	public static void setViewport(Object scroll, JFrame drawSpace, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * repaint
	 * 
	 */
	public void repaint(Graphics g, ExImage bufferedImage) {
		int drawWidth = bufferedImage.getWidth();
		int drawHeight = bufferedImage.getHeight();
		Dimension screenSize = getScreenSize();
		// ���÷ǻ滭������ɫ
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, (int) screenSize.getWidth() * 10, (int) screenSize.getHeight() * 10);
		// �����϶������ɫ
		g.setColor(Color.BLUE);
		g.fillRect(drawWidth, drawHeight, 4, 4);
		g.fillRect(drawWidth, (int) drawHeight / 2 - 2, 4, 4);
		g.fillRect((int) drawWidth / 2 - 2, drawHeight, 4, 4);
		// �ѻ����ͼƬ�滭����
		g.drawImage(bufferedImage, 0, 0, drawWidth, drawHeight, null);
	}

}
