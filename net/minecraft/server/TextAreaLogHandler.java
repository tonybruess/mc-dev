package net.minecraft.server;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.swing.JTextArea;

public class TextAreaLogHandler extends Handler {

    private int[] b = new int[1024];
    private int c;
    Formatter a = new TextAreaLogHandlerListener(this);
    private JTextArea d;

    public TextAreaLogHandler(JTextArea jtextarea) {
        this.setFormatter(this.a);
        this.d = jtextarea;
    }

    public void close() {}

    public void flush() {}

    public void publish(LogRecord logrecord) {
        int i = this.d.getDocument().getLength();

        this.d.append(this.a.format(logrecord));
        this.d.setCaretPosition(this.d.getDocument().getLength());
        int j = this.d.getDocument().getLength() - i;

        if (this.b[this.c] != 0) {
            this.d.replaceRange("", 0, this.b[this.c]);
        }

        this.b[this.c] = j;
        this.c = (this.c + 1) % 1024;
    }
}
