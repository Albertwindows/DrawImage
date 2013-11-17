package com.cyendra.drawimage.action;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.cyendra.drawimage.ImageFrame;
import com.cyendra.drawimage.tool.AbstractTool;
import com.cyendra.drawimage.tool.Tool;
import com.cyendra.drawimage.tool.ToolFactory;

/**
 * ����������
 * @author cyendra
 * @version 1.0
 */
public class ImageAction extends AbstractAction {
	
	private String name = "";
	private ImageFrame frame = null;
	private Color color = null;
	private Tool tool = null;
	private JPanel colorPanel = null;

	/**
	 * ������
	 * @param icon ImageIcon ͼ��
	 * @param name ����
	 * @param frame ImageFrame
	 */
	public ImageAction(Color color, JPanel colorPanel) {
		// ���ø�������
		super();
		this.color = color;
		this.colorPanel = colorPanel;
	}

	/**
	 * ������
	 * @param icon ImageIcon ͼ��
	 * @param name ����
	 * @param frame ImageFrame
	 */
	public ImageAction(ImageIcon icon, String name, ImageFrame frame) {
		// ���ø�������
		super("", icon);
		this.name = name;
		this.frame = frame;
	}
	
	/**
	 * ��дvoid actionPerformed( ActionEvent e )����
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		tool = name != "" ? ToolFactory.getToolInstance(frame, name) : tool;// ����tool
		if (tool != null) {
			frame.setTool(tool);// ��������ʹ�õ�tool
		}
		if (color != null) {
			AbstractTool.color = color;// ��������ʹ�õ���ɫ
			colorPanel.setBackground(color);
		}
	}

}
