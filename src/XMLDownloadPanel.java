import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.DefaultTableModel;

public class XMLDownloadPanel extends JPanel implements ActionListener {

    private JButton getAlbumsButton = new JButton("Get Albums");
    private JTable outputTable;
    private String type = "new-music/all/";
    private String limit = "10/";
    private String explicit = "explicit";
    private XMLDownloadTask downloadTask;
    private String[] columnNames = {"Name", "Artist", "Genre"};
    private DefaultTableModel tableModel;

    /**
     * XMLDownloadPanel(): constructor for class
     */
    public XMLDownloadPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        tableModel = new DefaultTableModel(columnNames, 0);
        outputTable = new JTable(tableModel);
        outputTable.setShowGrid(false);

        getAlbumsButton.addActionListener(this);
        topPanel.add(getAlbumsButton);
        add(topPanel, BorderLayout.PAGE_START);
        add(new JScrollPane(outputTable), BorderLayout.CENTER);
    }

    /**
     * actionPerformed(): clears output and implements getAlbumsButton listener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        tableModel.setRowCount(0);
        download();
    }

    /**
     * download(): creates URL and creates task to connect
     */
    public void download() {
//        1. Build a URL string for the requested fetch.
        String urlString = "https://rss.itunes.apple.com/api/v1/us/itunes-music/"
                + type + limit + explicit + ".atom";

//        2. Create a new XMLDownloadTask and pass it the URL string and a reference to this (so
//        that the task can access the text area in this class to display the data it downloads).
        downloadTask = new XMLDownloadTask(urlString, this);

//        3. To prevent the user from pushing the “Get Albums” button repeatedly, you may want to
//        create a PropertyChangeListener for the task. When the task has STARTED, you can
//        disable the button. When the task is DONE, you can enable the button. (See the Swing
//        Worker example posted on Blackboard for an example of how to set this up.)
        downloadTask.addPropertyChangeListener(event -> {
            switch (event.getPropertyName()) {
                case "progress":
                    break;
                case "state":
                    switch ((StateValue) event.getNewValue()) {
                        case DONE:
                            // clock stuff
                            getAlbumsButton.setText("Get Albums");
                            getAlbumsButton.setEnabled(true);
                            downloadTask = null;
                            break;
                        case STARTED:
                        case PENDING:
                            Dimension d = getAlbumsButton.getPreferredSize();
                            getAlbumsButton.setText("Working..");
                            getAlbumsButton.setPreferredSize(d);
                            getAlbumsButton.setEnabled(false);
                            // clock stuff
                            break;
                    }
                    break;
            }
        });

//        4. Call the execute() method for the task.
        downloadTask.execute();
    }

    /**
     * displayAlbum():
     * @param album
     */
    public void displayAlbum(Album album) {
        tableModel.addRow(album.toList());
    }

    /**
     * setExplicit(): setter method for explicit
     * @param explicit
     */
    public void setExplicit(String explicit) {
        this.explicit = explicit;
    }

    /**
     * setType(): setter method for type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * setLimit(): setter method for limit
     * @param limit
     */
    public void setLimit(String limit) {
        this.limit = limit;
    }
}
