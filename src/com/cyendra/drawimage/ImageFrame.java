package com.cyendra.drawimage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.cyendra.drawimage.image.ExImage;
import com.cyendra.drawimage.tool.Tool;
import com.cyendra.drawimage.tool.ToolFactory;

/**
 * ��ͼ����
 * @author cyendra
 * @version 1.0
 */
public class ImageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//ҵ���߼�
	private ImageService service = new ImageService();
	
	//��ʼ����Ļ�ĳߴ�
	private Dimension screenSize = service.getScreenSize();
	
	//����Ĭ�ϻ���
	private JPanel drawSpace = createDrawSpace();
	
	//���û���ͼƬ
	private ExImage bufferedImage = new ExImage((int) screenSize.getWidth() / 2,
												(int) screenSize.getHeight() / 2,
												BufferedImage.TYPE_INT_RGB);
	
	//���õ�ǰʹ�õĹ���
	private Tool tool = null;
	
	//���û�ͼ����
	Graphics g = bufferedImage.getGraphics();
	
	//��ɫ��ʾ���
	private JPanel currentColorPanel = null;
	
	//��ɫѡ����
	private JColorChooser colorChooser = getColorChooser();
	
	//�˵����¼�������
	ActionListener menuListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			service.menuDo(ImageFrame.this, e.getActionCommand());
		}
	};
	
	//Ĭ��JScrollPane
	private JScrollPane scroll = null;
	
	// ������
	JPanel toolPanel = createToolPanel();
	
	// ��ɫ���
	JPanel colorPanel = createToolPanel();

	public ImageFrame() {
		super();
		initialize();
	}
	
	private JPanel createToolPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	private JColorChooser getColorChooser() {
		// TODO Auto-generated method stub
		return null;
	}

	private JPanel createDrawSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initialize() {
		this.setTitle("��ͼ");
		service.initDrawSpace(this);
		tool = ToolFactory.getToolInstance(this, Tool.PENCIL_TOOL);
		MouseMotionListener motionListener = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				tool.mouseDragged(e);
			}
			public void mouseMoved(MouseEvent e) {
				tool.mouseMoved(e);
			}
		};
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				tool.mouseReleased(e);
			}
			public void mousePressed(MouseEvent e) {
				tool.mousePressed(e);
			}
			public void mouseClicked(MouseEvent e) {
				tool.mouseClicked(e);
			}
		};
		drawSpace.addMouseMotionListener(motionListener);
		drawSpace.addMouseListener(mouseListener);
		createMenuBar();
		scroll = new JScrollPane(drawSpace);
		ImageService.setViewport(scroll,drawSpace,bufferedImage.getWidth(),bufferedImage.getHeight());
		this.add(scroll, BorderLayout.CENTER);
		this.add(toolPanel, BorderLayout.WEST);
		this.add(colorPanel, BorderLayout.SOUTH);
	}

	private void createMenuBar() {
		// TODO Auto-generated method stub
		
	}

	public ExImage getBufferedImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public JFrame getDrawSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBufferedImage(ExImage img) {
		// TODO Auto-generated method stub
		
	}

	public Object getScroll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Frame getCurrentColorPanel() {
		// TODO Auto-generated method stub
		return null;
	}
}
