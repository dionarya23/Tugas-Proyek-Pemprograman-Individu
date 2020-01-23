package p10517016;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class FUtama extends javax.swing.JFrame {
    Connection conn;
    
    /**
     * Creates new form FUtama
     */
    public FUtama() {
        initComponents();
        conn = koneksi.getConnection();
        FLogin.setVisible(false);
        menuData.setVisible(false); // menu Data
        menuPelayanan.setVisible(false);  // menu Pelayanan

        menuLogin.setEnabled(true); // menu item Login
        menuLogout.setEnabled(false); // menu item Logout
        menuAnggota.setEnabled(false);  // menu item Anggota
    }
    
    public void login(){
        String sql;
        String username=txtUser.getText();
        String password=txtPass.getText();
        try {      
        sql ="Select * from user WHERE username ='" +txtUser.getText() + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs =st.executeQuery();      
        if (rs.next()) {
             String pass = rs.getString("password");
             String bagian = rs.getString("bagian");
             if (password.equals(pass)){
                 JOptionPane.showMessageDialog(this, "Login Berhasil");
                 if(bagian.equals("sirkulasi")){
                   menuData.setVisible(false); // menu Data
                   menuPelayanan.setVisible(true);  // menu Pelayanan

                   menuLogin.setEnabled(false); // menu item Login
                   menuLogout.setEnabled(true); // menu item Logout
                   menuAnggota.setEnabled(true);  // menu item Anggota
                   
                   FLogin.setVisible(false);
                 }
                 else
                 {
                   menuData.setVisible(true); // menu Data
                   menuPelayanan.setVisible(true); // menu Pelayanan

                   menuLogin.setEnabled(false); // menu item Login
                   menuLogout.setEnabled(true); // menu item Logout
                   menuAnggota.setEnabled(true);  // menu item Anggota
                   
                   FLogin.setVisible(false);
                 }
             }
             else
                  JOptionPane.showMessageDialog(this, "Ada Kesalahan pada Username/Password");
        } else
          JOptionPane.showMessageDialog(this, "Ada Kesalahan pada Username/Password");
      }
      catch (Exception e) {JOptionPane.showMessageDialog(this,e.getMessage());
      }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuLogin = new javax.swing.JMenuItem();
        menuLogout = new javax.swing.JMenuItem();
        menuExit = new javax.swing.JMenuItem();
        menuData = new javax.swing.JMenu();
        menuAnggota = new javax.swing.JMenuItem();
        menuBuku = new javax.swing.JMenuItem();
        menuPelayanan = new javax.swing.JMenu();
        menuPeminjaman = new javax.swing.JMenuItem();
        menuPengembalian = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        FLogin.setBackground(new java.awt.Color(0, 128, 128));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login");

        txtUser.setText("Username");

        txtPass.setText("Password");
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FLoginLayout = new javax.swing.GroupLayout(FLogin);
        FLogin.setLayout(FLoginLayout);
        FLoginLayout.setHorizontalGroup(
            FLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FLoginLayout.createSequentialGroup()
                .addContainerGap(111, Short.MAX_VALUE)
                .addGroup(FLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FLoginLayout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FLoginLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(144, 144, 144))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FLoginLayout.createSequentialGroup()
                            .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(101, 101, 101)))
                    .addGroup(FLoginLayout.createSequentialGroup()
                        .addGroup(FLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, FLoginLayout.createSequentialGroup()
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(82, 82, 82))))
        );
        FLoginLayout.setVerticalGroup(
            FLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FLoginLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(FLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnCancel))
                .addGap(51, 51, 51)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));

        menuFile.setText("File");

        menuLogin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        menuLogin.setText("Login");
        menuLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLoginActionPerformed(evt);
            }
        });
        menuFile.add(menuLogin);

        menuLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuLogout.setText("Logout");
        menuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogoutActionPerformed(evt);
            }
        });
        menuFile.add(menuLogout);

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        menuFile.add(menuExit);

        jMenuBar1.add(menuFile);

        menuData.setText("Data");
        menuData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDataActionPerformed(evt);
            }
        });

        menuAnggota.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuAnggota.setText("Anggota");
        menuAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAnggotaActionPerformed(evt);
            }
        });
        menuData.add(menuAnggota);

        menuBuku.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        menuBuku.setText("Buku");
        menuBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBukuActionPerformed(evt);
            }
        });
        menuData.add(menuBuku);

        jMenuBar1.add(menuData);

        menuPelayanan.setText("Pelayanan");

        menuPeminjaman.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menuPeminjaman.setText("Peminjaman");
        menuPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPeminjamanActionPerformed(evt);
            }
        });
        menuPelayanan.add(menuPeminjaman);

        menuPengembalian.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        menuPengembalian.setText("Pengembalian");
        menuPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPengembalianActionPerformed(evt);
            }
        });
        menuPelayanan.add(menuPengembalian);

        jMenuBar1.add(menuPelayanan);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(FLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLoginActionPerformed
        // TODO add your handling code here:
        FLogin.setVisible(true);
        menuLogin.setEnabled(false); // menu item Login
        menuLogout.setEnabled(true); // menu item Logout
    }//GEN-LAST:event_menuLoginActionPerformed

    private void menuAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAnggotaActionPerformed
        // TODO add your handling code here:
        FAnggota faggota = new FAnggota();
        faggota.setVisible(true);
    }//GEN-LAST:event_menuAnggotaActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        // TODO add your handling code here:
        int selectedOption = JOptionPane.showConfirmDialog(null,
        "Anda Yakin Ingin Keluar Dari Aplikasi Ini?", "! KONFIRMASI !", JOptionPane.YES_NO_OPTION);
         if (selectedOption == JOptionPane.YES_OPTION) {
        dispose();
 }
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBukuActionPerformed
        // TODO add your handling code here:
        FBuku fbuku = new FBuku();
        fbuku.setVisible(true);
    }//GEN-LAST:event_menuBukuActionPerformed

    private void menuDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuDataActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        txtUser.setText("");
        txtPass.setText("");
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        login();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedOption = JOptionPane.showConfirmDialog(null,
        "Anda Yakin Ingin Keluar Dari Aplikasi Ini?", "! KONFIRMASI !", JOptionPane.YES_NO_OPTION);
         if (selectedOption == JOptionPane.YES_OPTION) {
        dispose();
 }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void menuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogoutActionPerformed
        // TODO add your handling code here:
        initComponents();
        menuData.setVisible(false); // menu Data
        menuPelayanan.setVisible(false);  // menu Pelayanan

        menuLogin.setEnabled(true); // menu item Login
        menuLogout.setEnabled(false); // menu item Logout
        menuAnggota.setEnabled(true);  // menu item Anggota
    }//GEN-LAST:event_menuLogoutActionPerformed

    private void menuPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPeminjamanActionPerformed
        // TODO add your handling code here:
        FPinjamBuku pinjam = new FPinjamBuku();
        pinjam.setVisible(true);
    }//GEN-LAST:event_menuPeminjamanActionPerformed

    private void menuPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPengembalianActionPerformed
        // TODO add your handling code here:
        FPengembalianBuku pengbuku = new FPengembalianBuku();
        pengbuku.setVisible(true);
    }//GEN-LAST:event_menuPengembalianActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FLogin;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuAnggota;
    private javax.swing.JMenuItem menuBuku;
    private javax.swing.JMenu menuData;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuLogin;
    private javax.swing.JMenuItem menuLogout;
    private javax.swing.JMenu menuPelayanan;
    private javax.swing.JMenuItem menuPeminjaman;
    private javax.swing.JMenuItem menuPengembalian;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}