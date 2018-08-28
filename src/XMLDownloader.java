import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class XMLDownloader extends JFrame implements ActionListener{
    private XMLDownloadPanel downloadPanel = new XMLDownloadPanel();

    /**
     * main(): runs program
     * @param args
     */
    public static void main(String[] args) {

        // set UI look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | ClassNotFoundException | InstantiationException ex) {
            ex.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            XMLDownloader frame = new XMLDownloader("iTunes RSS Feed UI");
        });

    }

    /**
     * XMLDownloader(): constructor for JFrame
     * @param title
     */
    public XMLDownloader(String title) {
        super(title);
        createAndShowGUI();
    }

    /**
     * createAndShowGUI(): sets up JFrame and calls function to create components
     */
    private void createAndShowGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 600));

        createMenu();
        getContentPane().add(downloadPanel);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * crateMenu(): create menus with options for building URL
     */
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(getTypeMenu());
        menuBar.add(getLimitMenu());
        menuBar.add(getExplicitMenu());

        setJMenuBar(menuBar);
    }

    /**
     * getTypeMenu(): creates and returns Type menu
     * @return
     */
    private JMenu getTypeMenu() {
        JMenu menu = new JMenu("Type");
        menu.setMnemonic(KeyEvent.VK_T);

        ButtonGroup buttonGroup = new ButtonGroup();

        // create New Music menu item
        JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem("New Music");
        radioButtonMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.setSelected(true);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        // create Recent Releases menu item
        radioButtonMenuItem = new JRadioButtonMenuItem("Recent Releases");
        radioButtonMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        // create Top Albums menu item
        radioButtonMenuItem = new JRadioButtonMenuItem("Top Albums");
        radioButtonMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        return menu;
    }

    /**
     * getLimitMenu(): creates and returns Limit menu
     * @return
     */
    private JMenu getLimitMenu() {
        JMenu menu = new JMenu("Limit");
        menu.setMnemonic(KeyEvent.VK_L);

        ButtonGroup buttonGroup = new ButtonGroup();

        // create 10 menu item
        JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem("10");
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.setSelected(true);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        // create 25 menu item
        radioButtonMenuItem = new JRadioButtonMenuItem("25");
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        // create 50 menu item
        radioButtonMenuItem = new JRadioButtonMenuItem("50");
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        // create 100 menu item
        radioButtonMenuItem = new JRadioButtonMenuItem("100");
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        return menu;
    }

    /**
     * getExplicitMenu(): creates and returns Explicit menu
     * @return
     */
    private JMenu getExplicitMenu() {
        JMenu menu = new JMenu("Explicit");
        menu.setMnemonic(KeyEvent.VK_E);

        ButtonGroup buttonGroup = new ButtonGroup();

        // create Yes menu item
        JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem("Yes");
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.setSelected(true);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        // create No menu item
        radioButtonMenuItem = new JRadioButtonMenuItem("No");
        buttonGroup.add(radioButtonMenuItem);
        radioButtonMenuItem.addActionListener(this);
        menu.add(radioButtonMenuItem);

        return menu;
    }

    /**
     * actionPerformed(): implements menu listener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New Music":
                downloadPanel.setType("new-music/all/");
                break;
            case "Recent Releases":
                downloadPanel.setType("recent-releases/all/");
                break;
            case "Top Albums":
                downloadPanel.setType("top-albums/all/");
                break;
            case "10":
                downloadPanel.setLimit("10/");
                break;
            case "25":
                downloadPanel.setLimit("25/");
                break;
            case "50":
                downloadPanel.setLimit("50/");
                break;
            case "100":
                downloadPanel.setLimit("100/");
                break;
            case "Yes":
                downloadPanel.setExplicit("explicit");
                break;
            case "No":
                downloadPanel.setExplicit("non-explicit");
                break;
        }
    }
}
