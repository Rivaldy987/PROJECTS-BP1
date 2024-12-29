package tampilan;

import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class transaksi_bm extends javax.swing.JFrame {

    public final Connection conn = new koneksi().connect();
    private DefaultTableModel tabmod;
    private DefaultTableModel tabmod1;
    
    public void tanggal(){
        Date tgl = new Date();
        dateBm.setDate(tgl);
    }
    
    public void auto_id_bm(){
        try{
            Connection con = new koneksi().connect();
            java.sql.Statement stat = con.createStatement();
            Date tanggal_update = new Date();
            String tampilan ="yyMM";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tanggal = String.valueOf(fm.format(dateBm.getDate()));
            String sql = "select max(right(id_bm,4)) as no from td_bm where id_bm like'%" + "BM" + tanggal.toString() +"%'";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()){
                if(rs.first() == false){
                    txtIdBm.setText("BM" + tanggal.toString() + "0001");
                }else{
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int no_id = no.length();
                    //jumlah nomor 0
                    for (int j = 0; j < 4 - no_id; j++){
                        no = "0"+ no;
                    }
                    txtIdBm.setText("BM" + tanggal.toString() + no);
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
       auto_id_bm();
       txtKodeSupplier.setText(null);
       txtNamaSupplier.setText(null);
       txtKeterangan.setText(null);
       txtKodeSupplier.requestFocus();
   }
   
   
   public void noTable(){
       int baris = tabmod.getRowCount();
       for(int a = 0; a < baris; a++){
           String nomor = String.valueOf(a+1);
           tabmod.setValueAt(nomor +".", a, 0);
       }
   }
   
   public void noTable1(){
       int baris = tabmod1.getRowCount();
       for(int a = 0; a < baris; a++){
           String nomor = String.valueOf(a+1);
           tabmod1.setValueAt(nomor +".", a, 0);
       }
   }
  
   public void lebarKolom(){
       TableColumn kolom;
       tabelBm.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
       kolom = tabelBm.getColumnModel().getColumn(0);
       kolom.setPreferredWidth(20);
       kolom = tabelBm.getColumnModel().getColumn(1);
       kolom.setPreferredWidth(30);
       kolom = tabelBm.getColumnModel().getColumn(2);
       kolom.setPreferredWidth(100);
       kolom = tabelBm.getColumnModel().getColumn(3);
       kolom.setPreferredWidth(150);
       kolom = tabelBm.getColumnModel().getColumn(4);
       kolom.setPreferredWidth(100);
       kolom = tabelBm.getColumnModel().getColumn(5);
       kolom.setPreferredWidth(50);
       
   }
   
   public void lebarKolom1(){
       TableColumn kolom;
       tabelSupplier.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
       kolom = tabelSupplier.getColumnModel().getColumn(0);
       kolom.setPreferredWidth(40);
       kolom = tabelSupplier.getColumnModel().getColumn(1);
       kolom.setPreferredWidth(50);
       kolom = tabelSupplier.getColumnModel().getColumn(2);
       kolom.setPreferredWidth(100);
       kolom = tabelSupplier.getColumnModel().getColumn(3);
       kolom.setPreferredWidth(100);
       
   }
   
   public void dataTable(){
       Object[] Baris = {"No","Tanggal","ID BM","Kode Supplier","Nama Supplier","Keterngan"};
       tabmod = new DefaultTableModel(null, Baris);
       tabelBm.setModel(tabmod);
       String sql = "SELECT * FROM td_bm JOIN tb_supplier ON tb_supplier.kode_supplier = td_bm.kode_supplier "
               + " order by td_bm.id_bm asc";
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String tanggal = hasil.getString("tanggal");
               String id_bm = hasil.getString("id_bm");
               String kode_supplier = hasil.getString("kode_supplier");
               String nama_supplier = hasil.getString("nama_supplier");
               String keterangan = hasil.getString("keterangan");
               String [] data = {"",tanggal,id_bm,kode_supplier,nama_supplier,keterangan};
               tabmod.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void dataTable1(){
       Object[] Baris = {"No","ID","Kode Supplier","Supplier"};
       tabmod1 = new DefaultTableModel(null, Baris);
       tabelSupplier.setModel(tabmod1);
       String sql = "select * from tb_supplier order by id_supplier asc";
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String id_supplier = hasil.getString("id_supplier");
               String kode_supplier = hasil.getString("kode_supplier");
               String nama_supplier = hasil.getString("nama_supplier");
               String [] data = {"",id_supplier,kode_supplier,nama_supplier};
               tabmod1.addRow(data);
               noTable1();
               lebarKolom1();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void pencarian (String sql){
       Object[] Baris = {"No","Tanggal","ID BM","Kode Supplier","Nama Supplier","Keterngan"};
       tabmod = new DefaultTableModel(null, Baris);
       tabelBm.setModel(tabmod);
       
       int baris = tabelBm.getRowCount();
       for (int i = 0; 1 < baris; i++){
           tabmod.removeRow(i);
       }
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String tanggal = hasil.getString("tanggal");
               String id_bm = hasil.getString("id_bm");
               String kode_supplier = hasil.getString("kode_supplier");
               String nama_supplier = hasil.getString("nama_supplier");
               String keterangan = hasil.getString("keterangan");
               String [] data = {"",tanggal,id_bm,kode_supplier,nama_supplier,keterangan};
               tabmod.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void pencarian1 (String sql1){
      Object[] Baris = {"No","ID","Kode Supplier","Supplier"};
       tabmod1 = new DefaultTableModel(null, Baris);
       tabelSupplier.setModel(tabmod1);
       
       int baris = tabelSupplier.getRowCount();
       for (int i = 0; 1 < baris; i++){
           tabmod1.removeRow(i);
       }
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql1);
           while (hasil.next()){
               String id_supplier = hasil.getString("id_supplier");
               String kode_supplier = hasil.getString("kode_supplier");
               String nama_supplier = hasil.getString("nama_supplier");
               String [] data = {"",id_supplier,kode_supplier,nama_supplier};
               tabmod1.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   
    public transaksi_bm(){
        initComponents();
        tanggal();
        auto_id_bm();
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

        lisSupplier = new javax.swing.JDialog();
        txtCariSupplier = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelSupplier = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKodeSupplier = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtNamaSupplier = new javax.swing.JTextField();
        txtIdBm = new javax.swing.JTextField();
        btnKategori = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        dateBm = new com.toedter.calendar.JDateChooser();
        btnCetak = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBm = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        lisSupplier.setTitle("List Supplier");
        lisSupplier.setModalityType(null);
        lisSupplier.setSize(new java.awt.Dimension(420, 400));

        txtCariSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariSupplierKeyTyped(evt);
            }
        });

        tabelSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSupplierMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelSupplier);

        jLabel5.setBackground(new java.awt.Color(102, 204, 255));
        jLabel5.setText(" Cari Supplier");
        jLabel5.setOpaque(true);

        javax.swing.GroupLayout lisSupplierLayout = new javax.swing.GroupLayout(lisSupplier.getContentPane());
        lisSupplier.getContentPane().setLayout(lisSupplierLayout);
        lisSupplierLayout.setHorizontalGroup(
            lisSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lisSupplierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lisSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCariSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lisSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lisSupplierLayout.setVerticalGroup(
            lisSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lisSupplierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lisSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCariSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transaksi");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Tanggal");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID BM");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Kode Supplier");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nama Supplier");

        txtKodeSupplier.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKodeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeSupplierActionPerformed(evt);
            }
        });
        txtKodeSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeSupplierKeyPressed(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(153, 153, 153));
        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(153, 153, 153));
        btnUbah.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUbah.setText("UABAH");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(153, 153, 153));
        btnReset.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(153, 153, 153));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(153, 153, 153));
        btnHapus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtNamaSupplier.setEditable(false);
        txtNamaSupplier.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtIdBm.setEditable(false);
        txtIdBm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnKategori.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKategori.setText("...");
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Keterangan");

        txtKeterangan.setColumns(20);
        txtKeterangan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKeterangan.setRows(5);
        jScrollPane3.setViewportView(txtKeterangan);

        dateBm.setDateFormatString("dd-MM-yyyy");
        dateBm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnCetak.setBackground(new java.awt.Color(153, 153, 153));
        btnCetak.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCetak.setText("CETAK");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
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
                                .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateBm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtIdBm)
                            .addComponent(txtNamaSupplier)
                            .addComponent(jScrollPane3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(dateBm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdBm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(179, 179, 179))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabelBm.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelBm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelBm.setRowHeight(30);
        tabelBm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBmMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBm);

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

    private void tabelBmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBmMouseClicked
        int baris = tabelBm.getSelectedRow();
        String a = tabmod.getValueAt(baris, 0).toString();
        String b = tabmod.getValueAt(baris, 1).toString();
        String c = tabmod.getValueAt(baris, 2).toString();
        String d = tabmod.getValueAt(baris, 3).toString();
        String e = tabmod.getValueAt(baris, 4).toString();
        String f = tabmod.getValueAt(baris, 5).toString();
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
Date dateValue = null;
try {
    dateValue = date.parse((String)tabelBm.getValueAt(baris, 1));
} catch (ParseException ex) {
    Logger.getLogger(transaksi_bm.class.getName()).log(Level.SEVERE, null, ex);
}     
        dateBm.setDate(dateValue);
        txtIdBm.setText(c);
        txtKodeSupplier.setText(d);
        txtNamaSupplier.setText(e);
        txtKeterangan.setText(f);
    }//GEN-LAST:event_tabelBmMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (txtIdBm.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Username Tidak Boleh Kosong");
        } else if (txtKodeSupplier.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Password Tidak Boleh Kosong");
        } else{
            String sql = "insert into td_bm (tanggal,id_bm,kode_supplier,keterangan) values(?,?,?,?)";
            String tampilan = "yyyy-MM-dd";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tanggal = String.valueOf(fm.format(dateBm.getDate()));
            
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, tanggal);
                stat.setString(2, txtIdBm.getText());
                stat.setString(3, txtKodeSupplier.getText());
                stat.setString(4, txtKeterangan.getText());
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                dataTable();
                reset();
                txtKodeSupplier.requestFocus();
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
            String sql = "delete from td_bm where id_bm ='"+ txtIdBm.getText() +"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                dataTable();
                reset();
                txtKodeSupplier.requestFocus();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus! " + e);
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        String sql = "update td_bm set tanggal=?, id_bm=?,kode_supplier=?,keterangan=? where id_bm ='"+ txtIdBm.getText() +"'";
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal = String.valueOf(fm.format(dateBm.getDate()));
        try{
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tanggal);
            stat.setString(2, txtIdBm.getText());
            stat.setString(3, txtKodeSupplier.getText());
            stat.setString(4, txtKeterangan.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!" );
            dataTable();
            reset();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan! " + e);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        String sql = "Select * from td_bm join tb_supplier on tb_supplier.kode_supplier = td_bm.kode_supplier "
                + " where td_bm.id_bm like'%"+ txtCari.getText() +"%' or"
                + " td_bm.kode_supplier like'%"+ txtCari.getText() +"%' or "
                + " tb_supplier.nama_supplier like '%"+ txtCari.getText() +"%'or"
                + " td_bm.keterangan like '%"+ txtCari.getText()+"%'";
        pencarian(sql);
        lebarKolom();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtKodeSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeSupplierKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtNamaSupplier.requestFocus();
         }
    }//GEN-LAST:event_txtKodeSupplierKeyPressed

    private void tabelSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSupplierMouseClicked
       int baris = tabelSupplier.getSelectedRow();
        String a = tabmod1.getValueAt(baris, 0).toString();
        String b = tabmod1.getValueAt(baris, 1).toString();
        String c = tabmod1.getValueAt(baris, 2).toString();
        String d = tabmod1.getValueAt(baris, 3).toString();
        
        txtKodeSupplier.setText(c);
        txtNamaSupplier.setText(d);
        lisSupplier.setVisible(false);
    }//GEN-LAST:event_tabelSupplierMouseClicked

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
      dataTable1();
      lebarKolom1();
      lisSupplier.setVisible(true);
      lisSupplier.setLocationRelativeTo(this);
    }//GEN-LAST:event_btnKategoriActionPerformed

    private void txtCariSupplierKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariSupplierKeyTyped
        String sql1 = "Select * from tb_supplier "
                + " where kode_supplier like'%"+ txtCari.getText() +"%' or"
                + " nama_supplier like'%"+ txtCari.getText() +"%'";
        pencarian1(sql1);
        lebarKolom1();
    }//GEN-LAST:event_txtCariSupplierKeyTyped

    private void txtKodeSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeSupplierActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        try {
            File namaFile = new File("src/laporan/laporan_barang_masuk.jasper");
            JasperPrint jp = JasperFillManager.fillReport(namaFile.getPath(), null, conn);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btnCetakActionPerformed

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
            java.util.logging.Logger.getLogger(transaksi_bm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_bm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_bm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_bm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new transaksi_bm().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKategori;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private com.toedter.calendar.JDateChooser dateBm;
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
    private javax.swing.JDialog lisSupplier;
    private javax.swing.JTable tabelBm;
    private javax.swing.JTable tabelSupplier;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtCariSupplier;
    private javax.swing.JTextField txtIdBm;
    private javax.swing.JTextArea txtKeterangan;
    private javax.swing.JTextField txtKodeSupplier;
    private javax.swing.JTextField txtNamaSupplier;
    // End of variables declaration//GEN-END:variables
}
