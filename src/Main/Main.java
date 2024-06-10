package Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.management.openmbean.OpenType;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
	TableauDynamiqueND tabToDisplay;

	private static TableauDynamiqueND tab;


	public Main(int [] tailles) {
		tab = new TableauDynamiqueND(new Coords(true, tailles.length), tailles);
		tabToDisplay = tab.clone();
		for(int i = 0; i < tab.getDimension()-2; ++i){
			tabToDisplay = tabToDisplay.slice(profondeur);
		}


		WIDTH = 8*tabToDisplay.getTab()[0].getTaille()+66;
		HEIGHT = 8*tabToDisplay.getTaille()+66;
		setTitle("Jeu de la Vie!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);


		// Blinkers à chaque profondeur
		for (int i = 0; i < tab.getTaille()-2; i++) {
			tab.changeState(i, 10+i, 10);
			tab.changeState(i, 11+i, 10);
			tab.changeState(i, 12+i, 10);
		}

		// Interface Graphique
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (int i = 0; i < tabToDisplay.getTaille(); i++) {
					for (int j = 0; j < tabToDisplay.getTab()[i].getTaille(); j++) {
						if (tabToDisplay.getState(i, j)) {
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
                int cellSize = Math.min(tailles[tailles.length-1]*8 / tabToDisplay.getTab()[0].getTaille(), tailles[tailles.length-2]*8 / tabToDisplay.getTaille());
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;
				int[] tmp = new int[tab.getDimension()];
				for(int i = 0; i < tab.getDimension()-3; ++i){
					tmp[i] = 0;
				}
				tmp[tab.getDimension()-3] = profondeur;
				tmp[tab.getDimension()-2] = row;
				tmp[tab.getDimension()-1] = col;
				tab.changeState(tmp);
				tabToDisplay.changeState(row, col);
				if(!running)
	                repaint();
            }
        });


		add(panel);
		add(button, "South");


		JSlider slider = new JSlider(JSlider.VERTICAL, 0, tab.getTaille()-1, profondeur);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(e -> {
			profondeur = slider.getValue();
			tabToDisplay = tab.clone();
			for(int j = 0; j < tab.getDimension()-2; ++j){
				tabToDisplay = tabToDisplay.slice(profondeur);
			}
		});


		add(slider, "East");
		setVisible(true);
		runGameOfLife();
	}


	private TableauDynamiqueND update() throws Exception{
		TableauDynamiqueND tmp = tab.clone();
		try{
			if(tab.getDimension() == 2){
				for (int i = 0; i < tmp.getTaille(); i++) {
					for (int j = 0; j < tmp.getTab()[i].getTaille(); j++) {
						Operateur regle = new OU(new SI(new EQ(new COMPTER(new G8e(tab, i, j)), new CONST(3)), new CONST(1), new CONST(0)), new SI(new EQ(new COMPTER(new G8(tab, i, j)), new CONST(3)), new CONST(1), new CONST(0)));
						int state = regle.evaluer();
						tmp.setState(state, i, j);
						tabToDisplay.setState(state, i, j);
					}
				}
			}
			else{
				throw new Exception("Dimension non gérée");
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return tmp;
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
			try{
				tab = update();
			}
			catch(Exception e){
				System.out.println(e);
			}
			repaint();
		}
	}


	public static void main(String[] args) {
		// int[] tailles = new int[args.length];
		// for (int i = 0; i < args.length; i++) {
		// 	tailles[i] = Integer.parseInt(args[i]);
		// }
		int[] tailles = {10, 150, 200};
		new Main(tailles);
	}
}
