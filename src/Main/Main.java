package Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Graphics;
import Types.*;
import Operateurs.*;



public class Main extends JFrame {
	private static int WIDTH;
	private static int HEIGHT;
	private static Boolean running = false;
	private JPanel initialPanel;
    private JPanel gamePanel;
	private String pathToRegle = "src/Regle/Regle.xml";
	private int profondeur = 0;

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

		
		JButton startButton = new JButton("Règles de Conway");
		JButton openButton = new JButton("Règle personnalisée");
		JButton quitButton = new JButton("Quitter");
		JButton pauseButton = new JButton("Pause");


		// Création du panel initial
		initialPanel = new JPanel();
		initialPanel.setLayout(new BoxLayout(initialPanel, BoxLayout.PAGE_AXIS));

		// Bouton pause
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pauseButton.getText().equals("Pause")) {
					pauseButton.setText("Reprendre");
					running = false;
				} else {
					pauseButton.setText("Pause");
					running = true;
				}
			}
		});
		pauseButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startButton.getPreferredSize().height)); // Set maximum size



		// Bouton start
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Remplacement du panel initial par le panel du jeu
				getContentPane().remove(initialPanel);
				getContentPane().add(gamePanel);
				gamePanel.setLayout(new BorderLayout());
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
				buttonPanel.add(pauseButton);
				buttonPanel.add(quitButton);
				pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				gamePanel.add(buttonPanel, BorderLayout.SOUTH);
				validate();
				repaint();
				running = true;
			}
		});


		// Bouton ouvrir les fichiers
		openButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		openButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		openButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				pathToRegle = selectedFile.getAbsolutePath();
				System.out.println(pathToRegle);
				// Remplacement du panel initial par le panel du jeu
				getContentPane().remove(initialPanel);
				getContentPane().add(gamePanel);
				gamePanel.setLayout(new BorderLayout());
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
				buttonPanel.add(pauseButton);
				buttonPanel.add(quitButton);
				gamePanel.add(buttonPanel, BorderLayout.SOUTH);
				validate();
				repaint();
				running = true;
			}
		}
	});


		// Bouton quitter
		quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		quitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Quitter l'application
				System.exit(0);
			}
		});

		initialPanel.add(Box.createVerticalGlue());
		initialPanel.add(startButton);
		initialPanel.add(openButton);
		initialPanel.add(quitButton);
		initialPanel.add(Box.createVerticalGlue());


		// Interface Graphique du jeu
		gamePanel = new JPanel() {
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




		// Gestion de la souris
        gamePanel.addMouseListener(new MouseAdapter() {				
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellSize = Math.min(gamePanel.getWidth() / tab.getTaille(), gamePanel.getHeight() / tab.getTab()[1].getTaille());
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

				tab.changeState(row, col);
                repaint();
            }
        });


		add(initialPanel);

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
					// Operateur regle = new SI(new EQ(new COMPTER(new G0(tab, i, j)),new CONST(1)),new SI(new EQ(new COMPTER(new G8e(tab,i,j)),new CONST(3)),new CONST(1),new CONST(0)),new SI(new OU(new EQ(new COMPTER(new G8e(tab, i, j)),new CONST(2)),new EQ(new COMPTER(new G8e(tab, i, j)),new CONST(3))),new CONST(1),new CONST(0)));
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
