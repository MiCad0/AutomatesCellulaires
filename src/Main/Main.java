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
	private static final int WIDTH = 816;
	private static final int HEIGHT = 866;
	private static Boolean running = true;

	private TableauDynamiqueND tab;

	public Main() {
		tab = new TableauDynamiqueND(new Coords(true, 2), 100, 100);

		setTitle("Game of Life");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Vaisseau glider
		tab.changeState(10,10);
		tab.changeState(11,11);
		tab.changeState(11,12);
		tab.changeState(10,12);
		tab.changeState(9,12);

		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (int i = 0; i < 100; i++) {
					for (int j = 0; j < 100; j++) {
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


		
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellSize = Math.min(panel.getWidth() / 100, panel.getHeight() / 100);
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

                tab.changeState(row, col);
                panel.repaint();
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

			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 100; j++) {
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
