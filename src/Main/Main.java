package Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Graphics;
import Types.*;
import Operateurs.*;



public class Main extends JFrame {
	private static int WIDTH;
	private static int HEIGHT;
	private static Boolean running = true;

	private TableauDynamiqueND tab;

	public Main() {
		tab = new TableauDynamiqueND(new Coords(true, 2), 100, 100);
		WIDTH = 8*tab.getTaille()+16;
		HEIGHT = 8*tab.getTab()[0].getTaille()+66;
		setTitle("Jeu de la Vie!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Vaisseau glider
		tab.changeState(10,10);
		tab.changeState(11,11);
		tab.changeState(11,12);
		tab.changeState(10,12);
		tab.changeState(9,12);


		// Interface Graphique
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (int i = 0; i < tab.getTaille(); i++) {
					for (int j = 0; j < tab.getTab()[1].getTaille(); j++) {
						if (tab.getState(i, j)) {
							g.setColor(new Color(255,0,255));
							g.fillRect(j * 8, i * 8, 8, 8);
						} else {
							g.setColor(Color.WHITE);
							g.fillRect(j * 8, i * 8, 8, 8);
						}
						g.setColor(Color.BLACK);
						g.drawRect(j * 8, i * 8, 8, 8);
					}
				}
			}
		};


		// Bouton pause
		JButton button = new JButton("Pause");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (button.getText().equals("Pause")) {
					button.setText("Reprendre");
					running = false;
				} else {
					button.setText("Pause");
					running = true;
				}
			}
		});


		// Gestion de la souris
        panel.addMouseListener(new MouseAdapter() {				
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellSize = Math.min(panel.getWidth() / tab.getTaille(), panel.getHeight() / tab.getTab()[1].getTaille());
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

				tab.changeState(row, col);
                repaint();
            }
        });


		add(panel);
		add(button, "South");

		setVisible(true);

		runGameOfLife();
	}

	private void runGameOfLife() {
		while (true) {
			if (!running) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			TableauDynamiqueND tmp = tab.clone();

			for (int i = 0; i < tab.getTaille(); i++) {
				for (int j = 0; j < tab.getTab()[1].getTaille(); j++) {
					Operateur regle = new OU(new SI(new EQ(new COMPTER(new G8e(tab, i, j)), new CONST(3)), new CONST(1), new CONST(0)), new SI(new EQ(new COMPTER(new G8(tab, i, j)), new CONST(3)), new CONST(1), new CONST(0)));
					tmp.setState(regle.evaluer(), i, j);
				}
			}

			tab = tmp.clone();

			repaint();

			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}
