package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import object.Barang;
import object.DetilTransaksi;
import object.Transaksi;
import object.User;

import system.*;
import ui.listener.CustActionListener;
import ui.listener.CustKeyListener;
import java.awt.Font;

public class WindowFormTransaksi extends JFrame {

	final int TGL = 0, KASIR = 1, BARANG = 2, HARGA = 3, JUMLAH = 4;

	private Core core;

	private User user;
	private Transaksi t;

	private JPanel panLeft, panRight, panTable, panGrand;
	private JTextField tfTgl, tfKasir, tfHarga, tfJumlah;
	private JLabel l[] = new JLabel[5];
	private JComboBox cb;
	private JButton btnTambahBarang, btnTambahTransaksi, btnDelete;
	private JTable tbl;

	private DefaultTableModel model;

	private Container container;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Vector<String> nmBarang = new Vector<String>();
	private Vector<Barang> barang = new Vector<Barang>();

	public WindowFormTransaksi(Core core) {
		super("Formulir Transaksi - " + core.getDateAsString());
		setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		setTitle("Transaksi");
		this.core = core;
		this.user = core.getLoggedInUser();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(720, 396);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		getContentPane().setLayout(null);
		container = this.getContentPane();
		container.setBackground(Color.WHITE);
		setDefaultCloseOperation(0);
		model = new DefaultTableModel();
		model.addColumn("Nama Barang");
		model.addColumn("Jumlah Barang");
		model.addColumn("Total Harga");
		tbl = new JTable(model);
		Operator.disableTableEdit(tbl);
		ResultSet rs = Operator.getListBarang(core.getConnection());
		nmBarang.removeAllElements();
		barang.removeAllElements();
		try {
			while (rs.next()) {
				barang.add(new Barang(rs.getString(1), rs.getString(2),rs.getString(3), rs.getInt(4), rs.getInt(5)));
				if (barang.lastElement().getStok() > 0)
					nmBarang.add(barang.lastElement().getNama());
				else
					barang.removeElement(barang.lastElement());
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		cb = new JComboBox(nmBarang);
		tfTgl = new JTextField(core.getDateAsString());
		tfKasir = new JTextField(user.getUsername());
		tfJumlah = new JTextField();
		tfHarga = new JTextField();
		fillFormByIndex(cb.getSelectedIndex());

		panTable = new JPanel(new BorderLayout());
		panTable.setBounds(275, 0, 440, 300);
		panTable.setBackground(Color.WHITE);
		panGrand = new JPanel(null);
		panGrand.setBounds(275, 297, 440, 39);
		panLeft = new JPanel(null);
		panLeft.setBounds(0, 0, 275, 336);

		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);

		JMenu menuUser = new JMenu(
				"Kasir");
		JMenuItem miLogOut = new JMenuItem("Keluar");
		miLogOut.addActionListener(new CustActionListener(core, this, miLogOut,
				CustActionListener.LOGOUT));

		
		JMenu menuBarang = new JMenu("Barang");
		JMenuItem miBarangData = new JMenuItem("Data Barang");
		miBarangData.addActionListener(new CustActionListener(core, this,
				miBarangData, CustActionListener.SHOW_DATA_BARANG));
		
		menu.add(menuUser);
		menuUser.add(miLogOut);

		
		menu.add(menuBarang);
		menuBarang.add(miBarangData);

		l[TGL] = new JLabel("Tanggal");
		l[KASIR] = new JLabel("Nama Kasir");
		l[BARANG] = new JLabel("Nama Barang ");
		l[HARGA] = new JLabel("Harga Rp.");
		l[JUMLAH] = new JLabel("Jumlah Barang");

		tfTgl.setEnabled(false);
		tfKasir.setEnabled(false);
		tfHarga.setEnabled(false);
		tfTgl.setBounds(95, 10, 170, 20);
		tfKasir.setBounds(95, 35, 170, 20);
		cb.setBounds(95, 60, 170, 20);
		tfHarga.setBounds(95, 85, 170, 20);
		tfJumlah.setBounds(95, 110, 170, 20);

		l[TGL].setBounds(10, 10, 80, 20);
		l[KASIR].setBounds(10, 35, 80, 20);
		l[BARANG].setBounds(10, 60, 80, 20);
		l[HARGA].setBounds(10, 85, 80, 20);
		l[JUMLAH].setBounds(10, 110, 80, 20);

		btnTambahBarang = new JButton("Tambah Barang");
		btnTambahBarang.setBackground(Color.PINK);
		btnTambahBarang.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		btnTambahBarang.setBounds(105, 155, 140, 20);
		btnTambahBarang.addActionListener(new CustActionListener(this,
				btnTambahBarang));
		btnTambahTransaksi = new JButton("Selesai & Cetak");
		btnTambahTransaksi.setBackground(Color.PINK);
		btnTambahTransaksi.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		btnTambahTransaksi.setBounds(38, 13, 135, 20);
		btnTambahTransaksi.addActionListener(new CustActionListener(core, this,
				btnTambahTransaksi));
		tfJumlah.addKeyListener(new CustKeyListener(this, tfJumlah,
				CustKeyListener.NUMBER_ONLY));
		tfJumlah.addKeyListener(new CustKeyListener(this, tfJumlah,
				CustKeyListener.ON_STOCK));
		cb.addActionListener(new CustActionListener(this, cb));
		panLeft.add(cb);
		panLeft.add(tfTgl);
		panLeft.add(tfKasir);
		panLeft.add(tfHarga);
		panLeft.add(tfJumlah);
		for (int i = 0; i < l.length; i++) {
			l[i].setHorizontalAlignment(JLabel.RIGHT);
			panLeft.add(l[i]);
		}
		panTable.add((JTableHeader) tbl.getTableHeader(), BorderLayout.NORTH);
		panTable.add(new JScrollPane(tbl), BorderLayout.CENTER);
		panGrand.add(btnTambahTransaksi);
		panLeft.add(btnTambahBarang);

		container.add(panLeft);
		container.add(panTable);
		container.add(panGrand);
		
		final JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		btnDelete.setBackground(Color.PINK);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetForm();
				btnTambahTransaksi.setEnabled(false);
				btnDelete.setEnabled(false);
			}
		});
		btnDelete.setBounds(267, 11, 97, 25);
		panGrand.add(btnDelete);

		resetForm();
	}

	public void fillFormByIndex(int index) {
		tfJumlah.setText("1");
		tfHarga.setText(barang.get(index).getHarga() * Integer.parseInt(tfJumlah.getText()) + "");
	}

	public void resetForm() {
		int row = tbl.getRowCount() - 1;
		for (int i = row; i >= 0; i--)
			((DefaultTableModel) tbl.getModel()).removeRow(i);
		t = new Transaksi(core.getDate(), user);
	}

	public void addBarangToTable(DetilTransaksi dt) {
		for (int i = 0; i < tbl.getRowCount(); i++) {
			// test apakah sudah ada
		}
		model.addRow(new String[] { dt.getProduk().getNama(),
				dt.getQuantity() + "",
				dt.getProduk().getHarga() * dt.getQuantity() + "" });
		t.addDetilTransaksi(dt);
	}

	public Vector<Barang> getListBarang() {
		return barang;
	}

	public Barang getSelectedBarang() {
		return barang.get(cb.getSelectedIndex());
	}

	public int getQtyBarang() {
		return Integer.parseInt(tfJumlah.getText());
	}

	public Transaksi getTransaksi() {
		return t;
	}

	public Vector<DetilTransaksi> getDetilTransaksi() {
		return t.getDetilTransaksi();
	}

	public void submitToDB() {
		if (Operator.tambahTransaksi(getTransaksi(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data telah berhasil ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Maaf, Terjadi kesalahan!");
		}
		if (JOptionPane.showConfirmDialog(this, "Cetak transaksi?", "",
				JOptionPane.YES_NO_OPTION) == 0) {
			core.printReport(t);
		}
		resetForm();
	}
}
