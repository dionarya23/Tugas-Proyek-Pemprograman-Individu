package p10517016;
import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class FPengembalianBuku extends javax.swing.JFrame {
    Connection conn;
    public DefaultTableModel tabModel;


    public FPengembalianBuku() {
        initComponents();
        conn = koneksi.getConnection();
        Date tgl_sekarang=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String tgl=format.format(tgl_sekarang);
        txtTglKembali.setText(tgl);
        setJTable();
        
        txtNoAnggota.setEnabled(false);
        txtTglPinjam.setEnabled(false);
        txtDenda.setEnabled(false);
        txtTotal.setEnabled(false);
        txtKeterlambatan.setEnabled(false);
        txtTglKembali.setEnabled(false);
    }
    
    private void setJTable(){
    String [] JudulKolom={"No","No Pinjam","No Anggota","Tgl Pengembalian","Tgl Kembalikan","Keterlambatan","Denda","Total"};
    tabModel = new DefaultTableModel(null, JudulKolom){
                  boolean[] canEdit = new boolean [] { false, false, false, false, false, false, false};
                  @Override
                  public boolean isCellEditable(int rowIndex, int columnIndex) {
                   return canEdit [columnIndex];
                  }
              };
    TK.setModel(tabModel);
    TK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    TK.getColumnModel().getColumn(0).setPreferredWidth(30);
    TK.getColumnModel().getColumn(1).setPreferredWidth(100);
    TK.getColumnModel().getColumn(2).setPreferredWidth(100);
    TK.getColumnModel().getColumn(3).setPreferredWidth(100);
    TK.getColumnModel().getColumn(4).setPreferredWidth(100);
    TK.getColumnModel().getColumn(5).setPreferredWidth(100);
    TK.getColumnModel().getColumn(6).setPreferredWidth(100);
    TK.getColumnModel().getColumn(7).setPreferredWidth(100);
    getData();
} // Akhir setJTable
    
    private void Kosongkan(){

     int row=tabModel.getRowCount();
     for(int i=0;i<row;i++){
         tabModel.removeRow(0);
     }

     txtNoPinjam.setEnabled(false);
     txtNoAnggota.setEnabled(false);
     txtTglPinjam.setEnabled(false);
     txtTglKembali.setEnabled(false);
     txtKeterlambatan.setEnabled(false);
     txtDenda.setEnabled(false);
     txtTotal.setEnabled(false);
 }
    
    private void getData(){
          try{   
        String sql="Select * from kembalibuku";
        PreparedStatement st=conn.prepareStatement(sql);
        ResultSet rs=st.executeQuery();
        String NoPinjam,NoAnggota,TglPengembalian,TglKembalikan,Keterlambatan,Denda,Total;
        int no=0;
        while(rs.next()){
         no=no+1;
         NoPinjam=rs.getString("NoPinjam");
         NoAnggota=rs.getString("NoAnggota");
         TglPengembalian=rs.getString("TglPengembalian");
         TglKembalikan=rs.getString("TglKembalikan");
         Keterlambatan=rs.getString("Keterlambatan");
         Denda=rs.getString("Denda");
         Total=rs.getString("Total");

         Object Data[]={no,NoPinjam,NoAnggota,TglPengembalian,TglKembalikan,Keterlambatan,Denda,Total};
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
    } // Akhir Method getData
    
    void simpanData(){    
     try{            
            String sql="Insert into kembalibuku values(?,?,?,?,?,?,?)";
            PreparedStatement st=conn.prepareStatement(sql);
                st.setString(1, txtNoPinjam.getText());
                st.setString(2, txtNoAnggota.getText());
                st.setString(3, txtTglPinjam.getText());
                st.setString(4, txtTglKembali.getText());
                st.setString(5, txtKeterlambatan.getText());
                st.setString(6, txtDenda.getText());
                st.setString(7, txtTotal.getText());
            int rs=st.executeUpdate();

            if(rs>0){
            JOptionPane.showMessageDialog(this,"Input Berhasil");
            txtNoPinjam.setText("");
            txtNoAnggota.setText("");
            txtTglPinjam.setText("");
            txtKeterlambatan.setText("");
            txtDenda.setText("");
            txtTotal.setText("");
            
            btnTambah.setEnabled(true);
            btnSimpan.setEnabled(false);
            btnBatal.setEnabled(false);
            btnHapus.setEnabled(true);
            btnClose.setEnabled(true);
            
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
    
    public void hapusData() {
    // Konfirmasi sebelum melakukan penghapusan data
    ambilData_dari_JTable();
    int ok = JOptionPane.showConfirmDialog(this,
        "Anda Yakin Ingin Menghapus Data\nDengan No Pinjam = '" + txtNoPinjam.getText() +
        "'", "Konfirmasi Menghapus Data",JOptionPane.YES_NO_OPTION);
    if (ok == 0) {     // Apabila tombol OK ditekan
      try {       
        String sql = "DELETE FROM kembalibuku WHERE NoPinjam = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, txtNoPinjam.getText());
        int rs=st.executeUpdate();
        if(rs>0){
        tampilDataKeJTable();
        JOptionPane.showMessageDialog(this,"Data Berhasil dihapus");
        }
        txtNoPinjam.setText("");
        txtNoAnggota.setText("");
        txtTglPinjam.setText("");
        txtTglKembali.setText("");
        txtKeterlambatan.setText("");
        txtDenda.setText("");
        txtTotal.setText("");
      } catch (Exception se) {  // Silahkan tambahkan catch Exception yang lain
         JOptionPane.showMessageDialog(this,"Gagal Hapus Data.. ");
       }
    }
    }
    void ambilData_dari_JTable() {
    int row = TK.getSelectedRow();

    // Mengambil data yang dipilih pada JTable
    String NoPinjam = tabModel.getValueAt(row, 1).toString();
    String NoAnggota = tabModel.getValueAt(row, 2).toString();
    String TglPengembalian = tabModel.getValueAt(row, 3).toString();
    String TglKembalikan = tabModel.getValueAt(row, 4).toString();
    String Keterlambatan = tabModel.getValueAt(row, 5).toString();
    String Denda = tabModel.getValueAt(row, 6).toString();
    String Total = tabModel.getValueAt(row, 7).toString();
    
    txtNoPinjam.setText(NoPinjam);
    txtNoAnggota.setText(NoAnggota);
    txtTglPinjam.setText(TglPengembalian);
    txtTglKembali.setText(TglKembalikan);
    txtKeterlambatan.setText(Keterlambatan);
    txtDenda.setText(Denda);
    txtTotal.setText(Total);
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
        String sql="Select * from kembalibuku";
        PreparedStatement st=conn.prepareStatement(sql);
        ResultSet rs=st.executeQuery();
        String NoPinjam,NoAnggota,TglPengembalian,TglKembalikan,Keterlambatan,Denda,Total;
        int no=0;
        while(rs.next()){
         no=no+1;
         NoPinjam=rs.getString("NoPinjam");
         NoAnggota=rs.getString("NoAnggota");
         TglPengembalian=rs.getString("TglPengembalian");
         TglKembalikan=rs.getString("TglKembalikan");
         Keterlambatan=rs.getString("Keterlambatan");
         Denda=rs.getString("Denda");
         Total=rs.getString("Total");

         Object Data[]={no,NoPinjam,NoAnggota,TglPengembalian,TglKembalikan,Keterlambatan,Denda,Total};
         tabModel.addRow(Data);
        }
  }
    catch (Exception e) { }
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNoPinjam = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNoAnggota = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTglPinjam = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTglKembali = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDenda = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtKeterlambatan = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        TK = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("No Pinjam");

        txtNoPinjam.setEnabled(false);
        txtNoPinjam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoPinjamKeyPressed(evt);
            }
        });

        jLabel3.setText("No Anggota");

        txtNoAnggota.setEnabled(false);
        txtNoAnggota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoAnggotaKeyPressed(evt);
            }
        });

        jLabel4.setText("Tgl Pinjam");

        txtTglPinjam.setEnabled(false);

        jLabel10.setText("Tgl Kembali");

        txtTglKembali.setEnabled(false);
        txtTglKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTglKembaliActionPerformed(evt);
            }
        });

        jLabel11.setText("Denda");

        txtTotal.setEnabled(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        jLabel12.setText("Total");

        txtDenda.setEnabled(false);

        jLabel13.setText("Keterlambatan");

        txtKeterlambatan.setEnabled(false);
        txtKeterlambatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKeterlambatanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNoAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(33, 33, 33)
                        .addComponent(txtTglPinjam))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtKeterlambatan, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(34, 34, 34)
                                .addComponent(txtNoPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                    .addComponent(txtDenda)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(txtTglKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNoPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNoAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTglPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTglKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtKeterlambatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TK.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(TK);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.setEnabled(false);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnPreview.setText("Preview Report");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 128, 128));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Pengembalian Buku");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(0, 128, 128));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("© 2020 • 10517016 - Dion Arya Pamungkas ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(158, 158, 158))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPreview, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatal)
                        .addGap(18, 18, 18)
                        .addComponent(btnClose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPreview))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTglKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTglKembaliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTglKembaliActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtNoPinjamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoPinjamKeyPressed
        // TODO add your handling code here:
        String NoPinjam = txtNoPinjam.getText();
        int ascii = evt.getKeyCode();
        if(ascii==10){
            try {
                String sql = "select * from pinjam where NoPinjam='"+ NoPinjam +"'";
                PreparedStatement st=conn.prepareStatement(sql);
                ResultSet rs=st.executeQuery();
                while (rs.next()){
                    txtTglPinjam.setText(rs.getString(2));
                    txtNoAnggota.setText(rs.getString(3));
                    btnHapus.enable(true);
                }
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Data Tidak Di Temukan ....!! \n"
                    +e.getMessage());
            txtNoPinjam.requestFocus();
            }
        }
        
        DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date TanggalPinjam=df.parse(txtTglPinjam.getText());
            Date TanggalKembali=df.parse(txtTglKembali.getText());
            
            // konversi tgl ke milidetik
            long haripinjam= TanggalPinjam.getTime();
            long harikembali= TanggalKembali.getTime();
            long diff=harikembali-haripinjam;
            long lama = diff / (24 * 60 * 60 * 1000);
            txtKeterlambatan.setText(Long.toString(lama));
          //Untuk ketelambatan serta denda
          int keterlambatan, total;
          int denda=0;
            keterlambatan=Integer.parseInt(txtKeterlambatan.getText());
            if (keterlambatan <= 3) {
                keterlambatan=0;
                denda=0;
            }
            if (keterlambatan >= 3) {
                keterlambatan=keterlambatan-3;
                denda=keterlambatan*1000;
            }
            txtKeterlambatan.setText(String.valueOf(keterlambatan));
            txtDenda.setText(String.valueOf(denda));
            
            total=denda+keterlambatan;
            txtTotal.setText(String.valueOf(total));
            
        } catch (Exception e) {
        }   
    }//GEN-LAST:event_txtNoPinjamKeyPressed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        int selectedOption = JOptionPane.showConfirmDialog(null,
        "Anda Yakin Ingin Keluar Dari Form Data Pengembalian Buku?", "! KONFIRMASI !", JOptionPane.YES_NO_OPTION);
         if (selectedOption == JOptionPane.YES_OPTION) {
        dispose();
         }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void txtNoAnggotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoAnggotaKeyPressed
    }//GEN-LAST:event_txtNoAnggotaKeyPressed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        
        
     txtNoPinjam.setText("");
     txtNoAnggota.setText("");
     txtTglPinjam.setText("");
     txtTglKembali.setText("");
     txtKeterlambatan.setText("");
     txtDenda.setText("");
     txtTotal.setText("");
        
        btnTambah.setEnabled(true);
        btnSimpan.setEnabled(false);
        btnBatal.setEnabled(false);
        btnHapus.setEnabled(true);
        btnClose.setEnabled(true);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        simpanData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        
        txtNoPinjam.setEnabled(true);
//        txtNoAnggota.setEnabled(true);
//        txtTglPinjam.setEnabled(true);
//        txtTglKembali.setEnabled(true);
//        txtKeterlambatan.setEnabled(true);
//        txtDenda.setEnabled(true);
//        txtTotal.setEnabled(true);
        
        btnTambah.setEnabled(false);
        btnSimpan.setEnabled(true);
        btnBatal.setEnabled(true);
        btnHapus.setEnabled(false);
        btnClose.setEnabled(false);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        String reportSource;
        String reportDest;

        try{
            //reportSource="E:/INFORMATION SYSTEM - DATA/Tugas/Semester 5/Tugas Proyek Pemograman/Proyek/P10517088/report/Pengembalian.jrxml";
            //reportDest="E:/INFORMATION SYSTEM - DATA/Tugas/Semester 5/Tugas Proyek Pemograman/Proyek/P10517088/report/Pengembalian.html";
            //reportSource="E:/TPP_10517088/P10517088/report/Pengembalian.jrxml";
            //reportDest="E:/TPP_10517088/P10517088/report/Pengembalian.html";
            
            reportSource= System.getProperty("user.dir")+"/report/Pengembalian.jrxml";
            reportDest= System.getProperty("user.dir")+"/report/Pengembalian.html";
            
            JasperReport jasperReport=JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,null,conn);
            JasperExportManager.exportReportToHtmlFile(jasperPrint,reportDest);
            JasperViewer.viewReport(jasperPrint,false);

        }catch(Exception e){
        System.out.println("Gagal"+e.getMessage());
        }
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void txtKeterlambatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeterlambatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeterlambatanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

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
            java.util.logging.Logger.getLogger(FPengembalianBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FPengembalianBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FPengembalianBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FPengembalianBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FPengembalianBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TK;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField txtDenda;
    private javax.swing.JTextField txtKeterlambatan;
    private javax.swing.JTextField txtNoAnggota;
    private javax.swing.JTextField txtNoPinjam;
    private javax.swing.JTextField txtTglKembali;
    private javax.swing.JTextField txtTglPinjam;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
