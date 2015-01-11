package ui;

import java.awt.*;

import javax.swing.*;

import system.*;
import ui.listener.CustActionListener;

public class WindowLogin extends JFrame {

	final private Core core;

	private JButton btnLogin;
	private JTextField txUsr;
	private JPasswordField txPsw;
	private JLabel lblUsr, lblPsw;

	private Container container;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public WindowLogin(Core core) {
		super("Login");
		getContentPane().setBackground(new Color(51, 255, 255));
		setBackground(Color.GRAY);
		this.core = core;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(402, 279);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setResizable(false);
		JLabel labelHeader = new JLabel("<HTML><H1>Toko Jamu Herbal</H1></HTML>");
		labelHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		labelHeader.setForeground(Color.DARK_GRAY);
		labelHeader.setBounds(103,13,218,30);
		
		container = this.getContentPane();
		container.setLayout(null);
		//container.setBackground(Color.WHITE);
		btnLogin = new JButton("<HTML><H3>Login</H3></HTML>");
		btnLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		txUsr = new JTextField(15);
		txPsw = new JPasswordField(15);
		lblUsr = new JLabel("<HTML><H3>Username</H3></HTML>");
		lblPsw = new JLabel("<HTML><H3>Password</H3></HTML>");

		lblUsr.setHorizontalAlignment(JLabel.RIGHT);
		lblPsw.setHorizontalAlignment(JLabel.RIGHT);

		lblUsr.setBounds(22, 84, 70, 20);
		txUsr.setBounds(154, 79, 180, 25);
		lblPsw.setBounds(22, 131, 70, 20);
		txPsw.setBounds(154, 130, 180, 25);
		btnLogin.setBounds(154, 201, 180, 30);

		btnLogin.addActionListener(new CustActionListener(core, this, btnLogin));
		container.add(labelHeader);
		container.add(lblUsr);
		container.add(txUsr);
		container.add(lblPsw);
		container.add(txPsw);
		container.add(btnLogin);
	}

	public String getUser() {
		return txUsr.getText();
	}

	public String getPass() {
		return txPsw.getText();
	}
}
