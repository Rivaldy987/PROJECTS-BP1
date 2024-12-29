/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Hp
 */
public class transaksi_bk extends javax.swing.JFrame {

    public final Connection conn = new koneksi().connect();
    private DefaultTableModel tabmod;
    private DefaultTableModel tabmodCustomer;
    
    
    public void tanggal(){
        Date tgl = new Date();
        dateBk.setDate(tgl);
    }
    
    public void auto_id_bk(){
        try{
            Connection con = new koneksi().connect();
            java.sql.Statement stat = con.createStatement();
            Date tanggal_update = new Date();
            String tampilan ="yyMM";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tanggal = String.valueOf(fm.format(dateBk.getDate()));
            String sql = "select max(right(id_bk,4)) as no from tb_bk where id_bk like'%" + "BK" + tanggal.toString() +"%'";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()){
                if(rs.first() == false){
                    txtIdBk.setText("BK" + tanggal.toString() + "0001");
                }else{
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int no_id = no.length();
                    //jumlah nomor 0
                    for (int j = 0; j < 4 - no_id; j++){
                        no = "0"+ no;
                    }
                    txtIdBk.setText("BK" + tanggal.toString() + no);
                }
            }
        rs.close();
        stat.close();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan" + e);
        }
    }
 
   protected void reset(){
       tanggal();
       auto_id_bk();
       txtKodeCustomerBk.setText(null);
       txtNamaCustomerBk.setText(null);
       txtKeteranganBk.setText(null);
       txtKodeCustomerBk.requestFocus();
   }
   
   
   public void noTable(){
       int baris = tabmod.getRowCount();
       for(int a = 0; a < baris; a++){
           String nomor = String.valueOf(a+1);
           tabmod.setValueAt(nomor +".", a, 0);
       }
   }
   
   public void noTableCustomer(){
       int baris = tabmodCustomer.getRowCount();
       for(int a = 0; a < baris; a++){
           String nomor = String.valueOf(a+1);
           tabmodCustomer.setValueAt(nomor +".", a, 0);
       }
   }
  
   public void lebarKolom(){
       TableColumn kolom;
       tabelBk.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
       kolom = tabelBk.getColumnModel().getColumn(0);
       kolom.setPreferredWidth(20);
       kolom = tabelBk.getColumnModel().getColumn(1);
       kolom.setPreferredWidth(30);
       kolom = tabelBk.getColumnModel().getColumn(2);
       kolom.setPreferredWidth(100);
       kolom = tabelBk.getColumnModel().getColumn(3);
       kolom.setPreferredWidth(150);
       kolom = tabelBk.getColumnModel().getColumn(4);
       kolom.setPreferredWidth(100);
       kolom = tabelBk.getColumnModel().getColumn(5);
       kolom.setPreferredWidth(50);
       
   }
   
   public void lebarKolom1(){
       TableColumn kolom;
       tabelCustomer.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
       kolom = tabelCustomer.getColumnModel().getColumn(0);
       kolom.setPreferredWidth(40);
       kolom = tabelCustomer.getColumnModel().getColumn(1);
       kolom.setPreferredWidth(50);
       kolom = tabelCustomer.getColumnModel().getColumn(2);
       kolom.setPreferredWidth(100);
       kolom = tabelCustomer.getColumnModel().getColumn(3);
       kolom.setPreferredWidth(100);
       
   }
   
   public void dataTable(){
       Object[] Baris = {"No","Tanggal","ID BK","Kode Customer","Nama Customer","Keterngan"};
       tabmod = new DefaultTableModel(null, Baris);
       tabelBk.setModel(tabmod);
       String sql = "SELECT * FROM tb_bk JOIN tb_customer ON tb_customer.kode_customer = tb_bk.kode_customer "
               + " order by tb_bk.id_bk asc";
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String tanggal = hasil.getString("tanggal");
               String id_bk = hasil.getString("id_bk");
               String kode_customer = hasil.getString("id_bk");
               String nama_customer = hasil.getString("nama_customer");
               String keterangan = hasil.getString("keterangan");
               String [] data = {"",tanggal,id_bk,kode_customer,nama_customer,keterangan};
               tabmod.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void dataTable1(){
       Object[] Baris = {"No","ID Customer","Kode Customer","Nama Customer"};
       tabmodCustomer = new DefaultTableModel(null, Baris);
       tabelCustomer.setModel(tabmodCustomer);
       String sql = "select * from tb_customer order by id_customer asc";
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String id_customer = hasil.getString("id_customer");
               String kode_customer = hasil.getString("kode_customer");
               String nama_customer = hasil.getString("nama_customer");
               String [] data = {"",id_customer,kode_customer,nama_customer};
               tabmodCustomer.addRow(data);
               noTableCustomer();
               lebarKolom1();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void pencarian (String sql){
       Object[] Baris = {"No","Tanggal","ID BK","Kode Customer","Nama Customer","Keterngan"};
       tabmod = new DefaultTableModel(null, Baris);
       tabelBk.setModel(tabmod);
       
       int baris = tabelBk.getRowCount();
       for (int i = 0; 1 < baris; i++){
           tabmod.removeRow(i);
       }
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String tanggal = hasil.getString("tanggal");
               String id_bk = hasil.getString("id_bk");
               String kode_customer = hasil.getString("kode_customer");
               String nama_customer = hasil.getString("nama_customer");
               String keterangan = hasil.getString("keterangan");
               String [] data = {"",tanggal,id_bk,kode_customer,nama_customer,keterangan};
               tabmod.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void pencarian1 (String sql1){
      Object[] Baris = {"No","ID","Kode Customer","Supplier"};
       tabmodCustomer = new DefaultTableModel(null, Baris);
       tabelCustomer.setModel(tabmodCustomer);
       
       int baris = tabelCustomer.getRowCount();
       for (int i = 0; 1 < baris; i++){
           tabmodCustomer.removeRow(i);
       }
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql1);
           while (hasil.next()){
               String id_supplier = hasil.getString("id_supplier");
               String kode_customer = hasil.getString("kode_customer");
               String nama_customer = hasil.getString("nama_customer");
               String [] data = {"",id_supplier,kode_customer,nama_customer};
               tabmodCustomer.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   
    public transaksi_bk(){
        initComponents();
        tanggal();
        auto_id_bk();
        dataTable();
        lebarKolom();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lisCustomer = new javax.swing.JDialog();
        txtCariCustomer = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelCustomer = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKodeCustomerBk = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtNamaCustomerBk = new javax.swing.JTextField();
        txtIdBk = new javax.swing.JTextField();
        btnKodeCustomer = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtKeteranganBk = new javax.swing.JTextArea();
        dateBk = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBk = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        lisCustomer.setTitle("List Supplier");
        lisCustomer.setModalityType(null);
        lisCustomer.setSize(new java.awt.Dimension(420, 400));

        txtCariCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariCustomerKeyTyped(evt);
            }
        });

        tabelCustomer.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelCustomerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelCustomer);

        jLabel5.setBackground(new java.awt.Color(102, 204, 255));
        jLabel5.setText(" Cari Customer");
        jLabel5.setOpaque(true);

        javax.swing.GroupLayout lisCustomerLayout = new javax.swing.GroupLayout(lisCustomer.getContentPane());
        lisCustomer.getContentPane().setLayout(lisCustomerLayout);
        lisCustomerLayout.setHorizontalGroup(
            lisCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lisCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lisCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCariCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lisCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lisCustomerLayout.setVerticalGroup(
            lisCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lisCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lisCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCariCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Barang Keluar");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Tanggal");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID BK");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Kode Customer");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nama Customer");

        txtKodeCustomerBk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKodeCustomerBk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeCustomerBkActionPerformed(evt);
            }
        });
        txtKodeCustomerBk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeCustomerBkKeyPressed(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(153, 153, 153));
        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(153, 153, 153));
        btnUbah.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnUbah.setText("UABAH");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(153, 153, 153));
        btnReset.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(153, 153, 153));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(153, 153, 153));
        btnHapus.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtNamaCustomerBk.setEditable(false);
        txtNamaCustomerBk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtIdBk.setEditable(false);
        txtIdBk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnKodeCustomer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKodeCustomer.setText("...");
        btnKodeCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKodeCustomerActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Keterangan");

        txtKeteranganBk.setColumns(20);
        txtKeteranganBk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKeteranganBk.setRows(5);
        jScrollPane3.setViewportView(txtKeteranganBk);

        dateBk.setDateFormatString("dd-MM-yyyy");
        dateBk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(153, 153, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton1.setText("CETAK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtKodeCustomerBk, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnKodeCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateBk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtIdBk)
                            .addComponent(txtNamaCustomerBk)
                            .addComponent(jScrollPane3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(dateBk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdBk, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKodeCustomerBk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKodeCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaCustomerBk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHapus))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnCancel)
                    .addComponent(jButton1))
                .addGap(200, 200, 200))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabelBk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelBk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelBk.setRowHeight(30);
        tabelBk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBkMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBk);

        txtCari.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Pencarian");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void tabelBkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBkMouseClicked
        int baris = tabelBk.getSelectedRow();
        String a = tabmod.getValueAt(baris, 0).toString();
        String b = tabmod.getValueAt(baris, 1).toString();
        String c = tabmod.getValueAt(baris, 2).toString();
        String d = tabmod.getValueAt(baris, 3).toString();
        String e = tabmod.getValueAt(baris, 4).toString();
        String f = tabmod.getValueAt(baris, 5).toString();
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
Date dateValue = null;
try {
    dateValue = date.parse((String)tabelBk.getValueAt(baris, 1));
} catch (ParseException ex) {
    Logger.getLogger(transaksi_bk.class.getName()).log(Level.SEVERE, null, ex);
}     
        dateBk.setDate(dateValue);
        txtIdBk.setText(c);
        txtKodeCustomerBk.setText(d);
        txtNamaCustomerBk.setText(e);
        txtKeteranganBk.setText(f);
    }//GEN-LAST:event_tabelBkMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (txtIdBk.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Tidak Boleh Kosong");
        } else if (txtKodeCustomerBk.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Nama Tidak Boleh Kosong");
        } else{
            String sql = "insert into tb_bk (tanggal,id_bk,kode_customer,keterangan) values(?,?,?,?)";
            String tampilan = "yyyy-MM-dd";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tanggal = String.valueOf(fm.format(dateBk.getDate()));
            
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, tanggal);
                stat.setString(2, txtIdBk.getText());
                stat.setString(3, txtKodeCustomerBk.getText());
                stat.setString(4, txtKeteranganBk.getText());
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                dataTable();
                reset();
                txtKodeCustomerBk.requestFocus();
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data","Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
        if (ok == 0){
            String sql = "delete from tb_bk where id_bk ='"+ txtIdBk.getText() +"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                dataTable();
                reset();
                txtKodeCustomerBk.requestFocus();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus! " + e);
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        String sql = "update tb_bk set tanggal=?, id_bk=?,kode_customer=?,keterangan=? where id_bk ='"+ txtIdBk.getText() +"'";
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal = String.valueOf(fm.format(dateBk.getDate()));
        try{
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tanggal);
            stat.setString(2, txtIdBk.getText());
            stat.setString(3, txtKodeCustomerBk.getText());
            stat.setString(4, txtKeteranganBk.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!" );
            dataTable();
            reset();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan! " + e);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        String sql = "Select * from tb_bk join tb_customer on tb_customer.kode_customer = tb_bk.kode_customer "
                + " where tb_bk.id_bk like'%"+ txtCari.getText() +"%' or"
                + " tb_bk.kode_customer like'%"+ txtCari.getText() +"%' or "
                + " tb_customer.nama_customer like '%"+ txtCari.getText() +"%'or"
                + " tb_bk.keterangan like '%"+ txtCari.getText()+"%'";
        pencarian(sql);
        lebarKolom();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtKodeCustomerBkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeCustomerBkKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtNamaCustomerBk.requestFocus();
         }
    }//GEN-LAST:event_txtKodeCustomerBkKeyPressed

    private void tabelCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelCustomerMouseClicked
       int baris = tabelCustomer.getSelectedRow();
        String a = tabmodCustomer.getValueAt(baris, 0).toString();
        String b = tabmodCustomer.getValueAt(baris, 1).toString();
        String c = tabmodCustomer.getValueAt(baris, 2).toString();
        String d = tabmodCustomer.getValueAt(baris, 3).toString();
        
        txtKodeCustomerBk.setText(c);
        txtNamaCustomerBk.setText(d);
        lisCustomer.setVisible(false);
    }//GEN-LAST:event_tabelCustomerMouseClicked

    private void btnKodeCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKodeCustomerActionPerformed
      dataTable1();
      lebarKolom1();
      lisCustomer.setVisible(true);
      lisCustomer.setLocationRelativeTo(this);
    }//GEN-LAST:event_btnKodeCustomerActionPerformed

    private void txtCariCustomerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariCustomerKeyTyped
        String sql1 = "Select * from tb_customer "
                + " where kode_customer like'%"+ txtCari.getText() +"%' or"
                + " nama_customer like'%"+ txtCari.getText() +"%'";
        pencarian1(sql1);
        lebarKolom1();
    }//GEN-LAST:event_txtCariCustomerKeyTyped

    private void txtKodeCustomerBkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeCustomerBkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeCustomerBkActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            File namaFile = new File("src/laporan/laporan_barang_keluar.jasper");
            JasperPrint jp = JasperFillManager.fillReport(namaFile.getPath(), null, conn);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(transaksi_bk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_bk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_bk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_bk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new transaksi_bk().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKodeCustomer;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private com.toedter.calendar.JDateChooser dateBk;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JDialog lisCustomer;
    private javax.swing.JTable tabelBk;
    private javax.swing.JTable tabelCustomer;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtCariCustomer;
    private javax.swing.JTextField txtIdBk;
    private javax.swing.JTextArea txtKeteranganBk;
    private javax.swing.JTextField txtKodeCustomerBk;
    private javax.swing.JTextField txtNamaCustomerBk;
    // End of variables declaration//GEN-END:variables
}
