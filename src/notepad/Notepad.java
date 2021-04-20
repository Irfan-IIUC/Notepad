
package notepad;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



public class Notepad extends JFrame implements ActionListener {

    JTextArea ta;
    JScrollPane sp;
    String text;

    Notepad() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");

        JMenuItem New = new JMenuItem("New");
        New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        New.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAll.addActionListener(this);

        JMenuItem about = new JMenuItem("About Notepad");
        about.addActionListener(this);

        menubar.add(file);
        menubar.add(edit);
        menubar.add(help);

        file.add(New);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);

        help.add(about);

        setJMenuBar(menubar);

        ta = new JTextArea();
        ta.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        sp = new JScrollPane(ta);
        sp.setBorder(BorderFactory.createEmptyBorder());

        add(sp, BorderLayout.CENTER);

        setSize(500, 500);
        setTitle("Notepad++");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("New")) {
            ta.setText(null);

        } else if (e.getActionCommand().equals("Open")) {

            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter restrict = new FileNameExtensionFilter("only .txt file", "txt");

            chooser.addChoosableFileFilter(restrict);

            int action = chooser.showOpenDialog(this);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File name = chooser.getSelectedFile();

            try {
                BufferedReader ob1 = new BufferedReader(new FileReader(name));
                ta.read(ob1, null);

            } catch (FileNotFoundException ex) {

                Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {

                Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getActionCommand().equals("Save")) {

            JFileChooser saveas = new JFileChooser();
            saveas.setApproveButtonText("Save");

            int action = saveas.showOpenDialog(this);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File filename = new File(saveas.getSelectedFile() + ".txt");
            BufferedWriter ob = null;

            try {

                ob = new BufferedWriter(new FileWriter(filename));
                ta.write(ob);

            } catch (IOException ex) {
                Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getActionCommand().equals("Print")) {

            try {
                ta.print();

            } catch (PrinterException ex) {

                Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getActionCommand().equals("Exit")) {

            System.exit(0);

        } else if (e.getActionCommand().equals("Cut")) {

            ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());

        } else if (e.getActionCommand().equals("Copy")) {

            text = ta.getSelectedText();

        } else if (e.getActionCommand().equals("Paste")) {

            ta.insert(text, ta.getCaretPosition());

        } else if (e.getActionCommand().equals("Select All")) {

            ta.selectAll();

        } else if (e.getActionCommand().equals("About Notepad")) {

            new About().setVisible(true);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        new Notepad().setVisible(true);
        UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
    }

}
