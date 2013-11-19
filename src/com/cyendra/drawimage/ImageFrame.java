package com.cyendra.drawimage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.cyendra.drawimage.action.ImageAction;
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
	JPanel colorPanel = createColorPanel();
	
	/**
	 * Ĭ�Ϲ�����
	 */
	public ImageFrame() {
		super();
		initialize();
	}

	/**
	 * ��ʼ�� ImageFrame
	 */
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
		this.pack();
	}

	
	/**
	 * ��ȡ��ɫѡ����
	 * @return JColorChooser
	 */
	public JColorChooser getColorChooser() {
		if (colorChooser == null) {
			colorChooser = new JColorChooser();
		}
		return colorChooser;
	}
	
	/**
	 * �����˵���
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		String[] menuArr = { "�ļ�(F)", "�鿴(V)", "��ɫ(C)", "����(H)" };
		String[][] menuItemArr = { { "�½�(N)", "��(O)", "����(S)", "-", "�˳�(X)" },
								   { "������(T)", "���Ϻ�(C)" }, 
								   { "�༭��ɫ" }, { "��������", "����" } };
		for (int i = 0; i < menuArr.length; i++) {
			JMenu menu = new JMenu(menuArr[i]);
			for (int j = 0; j < menuItemArr[i].length; j++) {
				if (menuItemArr[i][j].equals("-")) {
					menu.addSeparator();
				}
				else {
					JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
					menuItem.addActionListener(menuListener);
					menu.add(menuItem);
				}
			}
			menuBar.add(menu);
		}
		this.setJMenuBar(menuBar);
	}
	

	/**
	 * ��������ɫѡ���
	 * @return JPanel
	 */
	public JPanel createColorPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JToolBar toolBar = new JToolBar("��ɫ");
		toolBar.setFloatable(false);
		toolBar.setMargin(new Insets(2, 2, 2, 2));
		toolBar.setLayout(new GridLayout(2, 6, 2, 2));
		Color[] colorArr = { Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY,
							 Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW };
		JButton[] panelArr = new JButton[colorArr.length];
		currentColorPanel = new JPanel();
		currentColorPanel.setBackground(Color.BLACK);
		currentColorPanel.setPreferredSize(new Dimension(20, 20));
		for (int i = 0; i < panelArr.length; i++) {
			panelArr[i] = new JButton(new ImageAction(colorArr[i],currentColorPanel));
			panelArr[i].setBackground(colorArr[i]);
			toolBar.add(panelArr[i]);
		}
		panel.add(currentColorPanel);
		panel.add(toolBar);
		return panel;
	}

	/**
	 * ���������� 
	 * @return JPanel
	 */
	private JPanel createToolPanel() {
		JPanel panel = new JPanel();
		JToolBar toolBar = new JToolBar("����");
		toolBar.setOrientation(toolBar.VERTICAL);
		toolBar.setFloatable(true);
		toolBar.setMargin(new Insets(2, 2, 2, 2));
		toolBar.setLayout(new GridLayout(5, 2, 2, 2));
		String[] toolarr = { Tool.PENCIL_TOOL, Tool.BRUSH_TOOL, Tool.COLORPICKED_TOOL,
							 Tool.ATOMIZER_TOOL, Tool.ERASER_TOOL, Tool.LINE_TOOL, Tool.POLYGON_TOOL, 
							 Tool.RECT_TOOL,Tool.ROUND_TOOL, Tool.ROUNDRECT_TOOL };
		for (int i = 0; i < toolarr.length; i++) {
			ImageAction action = new ImageAction(new ImageIcon("img/" + toolarr[i] + ".jpg"), toolarr[i], this);
			JButton button = new JButton(action);
			toolBar.add(button);
		}
		panel.add(toolBar);
		return panel;
	}
	
	/**
	 * ��������
	 * @return JPanel
	 */
	private JPanel createDrawSpace() {
		JPanel drawSpace = new DrawSpace();
		// ����drawSpace�Ĵ�С
		drawSpace.setPreferredSize(new Dimension((int) screenSize.getWidth(),
				(int) screenSize.getHeight() - 150));
		return drawSpace;
	}
	
	/**
	 * ��ȡ����
	 * @return JPanel
	 */
	public JPanel getDrawSpace() {
		return this.drawSpace;
	}

	/**
	 * ����ͼƬ
	 * @param bufferedImage MyImage
	 */
	public void setBufferedImage(ExImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	/**
	 * ��ȡͼƬ 
	 * @return ExImage
	 */
	public ExImage getBufferedImage() {
		return this.bufferedImage;
	}
	
	/**
	 * ��ȡJScroolPane
	 * @return JScrollPane
	 */
	public JScrollPane getScroll() {
		return this.scroll;
	}

	/**
	 * ��ȡ��ɫ��ʾ���
	 * @return JPanel
	 */
	public JPanel getCurrentColorPanel() {
		return this.currentColorPanel;
	}
	
	/**
	 * ��ȡ������
	 * @return JPanel
	 */
	public JPanel getToolPanel() {
		return this.toolPanel;
	}
	
	/**
	 * ���ù���
	 * @param tool Tool
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	/**
	 * ��ȡ����
	 * @return Tool
	 */
	public Tool getTool() {
		return this.tool;
	}
	
	/**
	 * ��ȡ��ɫ���
	 * @return JPanel
	 */
	public JPanel getColorPanel() {
		return this.colorPanel;
	}
	

	/**
	 * ��ȡscreenSize
	 * @return Dimension
	 */
	public Dimension getScreenSize() {
		return this.screenSize;
	}
	
	// ��ͼ����
	public class DrawSpace extends JPanel {
		private static final long serialVersionUID = 1L;
		/**
		 * ��дvoid paint( Graphics g )����
		 * @param g Graphics
		 */
		public void paint(Graphics g) {
			service.repaint(g, bufferedImage);// draw
		}
	}
	
}
