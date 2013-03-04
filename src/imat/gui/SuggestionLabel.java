package imat.gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JButton;

public class SuggestionLabel extends JButton {
	
	public SuggestionLabel(String s, ActionListener listener) {
		super(s);
		setOpaque(false);
		setBorderPainted(false);
		setForeground(Color.BLUE);
		Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
		map.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
		setFont(getFont().deriveFont(map));
		setActionCommand("search");
		addActionListener(listener);
	}
	
}
