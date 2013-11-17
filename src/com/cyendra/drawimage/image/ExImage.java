package com.cyendra.drawimage.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

/**
 * ͼƬ��
 * �̳��� BufferedImage
 * @author cyendra
 * @version 1.0
 */
public class ExImage extends BufferedImage {

	// �Ƿ��Ѿ�����
	private boolean isSaved = true;
	
	/**
	 * @param width int
	 * @param height int
	 * @param type int
	 */
	public ExImage(int width, int height, int type) {
		super(width, height, type);
		this.getGraphics().fillRect(0, 0, width, height);
	}
	
	/**
	 * �����Ƿ񱣴�
	 * @param b boolean
	 */
	public void setIsSaved(boolean b) {
		this.isSaved = b;
	}

	/**
	 * �Ƿ��Ѿ�����
	 * @return boolean
	 */
	public boolean isSaved() {
		return this.isSaved;
	}
}
