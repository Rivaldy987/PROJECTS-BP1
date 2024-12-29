
package tampilan;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import koneksi.koneksi;

public class data_barang extends javax.swing.JFrame {

    public final Connection conn = new koneksi().connect();
    private DefaultTableModel tabmod;
    private DefaultTableModel tabmod1;
    
   protected void reset(){
       txtID.setText(null);
       txtKodeBarang.setText(null);
       txtNamaBarang.setText(null);
       txtKodeKategori.setText(null);
       txtJumlahBarang.setText(null);
       txtKodeBarang.requestFocus();
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
       tabelBarang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
       kolom = tabelBarang.getColumnModel().getColumn(0);
       kolom.setPreferredWidth(20);
       kolom = tabelBarang.getColumnModel().getColumn(1);
       kolom.setPreferredWidth(30);
       kolom = tabelBarang.getColumnModel().getColumn(2);
       kolom.setPreferredWidth(100);
       kolom = tabelBarang.getColumnModel().getColumn(3);
       kolom.setPreferredWidth(150);
       kolom = tabelBarang.getColumnModel().getColumn(4);
       kolom.setPreferredWidth(100);
       kolom = tabelBarang.getColumnModel().getColumn(5);
       kolom.setPreferredWidth(50);
       
   }
   
   public void lebarKolom1(){
       TableColumn kolom;
       tabelKategori.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
       kolom = tabelKategori.getColumnModel().getColumn(0);
       kolom.setPreferredWidth(40);
       kolom = tabelKategori.getColumnModel().getColumn(1);
       kolom.setPreferredWidth(50);
       kolom = tabelKategori.getColumnModel().getColumn(2);
       kolom.setPreferredWidth(100);
       kolom = tabelKategori.getColumnModel().getColumn(3);
       kolom.setPreferredWidth(100);
       
   }
   
   public void dataTable(){
       Object[] Baris = {"No","ID","Kode Barang","Nama Barang","Kode Kategori","Jumlah"};
       tabmod = new DefaultTableModel(null, Baris);
       tabelBarang.setModel(tabmod);
       String sql = "select * from tb_barang order by id_barang asc";
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String id_barang = hasil.getString("id_barang");
               String kode_barang = hasil.getString("kode_barang");
               String nama_barang = hasil.getString("nama_barang");
               String kode_kategori = hasil.getString("kode_kategori");
               String jumlah_barang = hasil.getString("jumlah_barang");
               String [] data = {"",id_barang,kode_barang,nama_barang,kode_kategori,jumlah_barang};
               tabmod.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void dataTable1(){
       Object[] Baris = {"No","ID","Kode Kategori","Nama Kategori"};
       tabmod1 = new DefaultTableModel(null, Baris);
       tabelKategori.setModel(tabmod1);
       String sql = "select * from tb_kategori order by id_kategori asc";
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String id_kategori = hasil.getString("id_kategori");
               String kode_kategori = hasil.getString("kode_kategori");
               String nama_barang = hasil.getString("nama_barang");
               String [] data = {"",id_kategori,kode_kategori,nama_barang};
               tabmod1.addRow(data);
               noTable1();
               lebarKolom1();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void pencarian (String sql){
       Object[] Baris = {"No","ID","Kode Barang","Nama Barang","Kategori","Jumlah"};
       tabmod = new DefaultTableModel(null, Baris);
       tabelBarang.setModel(tabmod);
       
       int baris = tabelBarang.getRowCount();
       for (int i = 0; 1 < baris; i++){
           tabmod.removeRow(i);
       }
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           while (hasil.next()){
               String id_barang = hasil.getString("id_barang");
               String kode_barang = hasil.getString("kode_barang");
               String nama_barang = hasil.getString("nama_barang");
               String kode_kategori = hasil.getString("kode_kategori");
               String jumlah_barang = hasil.getString("jumlah_barang");
               String [] data = {"",id_barang,kode_barang,nama_barang,kode_kategori,jumlah_barang};
               tabmod.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
   public void pencarian1 (String sql1){
       Object[] Baris = {"No","ID","Kode Kategori","Nama Kategori"};
       tabmod1 = new DefaultTableModel(null, Baris);
       tabelKategori.setModel(tabmod1);
       
       int baris = tabelKategori.getRowCount();
       for (int i = 0; 1 < baris; i++){
           tabmod1.removeRow(i);
       }
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql1);
           while (hasil.next()){
               String id_kategori = hasil.getString("id_kategori");
               String kode_kategori = hasil.getString("kode_kategori");
               String nama_barang = hasil.getString("nama_barang");
               String [] data = {"",id_kategori,kode_kategori,nama_barang};
               tabmod1.addRow(data);
               noTable();
               lebarKolom();
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak bisa Ditampilkan");
       }
   }
   
    public data_barang() {
        initComponents();
        dataTable();
        lebarKolom();
        txtID.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lisKategori = new javax.swing.JDialog();
        txtCariKategori = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelKategori = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKodeBarang = new javax.swing.JTextField();
        txtKodeKategori = new javax.swing.JTextField();
        txtID = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtJumlahBarang = new javax.swing.JTextField();
        txtNamaBarang = new javax.swing.JTextField();
        btnKategori = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBarang = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        lisKategori.setModalityType(null);
        lisKategori.setSize(new java.awt.Dimension(420, 400));

        txtCariKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKategoriKeyTyped(evt);
            }
        });

        tabelKategori.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKategoriMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelKategori);

        jLabel5.setBackground(new java.awt.Color(102, 204, 255));
        jLabel5.setText("  Cari Kategori");
        jLabel5.setOpaque(true);

        javax.swing.GroupLayout lisKategoriLayout = new javax.swing.GroupLayout(lisKategori.getContentPane());
        lisKategori.getContentPane().setLayout(lisKategoriLayout);
        lisKategoriLayout.setHorizontalGroup(
            lisKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lisKategoriLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lisKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCariKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lisKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lisKategoriLayout.setVerticalGroup(
            lisKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lisKategoriLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lisKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCariKategori, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Barang");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Kode Barang");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nama Barang");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Kode Kategori");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Jumlah Barang");

        txtKodeBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKodeBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeBarangKeyPressed(evt);
            }
        });

        txtKodeKategori.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKodeKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeKategoriKeyPressed(evt);
            }
        });

        txtID.setForeground(new java.awt.Color(240, 240, 240));

        btnSimpan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUbah.setText("UABAH");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtJumlahBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtNamaBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnKategori.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKategori.setText("...");
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(114, 114, 114)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKodeBarang)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus))
                            .addComponent(txtJumlahBarang)
                            .addComponent(txtNamaBarang)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtKodeKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(txtKodeBarang))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtKodeKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtJumlahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnCancel))
                .addGap(247, 247, 247))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabelBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelBarang.setRowHeight(30);
        tabelBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBarang);

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
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 378, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void tabelBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBarangMouseClicked
        int baris = tabelBarang.getSelectedRow();
        String a = tabmod.getValueAt(baris, 0).toString();
        String b = tabmod.getValueAt(baris, 1).toString();
        String c = tabmod.getValueAt(baris, 2).toString();
        String d = tabmod.getValueAt(baris, 3).toString();
        String e = tabmod.getValueAt(baris, 4).toString();
        String f = tabmod.getValueAt(baris, 5).toString();
        
        txtID.setText(b);
        txtKodeBarang.setText(c);
        txtNamaBarang.setText(d);
        txtKodeKategori.setText(e);
        txtJumlahBarang.setText(f);
    }//GEN-LAST:event_tabelBarangMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (txtKodeBarang.getText().equals("")){
            JOptionPane.showConfirmDialog(null, "Kode Barang Tida boleh kosong!");
        } else if (txtNamaBarang.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Nama Barang Tidak Boleh Kosong");
        } else if (txtKodeKategori.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Kategori Tidak Boleh Kosong");
        } else{
            String sql = "insert into tb_barang (kode_barang,nama_barang,kode_kategori,jumlah_barang) values(?,?,?,?)";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtKodeBarang.getText());
                stat.setString(2, txtNamaBarang.getText());
                stat.setString(3, txtKodeKategori.getText());
                stat.setString(4, txtJumlahBarang.getText());
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                dataTable();
                reset();
                txtKodeBarang.requestFocus();
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtKodeBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeBarangKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtNamaBarang.requestFocus();
        }
    }//GEN-LAST:event_txtKodeBarangKeyPressed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data","Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
        if (ok == 0){
            String sql = "delete from tb_barang where id_barang ='"+ txtID.getText() +"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                dataTable();
                reset();
                txtKodeBarang.requestFocus();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus! " + e);
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        String sql = "update tb_barang set kode_barang=?, nama_barang=?,kode_kategori=?,jumlah_barang=? where id_barang ='"+ txtID.getText() +"'";
        try{
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtKodeBarang.getText());
            stat.setString(2, txtNamaBarang.getText());
            stat.setString(3, txtKodeKategori.getText());
            stat.setString(4, txtJumlahBarang.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!" );
            dataTable();
            reset();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan! " + e);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        String sql = "Select * from tb_barang "
                + " where kode_barang like'%"+ txtCari.getText() +"%' or"
                + " nama_barang like'%"+ txtCari.getText() +"%' or "
                + " kode_kategori like '%"+ txtCari.getText() +"%'or"
                + " jumlah_barang like '%"+ txtCari.getText()+"%'";
        pencarian(sql);
        lebarKolom();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtKodeKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeKategoriKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtJumlahBarang.requestFocus();
         }
    }//GEN-LAST:event_txtKodeKategoriKeyPressed

    private void tabelKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKategoriMouseClicked
       int baris = tabelKategori.getSelectedRow();
        String a = tabmod1.getValueAt(baris, 0).toString();
        String b = tabmod1.getValueAt(baris, 1).toString();
        String c = tabmod1.getValueAt(baris, 2).toString();
        String d = tabmod1.getValueAt(baris, 3).toString();
        
        txtKodeKategori.setText(c);
    }//GEN-LAST:event_tabelKategoriMouseClicked

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
      dataTable1();
      lebarKolom1();
      lisKategori.setVisible(true);
      lisKategori.setLocationRelativeTo(this);
    }//GEN-LAST:event_btnKategoriActionPerformed

    private void txtCariKategoriKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKategoriKeyTyped
        String sql1 = "Select * from tb_kategori "
                + " where kode_kategori like'%"+ txtCari.getText() +"%' or"
                + " nama_barang like'%"+ txtCari.getText() +"%'";
        pencarian1(sql1);
        lebarKolom1();
    }//GEN-LAST:event_txtCariKategoriKeyTyped

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
            java.util.logging.Logger.getLogger(data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new data_barang().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKategori;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JDialog lisKategori;
    private javax.swing.JTable tabelBarang;
    private javax.swing.JTable tabelKategori;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtCariKategori;
    private javax.swing.JLabel txtID;
    private javax.swing.JTextField txtJumlahBarang;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtKodeKategori;
    private javax.swing.JTextField txtNamaBarang;
    // End of variables declaration//GEN-END:variables
}
