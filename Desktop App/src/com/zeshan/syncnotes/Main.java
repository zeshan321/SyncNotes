package com.zeshan.syncnotes;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import org.parse4j.ParseException;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField uuidField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Initialize parse
		new ParseSetup().initializeParse();
		
		// Change UI to match system
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    
		}
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel welcomeLabel = new JLabel("Welcome to SyncNotes!");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Arial Black", Font.BOLD, 27));
		welcomeLabel.setBounds(10, 11, 414, 78);
		contentPane.add(welcomeLabel);

		JLabel getStartedLabel = new JLabel("To get started get your UUID from the SyncNotes APP! Settings > UUID");
		getStartedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getStartedLabel.setBounds(20, 100, 404, 14);
		contentPane.add(getStartedLabel);

		uuidField = new JTextField();
		uuidField.setBounds(20, 155, 404, 20);
		contentPane.add(uuidField);
		uuidField.setColumns(10);

		JButton startButton = new JButton("Start!");
		startButton.setBounds(335, 228, 89, 23);
		contentPane.add(startButton);

		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String UUID = uuidField.getText();
				final ParseQuery<Note> query = ParseQuery.getQuery("Note");

		        query.whereEqualTo("ownerID", UUID);
		        query.findInBackground(new FindCallback<Note>() {
					@Override
					public void done(List<Note> arg0, ParseException arg1) {
						for (Note note: arg0) {
							NoteView noteView = new NoteView();
							noteView.frame = noteView.createFrame(note.getTitle(), note.getBody());
							noteView.showFrame();
						}
					}
		        });
			}
		});
	}
}
