package com.zeshan.syncnotes;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTextPane;

public class NoteView extends JFrame {

	private static final long serialVersionUID = 1L;


	public NoteView frame;
	
	private JPanel pane;
	private Point mouseDownCompCoords;
	private JTextField textField;
	private JTextPane textPane;
	private ComponentResizer cr = new ComponentResizer();
	
	private String ID;
	private int x;
	private int y;
	

	/**
	 * Create the frame.
	 */
	
	public NoteView createFrame(String ID, String title, String body) {
		this.ID = ID;
		
		setBounds(100, 100, 450, 300);
		
		pane = (JPanel) getContentPane();
		
		pane.setLayout(null);
		pane.setBackground(hexToColor("#FFE981"));
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Arial Black", Font.BOLD, 15));
		textField.setBounds(10, 11, 414, 20);
		pane.add(textField);
		textField.setColumns(10);
		textField.setText(title);
		textField.setOpaque(false);
		textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 414, 209);
		scrollPane.setBackground(hexToColor("#FFE981"));
		scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		pane.add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setText(body);
		textPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textPane.setBackground(hexToColor("#FFE981"));
		
		return this;
	}
	
	public void showFrame() {
		dragListener();
		cr.registerComponent(frame);
				
        frame.setUndecorated(true);
		frame.setVisible(true);
		
		// Hide from tool bar.
		//frame.setType(javax.swing.JFrame.Type.UTILITY);
	}
	
	private Color hexToColor(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	private void dragListener() {
		mouseDownCompCoords = null;
		
		textField.addMouseListener(new MouseListener(){
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
            }
        });

		textField.addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
                
                x = currCoords.x - mouseDownCompCoords.x;
                y = currCoords.y - mouseDownCompCoords.y;
            }
        });
	}
}
