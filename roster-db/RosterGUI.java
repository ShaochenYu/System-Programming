
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class RosterGUI {
    final static String PLAYERPANEL = "PLAYER";
    final static String TEAMPANEL = "TEAM";
	final static String LEAGUEPANEL = "LEAGUE";
    final static int extraWindowWidth = 100;
	private RosterDB rdb ;

	public RosterGUI(){
		rdb = new RosterDB();
	}
	
    public void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel card1 = new JPanel();
		JPanel card2 = new JPanel();
		JPanel card3 = new JPanel();
		
		
		TabPage tp1 = new TabPage(card1, rdb, 1);
		TabPage tp2 = new TabPage(card2, rdb, 2);
		TabPage tp3 = new TabPage(card3, rdb, 3);
		//createComponent(card1, 1);
        
		/*
        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));
		
		JPanel card3 = new JPanel();
        */
		tabbedPane.addTab(PLAYERPANEL, card1);
        tabbedPane.addTab(TEAMPANEL, card2);
		tabbedPane.addTab(LEAGUEPANEL, card3);

        pane.add(tabbedPane, BorderLayout.CENTER);
    }
	
    /*
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ROSTER SYSTEM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        RosterGUI demo = new RosterGUI();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
