/*
 * READ Frame - Menampilkan semua data film dari database
 * Bagian READ dari CRUD - projectPemlan
 */
package view;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class readFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(readFrame.class.getName());

    private DefaultTableModel tableModel;

    // Constructor
    public readFrame() {
        initComponents();
        setupTable();
        loadData();
    }

    // Setup kolom JTable
    private void setupTable() {
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Judul Film", "Genre", "Rating", "Review"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblMovies.setModel(tableModel);

        tblMovies.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblMovies.getColumnModel().getColumn(1).setPreferredWidth(160);
        tblMovies.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblMovies.getColumnModel().getColumn(3).setPreferredWidth(60);
        tblMovies.getColumnModel().getColumn(4).setPreferredWidth(300);
    }

    // Load semua data dari database ke JTable
    private void loadData() {
        loadData("");
    }

    private void loadData(String keyword) {
        tableModel.setRowCount(0);

        String sql;
        if (keyword == null || keyword.trim().isEmpty()) {
            sql = "SELECT id, title, genre, rating, review FROM movies ORDER BY id ASC";
        } else {
            sql = "SELECT id, title, genre, rating, review FROM movies "
                + "WHERE title LIKE ? ORDER BY id ASC";
        }

        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            if (keyword != null && !keyword.trim().isEmpty()) {
                pst.setString(1, "%" + keyword.trim() + "%");
            }

            ResultSet rs = pst.executeQuery();

            int rowCount = 0;
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("rating"),
                    rs.getString("review")
                };
                tableModel.addRow(row);
                rowCount++;
            }

            lblJumlah.setText("Total: " + rowCount + " film");

            rs.close();
            pst.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal memuat data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("loadData error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1      = new javax.swing.JLabel();
        jLabel2      = new javax.swing.JLabel();
        txtCari      = new javax.swing.JTextField();
        btnCari      = new javax.swing.JButton();
        btnRefresh   = new javax.swing.JButton();
        btnDelete     = new javax.swing.JButton();
        btnTambah    = new javax.swing.JButton();
        btnUpdate    = new javax.swing.JButton();
        lblJumlah    = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovies    = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Movie Reviews - Daftar Film");

        // Header
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        jLabel1.setText("Daftar Film");

        // Label cari
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13));
        jLabel2.setText("Cari Judul:");

        // TextField cari
        txtCari.setFont(new java.awt.Font("Segoe UI", 0, 13));
        txtCari.setToolTipText("Ketik judul film lalu klik Cari");

        // Tombol Cari
        btnCari.setText("Cari");
        btnCari.setFont(new java.awt.Font("Segoe UI", 0, 13));
        btnCari.addActionListener(evt -> btnCariActionPerformed(evt));

        // Tombol Refresh
        btnRefresh.setText("Refresh");
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 13));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));

        // —-- Tombol Delete —--
        btnDelete.setText("Delete");
        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 13));
        btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));

        // Tombol Tambah Film
        btnTambah.setText("Tambah Film");
        btnTambah.setFont(new java.awt.Font("Segoe UI", 0, 13));
        btnTambah.addActionListener(evt -> {
            new moviesFrame().setVisible(true);
        });

        // Tombol Update Review
        btnUpdate.setText("Update Review");
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 13));
        btnUpdate.addActionListener(evt -> btnUpdateActionPerformed(evt));

        // Label jumlah
        lblJumlah.setFont(new java.awt.Font("Segoe UI", 0, 12));
        lblJumlah.setForeground(java.awt.Color.GRAY);
        lblJumlah.setText("Total: 0 film");

        // JTable
        tblMovies.setFont(new java.awt.Font("Segoe UI", 0, 13));
        tblMovies.setRowHeight(24);
        tblMovies.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblMovies);

        // Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    // Baris 1: judul halaman
                    .addComponent(jLabel1)
                    // Baris 2: cari + tombol-tombol
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(8)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8)
                        .addComponent(btnCari)
                        .addGap(8)
                        .addComponent(btnRefresh)
                        .addGap(8)
                        .addComponent(btnDelete) 
                        .addComponent(btnTambah)
                        .addGap(8)
                        .addComponent(btnUpdate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    // Baris 3: label jumlah
                    .addComponent(lblJumlah)
                    // Baris 4: tabel
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
                .addGap(18))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14)
                .addComponent(jLabel1)
                .addGap(14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari)
                    .addComponent(btnRefresh)
                    .addComponent(btnDelete) 
                    .addComponent(btnTambah)
                    .addComponent(btnUpdate))
                .addGap(8)
                .addComponent(lblJumlah)
                .addGap(8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    // Event Handlers
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {
        String keyword = txtCari.getText();
        loadData(keyword);
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        txtCari.setText("");
        loadData();
        JOptionPane.showMessageDialog(this, "Data berhasil direfresh!", "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        // Cek apakah ada baris yang dipilih di tabel
        int barisDipilih = tblMovies.getSelectedRow();
        if (barisDipilih == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih film dulu di tabel!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil data film yang dipilih
        int id = (int) tableModel.getValueAt(barisDipilih, 0);
        String judul = (String) tableModel.getValueAt(barisDipilih, 1);
        String reviewLama = (String) tableModel.getValueAt(barisDipilih, 4);

        // Muncul dialog input review baru
        String reviewBaru = (String) JOptionPane.showInputDialog(
            this,
            "Update review untuk film: " + judul,
            "Update Review",
            JOptionPane.PLAIN_MESSAGE,
            null, null,
            reviewLama // isi default dengan review lama
        );

        // Kalau user klik Cancel
        if (reviewBaru == null) return;

        // Update ke database
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "UPDATE movies SET review = ? WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, reviewBaru);
            pst.setInt(2, id);
            pst.executeUpdate();
            pst.close();

            JOptionPane.showMessageDialog(this,
                "Review film \"" + judul + "\" berhasil diupdate!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Refresh tabel otomatis
            loadData();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal update: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("btnUpdate error: " + e.getMessage());
        }
    }
    
         private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {

         int selectedRow = tblMovies.getSelectedRow();

         if (selectedRow == -1) {
             JOptionPane.showMessageDialog(this,
                 "Pilih film yang ingin dihapus!");
             return;
         }

         int confirm = JOptionPane.showConfirmDialog(
             this,
             "Yakin ingin menghapus film ini?",
             "Konfirmasi Delete",
             JOptionPane.YES_NO_OPTION
         );

         if (confirm == JOptionPane.YES_OPTION) {

             try {
                 int id = (int) tableModel.getValueAt(selectedRow, 0);

                 Connection conn = Koneksi.getConnection();

                 String sql = "DELETE FROM movies WHERE id = ?";

                 PreparedStatement pst = conn.prepareStatement(sql);

                 pst.setInt(1, id);

                 pst.executeUpdate();

                 JOptionPane.showMessageDialog(this,
                     "Film berhasil dihapus!");

                 loadData();

             } catch (SQLException e) {

                 JOptionPane.showMessageDialog(this,
                     "Gagal menghapus data: " + e.getMessage());
             }
         }
     }

    // Main
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new readFrame().setVisible(true));
    }

    // Variable declaration
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblJumlah;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMovies;
    private javax.swing.JTextField txtCari;
}
