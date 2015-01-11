package object;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Transaksi {
	private String noTrans;
	private Vector<DetilTransaksi> detilTransaksi = new Vector<DetilTransaksi>();
	private Date tgl;
	private User usr;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

	
	public Transaksi(String noTrans, Vector<DetilTransaksi> detilTransaksi, Date tgl,
			User usr) {
		this.noTrans = noTrans;
		this.detilTransaksi = detilTransaksi;
		this.tgl = tgl;
		this.usr = usr;
	}

	
	public Transaksi(Date tgl, User usr) {
		this.tgl = tgl;
		this.usr = usr;
	}

	public Transaksi(String noTrans) {
		this.noTrans = noTrans;
	}

	public String getId() {
		return noTrans;
	}

	public Vector<DetilTransaksi> getDetilTransaksi() {
		return detilTransaksi;
	}

	public Date getTgl() {
		return tgl;
	}

	public String getTglAsString() {
		return formatter.format(tgl.getTime());
	}

	public User getUser() {
		return usr;
	}

	public int getTotalItem() {
		int total = 0;
		for (int i = 0; i < detilTransaksi.size(); i++) {
			total += detilTransaksi.get(i).getQuantity();
		}
		return total;
	}

	public int getTotalHrg() {
		int total = 0;
		for (int i = 0; i < detilTransaksi.size(); i++) {
			total += detilTransaksi.get(i).getProduk().getHarga()
					* detilTransaksi.get(i).getQuantity();
		}
		return total;
	}

	public void addDetilTransaksi(DetilTransaksi dt) {
		detilTransaksi.add(dt);
	}
}
