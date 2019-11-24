package ua.nure.cs.shatalov.usermanagement.domain.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.omg.CORBA.INITIALIZE;

public class MainFrame extends JFrame {

	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private JPanel contentPanel;
	private JPanel browsePanel;
	private AddPanel addPanel;

	public MainFrame() {
		super();
		initialize();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("User management");
		this.setContentPane(getJContentPanel());
	}

	private JPanel getJContentPanel() {
		// TODO Auto-generated method stub
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private JPanel getBrowsePanel() {
		// TODO Auto-generated method stub
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);
		}
		return browsePanel;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame frame = new MainFrame();
		frame.setVisible(true);

	}

	public void showAddPanel() {
		// TODO Auto-generated method stub
		showPanel(getAddPanel());
	}
	
	public void showBrowsePanel() {
		// TODO Auto-generated method stub
		showPanel(getBrowsePanel());
	}

	private void showPanel(JPanel panel) {
		// TODO Auto-generated method stub
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private AddPanel getAddPanel() {
		// TODO Auto-generated method stub
		if (addPanel == null) {
			addPanel = new AddPanel(this);
		}
		return addPanel;
	}

}
