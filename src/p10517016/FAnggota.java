package p10517016;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class FAnggota extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel tabModel;

    public FAnggota() {
        initComponents();
        conn = koneksi.getConnection();
        setJTable();
    }
    private void setJTable(){
    String [] JudulKolom={"No","No Anggota","Nama Anggota","Alamat"};
    tabModel = new DefaultTableModel(null, JudulKolom){
                  boolean[] canEdit = new boolean [] { false, false, false, false};
                  @Override
                  public boolean isCellEditable(int rowIndex, int columnIndex) {
                   return canEdit [columnIndex];
                  }
              };
    TAnggota.setModel(tabModel);
    TAnggota.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    TAnggota.getColumnModel().getColumn(0).setPreferredWidth(30);
    TAnggota.getColumnModel().getColumn(1).setPreferredWidth(100);
    TAnggota.getColumnModel().getColumn(2).setPreferredWidth(200);
    TAnggota.getColumnModel().getColumn(3).setPreferredWidth(300);

    getData();
} // Akhir setJTable
    
    private void getData(){
          try{   
        String sql="Select * from Anggota";
        PreparedStatement st=conn.prepareStatement(sql);
        ResultSet rs=st.executeQuery();
        String NoAnggota,Nama,Alamat;
        int no=0;
        while(rs.next()){
         no=no+1;
         NoAnggota=rs.getString("NoAnggota");
         Nama=rs.getString("Nama");
         Alamat=rs.getString("Alamat");

         Object Data[]={no,NoAnggota,Nama,Alamat};
         tabModel.addRow(Data);
        }
    }
    catch (SQLException sqle) {                  
           System.out.println("Proses Query Gagal = " + sqle);
           System.exit(0);
    }
    catch(Exception e){
           System.out.println("Koneksi Access Gagal " +e.getMessage());
           System.exit(0);
    }
    }
    
    void simpanData(){
     try{            
            String sql="Insert into Anggota values(?,?,?)";
            PreparedStatement st=conn.prepareStatement(sql);
                st.setString(1, txtNoAnggota.getText());
                st.setString(2, txtNama.getText());
                st.setString(3, txtAlamat.getText());
            int rs=st.executeUpdate();

            if(rs>0){
            JOptionPane.showMessageDialog(this,"Input Berhasil");
      	    setJTable();
            }
        }
        catch (SQLException sqle) {
           JOptionPane.showMessageDialog(this,"Input  Gagal = " + sqle.getMessage());
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(this,"Koneksi Gagal " +e.getMessage());
        }
    }
    void ambilData_dari_JTable() {
    int row = TAnggota.getSelectedRow();
    String NoAnggota = tabModel.getValueAt(row, 1).toString();
    String Nama = tabModel.getValueAt(row, 2).toString();
    String Alamat = tabModel.getValueAt(row, 3).toString();
    
    txtNoAnggota.setText(NoAnggota);
    txtNama.setText(Nama);
    txtAlamat.setText(Alamat);
    }
    public void hapusIsiJTable() {
    int row = tabModel.getRowCount();
    for (int i = 0; i < row; i++) {
      tabModel.removeRow(0);
    }
   }
    
    public void tampilDataKeJTable() {
    hapusIsiJTable();
    try {
        String sql="Select * from Anggota";
        PreparedStatement st=conn.prepareStatement(sql);
        ResultSet rs=st.executeQuery();
        String NoAnggota,Nama,Alamat;
        int no=0;
        while(rs.next()){
         no=no+1;
         NoAnggota=rs.getString("NoAnggota");
         Nama=rs.getString("Nama");
         Alamat=rs.getString("Alamat");
         Object Data[]={no,NoAnggota,Nama,Alamat};
         tabModel.addRow(Data);
      }
  }
    catch (Exception e) {
    JOptionPane.showMessageDialog(this,"Gagal Tampil");}
 }
    
    public void updateData() {
    int ok = JOptionPane.showConfirmDialog(this,
        "Anda Yakin Ingin Mengubah Data\n Dengan No Anggota = '" + txtNoAnggota.getText() +
        "'", "! Konfirmasi Ubah Data !",JOptionPane.YES_NO_OPTION);
    if (ok == 0) {
      try {
        String sql ="UPDATE Anggota SET Nama = ?, Alamat= ? WHERE NoAnggota = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        
          st.setString(1, txtNama.getText());
          st.setString(2, txtAlamat.getText());
          st.setString(3, txtNoAnggota.getText());
          int rs=st.executeUpdate();

          if(rs>0){
            JOptionPane.showMessageDialog(this,"Edit Data Berhasil");
      	    tampilDataKeJTable();
          }         

          txtNoAnggota.setText("");
          txtNama.setText("");
          txtAlamat.setText(""); 
      }
      catch (SQLException sqle) {
           JOptionPane.showMessageDialog(this,"Edit Gagal = " + sqle.getMessage());
        }
   }
  }
    
    public void hapusData(){
    ambilData_dari_JTable();
    int ok = JOptionPane.showConfirmDialog(this,
        "Anda Yakin Ingin Menghapus Data\nDengan No Anggota = '" + txtNoAnggota.getText() +
        "'", "! Konfirmasi Menghapus Data !",JOptionPane.YES_NO_OPTION);
    if (ok == 0) { 
      try {       
        String sql = "DELETE FROM Anggota WHERE NoAnggota = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, txtNoAnggota.getText());
        int rs=st.executeUpdate();
        if(rs>0){
        tampilDataKeJTable();
        JOptionPane.showMessageDialog(this,"Data Berhasil dihapus");
        }
        txtNoAnggota.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
      } catch (Exception se) {
         JOptionPane.showMessageDialog(this,"Gagal Hapus Data!");
       }
    }
   }
    public void cariData(){
        String sql;
        int pilih = CBCari.getSelectedIndex();
        String cari = txtCari.getText();
    try {
        if(pilih==0)
        sql ="Select * from Anggota WHERE NoAnggota ='" +txtCari.getText() + "'";
        else
        sql ="Select * from Anggota WHERE Nama Like '%" +txtCari.getText() + "%'";

        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs =st.executeQuery();

        hapusIsiJTable();
        int no=0;
        while (rs.next()) {
            no++;
             String NoAnggota = rs.getString("NoAnggota");
             String Nama = rs.getString("Nama");
             String Alamat = rs.getString("Alamat");
             Object [] data = {no,NoAnggota, Nama, Alamat};
             tabModel.addRow(data);
        }

         if(tabModel.getRowCount()>0)         
           JOptionPane.showMessageDialog(this,"Data Ditemukan ");        
        else
            JOptionPane.showMessageDialog(this,"Data Tidak Ditemukan.. ");

      }
      catch (SQLException se) {
      JOptionPane.showMessageDialog(this, "Koneksi Gagal" + se.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNoAnggota = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TAnggota = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        CBCari = new javax.swing.JComboBox<>();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnPreview = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        txtAlamat.setEnabled(false);
        jScrollPane2.setViewportView(txtAlamat);

        jLabel1.setText("No Anggota");

        jLabel2.setText("Nama");

        jLabel3.setText("Alamat");

        txtNoAnggota.setEnabled(false);
        txtNoAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoAnggotaActionPerformed(evt);
            }
        });

        txtNama.setEnabled(false);

        btnTambah.setBackground(new java.awt.Color(0, 128, 128));
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/p10517016/images/add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(0, 128, 128));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/p10517016/images/save.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.setEnabled(false);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(0, 128, 128));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/p10517016/images/edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(0, 128, 128));
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/p10517016/images/batal.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(0, 128, 128));
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/p10517016/images/hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnKeluar.setBackground(new java.awt.Color(0, 128, 128));
        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/p10517016/images/keluar.png"))); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        TAnggota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TAnggota);

        jLabel4.setText("Cari Data");

        CBCari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Anggota", "Nama" }));
        CBCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBCariActionPerformed(evt);
            }
        });

        btnCari.setBackground(new java.awt.Color(0, 128, 128));
        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/p10517016/images/cari.png"))); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(0, 128, 128));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/p10517016/images/reset.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(0, 128, 128));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Data Anggota Perpustakaan");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        btnPreview.setBackground(new java.awt.Color(0, 128, 128));
        btnPreview.setText("Preview Report");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 128, 128));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("© 2020 • 10517016 - Dion Arya Pamungkas ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(234, 234, 234))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNama, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNoAnggota, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnTambah))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnPreview)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(CBCari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEdit)
                            .addComponent(btnBatal)
                            .addComponent(btnTambah))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapus)
                            .addComponent(btnSimpan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnKeluar)
                            .addComponent(btnPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addComponent(btnCari)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNoAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        txtNoAnggota.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        
        // Mengatur  Enable Tombol Dan Textfield  
        txtNoAnggota.setEnabled(false);
        txtNama.setEnabled(false);
        txtAlamat.setEnabled(false);

        btnTambah.setEnabled(true);
        btnSimpan.setEnabled(false);
        btnEdit.setEnabled(true);
        btnBatal.setEnabled(true);
        btnHapus.setEnabled(true);
        btnKeluar.setEnabled(true);

    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        int selectedOption = JOptionPane.showConfirmDialog(null,
        "Anda Yakin Ingin Keluar Dari Form Anggota?", "! KONFIRMASI !", JOptionPane.YES_NO_OPTION);
         if (selectedOption == JOptionPane.YES_OPTION) {
        dispose();
         }
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:  
        
        // Mengatur  Enable Tombol Dan Textfield  
        txtNoAnggota.setEnabled(true);
        txtNama.setEnabled(true);
        txtAlamat.setEnabled(true);
        btnSimpan.setText("Simpan");

        btnTambah.setEnabled(false);
        btnSimpan.setEnabled(true);
        btnEdit.setEnabled(false);
        btnBatal.setEnabled(true);
        btnHapus.setEnabled(false);
        btnKeluar.setEnabled(false);

    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        if(btnSimpan.getText().equalsIgnoreCase("Simpan"))
        simpanData();
        else
        updateData();
        
        txtNoAnggota.setEnabled(false); // txtNoAnggota Tidak Aktif
        txtNama.setEnabled(false);
        txtAlamat.setEnabled(false);
        
        btnTambah.setEnabled(true);
        btnSimpan.setEnabled(false);
        btnEdit.setEnabled(true);
        btnBatal.setEnabled(false);
        btnHapus.setEnabled(true);
        btnKeluar.setEnabled(true);       


    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        txtNoAnggota.setEnabled(false); // txtNoAnggota Tidak Aktif
        txtNama.setEnabled(true);       // txtNama          Aktif
        txtAlamat.setEnabled(true);     // txtAlamat Aktif

        btnSimpan.setText("Update"); // Merubah Teks Tombol Simpan
        
        btnTambah.setEnabled(false);
        btnSimpan.setEnabled(true);
        btnEdit.setEnabled(true);
        btnBatal.setEnabled(true);
        btnHapus.setEnabled(false);
        btnKeluar.setEnabled(false);
        
        // Memanggil Method  ambilData_dari_JTable()
        ambilData_dari_JTable();

    }//GEN-LAST:event_btnEditActionPerformed

    private void CBCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBCariActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        cariData();
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtNoAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoAnggotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoAnggotaActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        txtCari.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        String reportSource;
        String reportDest;

        try{
            //reportSource="E:/INFORMATION SYSTEM - DATA/Tugas/Semester 5/Tugas Proyek Pemograman/Proyek/P10517088/report/Anggota.jrxml";
            //reportDest="E:/INFORMATION SYSTEM - DATA/Tugas/Semester 5/Tugas Proyek Pemograman/Proyek/P10517088/report/Anggota.html";
            //reportSource="E:/TPP_10517088/P10517088/report/Anggota.jrxml";
            //reportDest="E:/TPP_10517088/P10517088/report/Anggota.html";
            
            reportSource= System.getProperty("user.dir")+"/report/Anggota.jrxml";
            reportDest= System.getProperty("user.dir")+"/report/Anggota.html";


            JasperReport jasperReport=JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,null,conn);
            JasperExportManager.exportReportToHtmlFile(jasperPrint,reportDest);
            JasperViewer.viewReport(jasperPrint,false);

        }catch(Exception e){
        System.out.println("Gagal"+e.getMessage());
        }

    }//GEN-LAST:event_btnPreviewActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBCari;
    private javax.swing.JTable TAnggota;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoAnggota;
    // End of variables declaration//GEN-END:variables

}
