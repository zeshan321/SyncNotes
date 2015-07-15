package com.zeshan.syncnotes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;

public class NoteList extends JFrame {
	public NoteList() {
	}

	private JPanel contentPane;
	
	public NoteList frame;
	
	/**
	 * Create the frame.
	 */
	public NoteList createFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		List<String> data = new ArrayList<String>();
		final List<Note> noteData = new Database().getNoteList();
		
		for (Note note: noteData) {
			data.add(note.getTitle());
		}
		String[] strarray = new String[data.size()];
		strarray = data.toArray(strarray);
		
		final JList list = new JList(strarray);
		contentPane.add(list);
		
		
		list.addListSelectionListener(new ListSelectionListener() {
			  public void valueChanged(ListSelectionEvent event) {
			    if (!event.getValueIsAdjusting()) {
			    	int selected = list.getSelectedIndex();
			    	Note note = noteData.get(selected);
			    	
			    	NoteView noteView = new NoteView();
					noteView.frame = noteView.createFrame(note.getID(), note.getTitle(), note.getBody());
					noteView.showFrame();
			    }
			  }
			});
		
		return this;
	}
	
	public void showFrame() {
		frame.setVisible(true);
	}
}
