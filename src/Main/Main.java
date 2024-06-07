package Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSlider;
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
	private static int profondeur = 0;

	private TableauDynamiqueND tab;

	public Main() {
		tab = new TableauDynamiqueND(new Coords(true, 3), 10, 150, 200);
		WIDTH = 8*tab.getTab()[0].getTab()[0].getTaille()+66;
		HEIGHT = 8*tab.getTab()[0].getTaille()+66;
		setTitle("Jeu de la Vie!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Vaisseau glider
		// tab.changeState(10,10);
		// tab.changeState(11,11);
		// tab.changeState(11,12);
		// tab.changeState(10,12);
		// tab.changeState(9,12);

		// Blinkers à chaque profondeur
		for (int i = 0; i < tab.getTaille(); i++) {
			tab.changeState(i, 10+i, 10);
			tab.changeState(i, 11+i, 10);
			tab.changeState(i, 12+i, 10);
		}

		// On colorie des cellules aléatoirement
		for (int i = 0; i < tab.getTaille(); i++) {
			for (int j = 0; j < tab.getTab()[0].getTaille(); j++) {
				for (int k = 0; k < tab.getTab()[0].getTab()[0].getTaille(); k++) {
					if (Math.random() < 0.1) {
						tab.changeState(i, j, k);
					}
				}
			}
		}


		// Interface Graphique
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (int i = 0; i < tab.getTab()[profondeur].getTaille(); i++) {
					for (int j = 0; j < tab.getTab()[profondeur].getTab()[0].getTaille(); j++) {
						if (tab.getState(profondeur, i, j)) {
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
                int cellSize = Math.min(WIDTH / tab.getTab()[profondeur].getTab()[0].getTaille(), HEIGHT / tab.getTab()[profondeur].getTaille());
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;
				tab.getTab()[profondeur].changeState(row, col);
                repaint();
            }
        });


		// Slider pour la profondeur
		JSlider slider = new JSlider(JSlider.VERTICAL, 0, tab.getTaille()-1, profondeur);
		// slider.setMajorTickSpacing(10);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(e -> {
			profondeur = slider.getValue();
		});


		add(panel);
		add(button, "South");
		add(slider, "East");
		setVisible(true);
		runGameOfLife();
	}

	private void runGameOfLife() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
				TableauDynamiqueND tabForCalculation = tab.slice(i);
				for (int j = 0; j < tabForCalculation.getTaille(); j++) {
					for(int k = 0; k < tabForCalculation.getTab()[j].getTaille(); k++){
						Operateur regle = new OU(new SI(new EQ(new COMPTER(new G8e(tabForCalculation, j, k)), new CONST(3)), new CONST(1), new CONST(0)), new SI(new EQ(new COMPTER(new G8(tabForCalculation, j, k)), new CONST(3)), new CONST(1), new CONST(0)));
						tmp.setState(regle.evaluer(), i, j, k);
					}
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
