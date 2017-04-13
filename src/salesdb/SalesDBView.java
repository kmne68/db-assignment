/*
 * SalesDBView.java
 */

package salesdb;

import business.Customer;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * The application's main frame.
 */
public class SalesDBView extends FrameView {
    
    String dbURL = "jdbc:mysql://localhost:3306/salesdb?useSSL=false";
    String dbUser = "root";
    String dbPwd = "";
    boolean loading = false; // stops statsu message from changing on load events

    public SalesDBView(SingleFrameApplication app) {
        super(app);

        initComponents();
        
        tbl_sales.setName(null); // to eliminate warning in some cases

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SalesDBApp.getApplication().getMainFrame();
            aboutBox = new SalesDBAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SalesDBApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmb_customers = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_sales = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        btn_clear = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        mnu_load = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(salesdb.SalesDBApp.class).getContext().getResourceMap(SalesDBView.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        cmb_customers.setName("cmb_customers"); // NOI18N
        cmb_customers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_customersItemStateChanged(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbl_sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_sales.setName("tbl_sales"); // NOI18N
        jScrollPane1.setViewportView(tbl_sales);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        txt_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_total.setText(resourceMap.getString("txt_total.text")); // NOI18N
        txt_total.setName("txt_total"); // NOI18N

        btn_clear.setText(resourceMap.getString("btn_clear.text")); // NOI18N
        btn_clear.setName("btn_clear"); // NOI18N
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(cmb_customers, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_clear)
                .addGap(295, 295, 295))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmb_customers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_clear)
                .addGap(46, 46, 46))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        mnu_load.setText(resourceMap.getString("mnu_load.text")); // NOI18N
        mnu_load.setName("mnu_load"); // NOI18N
        mnu_load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_loadActionPerformed(evt);
            }
        });
        fileMenu.add(mnu_load);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(salesdb.SalesDBApp.class).getContext().getActionMap(SalesDBView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 466, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void mnu_loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_loadActionPerformed
        loading = true;
        
        statusMessageLabel.setText("");
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPwd);
            Statement s = conn.createStatement();
            sql = "SELECT * FROM customer ORDER BY name";            
            ResultSet rs = s.executeQuery(sql);
            
            rs.last();
            statusMessageLabel.setText("Customers " + rs.getRow());
            
            // create a customer object for every customer in the result set
            rs.first(); // returns us to the top of hte result set
            do {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CUSTOMER_ID"));
                c.setCustomerName(rs.getString("NAME"));
                c.setAddress(rs.getString("ADDRESS"));
                c.setCity(rs.getString("CITY"));
                c.setState(rs.getString("STATE"));
                c.setZip(rs.getString("ZIP_CODE"));
                c.setAreaCode(rs.getInt("AREA_CODE"));
                c.setPhoneNumber(rs.getInt("PHONE_NUMBER"));
                c.setSalesID(rs.getInt("SALESPERSON_ID"));
                c.setCreditLimit(rs.getDouble("credit_limit"));
                c.setComments(rs.getString("comments"));
                
                cmb_customers.addItem(c);
            } while (rs.next());
            rs.close();
            conn.close();
            cmb_customers.setSelectedIndex(-1);
        }
        catch (SQLException e){
            statusMessageLabel.setText("SQL Error: " + e + " " + sql);
        } 
        catch (Exception e) {
            statusMessageLabel.setText("General Err0r");
        }
        loading = false;
    }//GEN-LAST:event_mnu_loadActionPerformed

    private void cmb_customersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_customersItemStateChanged
        
        if(loading) {
            return;
        }
        
        statusMessageLabel.setText("");
        
        if(cmb_customers.getSelectedIndex() == -1) {
            // no selection
            statusMessageLabel.setText("No customer selected");
            return;
        }
        
        // get customer from combo box
        Customer c = (Customer) cmb_customers.getSelectedItem();
        String[][] t;
        DefaultTableModel m; // tbl_sales
        
        String[] cols = {"Order Date", "Order ID", "Ship Date", "Item Count", "Order Total" };
        
        String sql = "SELECT s.order_date, s.order_id, s.ship_date, count(i.item_id) AS ItemCount, s.total " +
                "FROM sales_order s, item i WHERE s.order_id = i.order_id AND (s.customer_id = ?)" +
                "GROUP BY s.order_date, s.order_id, s.ship_date, s.total ORDER BY s.order_date, s.order_id";
        
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPwd);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c.getCustomerID()); // put variable in for question mark
            ResultSet r = ps.executeQuery();
            r.last(); // number of items in set
            int lastRow = r.getRow();
            
            if(lastRow == 0) {
                // set table for no sales message
                tbl_sales.setModel(new DefaultTableModel());
                statusMessageLabel.setText("No sales on file.");
            } else {
                statusMessageLabel.setText("Orders on file = " + lastRow);
                t = new String[lastRow][5]; // rows = last row, columns = 5
                m = new DefaultTableModel(t, cols);
                tbl_sales.setModel(m);
                
                r.first(); // go to top of set
                do {
                    int tr = r.getRow() - 1; // point to row we watn in the table
                    tbl_sales.setValueAt(r.getString("order_date"), tr, 0);
                    tbl_sales.setValueAt(r.getString("order_id"), tr, 1);
                    tbl_sales.setValueAt(r.getString("ship_date"), tr, 2);
                    tbl_sales.setValueAt(r.getInt("ItemCount"), tr, 3);
                    tbl_sales.setValueAt(r.getDouble("total"), tr, 4);
                } while (r.next());
                r.close();
                conn.close();
            }
            
        } catch(SQLException e) {
            statusMessageLabel.setText("SQL Error: " + e.getMessage());            
        }        
        
    }//GEN-LAST:event_cmb_customersItemStateChanged

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        
        statusMessageLabel.setText("");
        txt_total.setText("");
        tbl_sales.setModel(new DefaultTableModel());
        cmb_customers.setSelectedIndex(-1);

    }//GEN-LAST:event_btn_clearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JComboBox cmb_customers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem mnu_load;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTable tbl_sales;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
