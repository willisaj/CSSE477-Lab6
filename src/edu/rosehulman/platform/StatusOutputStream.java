package edu.rosehulman.platform;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class StatusOutputStream extends OutputStream {
	private JTextArea text;
		public StatusOutputStream(JTextArea text) {
			this.text = text;
		}

		public void write(int b) throws IOException {
			this.text.append(String.valueOf((char) b));
			this.text.setCaretPosition(this.text.getDocument().getLength());
		}
	}