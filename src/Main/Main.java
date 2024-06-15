package Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import Types.*;
import Operateurs.*;



public class Main extends JFrame {
	private static int WIDTH;
	private static int HEIGHT;
	private static Boolean running = false;
	private JPanel initialPanel;
    private JPanel gamePanel;
	private String pathToRegle = "C:\\Users\\denis\\OneDrive - edu.univ-paris13.fr\\Documents\\Prog\\Java\\AutomatesCellulaires\\src\\Main\\Regle.xml";
	private int[] slices;
	private String regle = "";
	private ReadXML readXML;
	private int[] tailles = {100, 100};
	private int type = 2;
	private int random = 0;
	private Boolean alive = false;

	JButton startButton = new JButton("Règles de Conway");
	JButton openButton = new JButton("Règle personnalisée");
	JButton quitButton = new JButton("Quitter");
	JButton pauseButton = new JButton("Pause");
	JButton menuButton = new JButton("Menu");

	private TableauDynamiqueND tab;
	private TableauDynamiqueND tabToDisplay;
	private TableauDynamiqueND tabForCalculations;


	public void goToGame(int ... taillesTab){
		tab = new TableauDynamiqueND(new Coords(true, taillesTab.length), taillesTab);
		tabForCalculations = tab.clone();
		tabToDisplay = tab.clone();
		HEIGHT = 8*tab.getTaille()+16;
		WIDTH = 8*tab.getTab()[0].getTaille()+66;
		setSize(WIDTH, HEIGHT);

		// Initialisation de la grille de manière aléatoire ou non
		if(type == 2){
			for(int i = 0; i < tabToDisplay.getTaille(); i++){
				for(int j = 0; j < tabToDisplay.getTab()[1].getTaille(); j++){
					tab.setState(Math.random() < random/100.0 ? 1 : 0, i, j);
				}
			}
		}
		else if(type == 1){
			tab.changeState(0, taillesTab[1]/2);
		}
		// Remplacement du panel initial par le panel du jeu
		getContentPane().remove(initialPanel);
		getContentPane().add(gamePanel);
		gamePanel.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(menuButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(quitButton);
		pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		gamePanel.add(buttonPanel, BorderLayout.SOUTH);
		repaint();
		validate();
		running = true;
		alive = true;
		new Thread(() -> {
			runGameOfLife();
		}).start();
	}

	public Main() {		

		WIDTH = 816;
		HEIGHT = 866;
		setSize(WIDTH, HEIGHT);

		setTitle("Jeu de la Vie!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);


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

		
		// Bouton menu
		menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Remplacement du panel du jeu par le panel initial
				getContentPane().remove(gamePanel);
				getContentPane().add(initialPanel);
				repaint();
				validate();
				running = false;
				alive = false;
			}
		});

		// Bouton start
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = 2;
				readXML = new ReadXML(pathToRegle);
				regle = readXML.getRegle();
				tailles = readXML.getTailles();
				random = readXML.getRandom();
				goToGame(tailles);
			}
		});

		// Bouton triangle de Sierpinski
		JButton sierpinskiButton = new JButton("Triangle de Sierpinski");
		sierpinskiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		sierpinskiButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		sierpinskiButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				readXML = new ReadXML("C:\\Users\\denis\\OneDrive - edu.univ-paris13.fr\\Documents\\Prog\\Java\\AutomatesCellulaires\\src\\Main\\RegleSierpinski.xml");
				regle = readXML.getRegle();
				tailles = readXML.getTailles();
				type = 1;
				goToGame(100, tailles[0]);
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
				readXML = new ReadXML(pathToRegle);
				regle = readXML.getRegle();
				tailles = readXML.getTailles();
				type = tailles.length;
				random = readXML.getRandom();
				System.out.println(type);
				if(type == 1){
					goToGame(100, tailles[0]);
				}
				else{
					goToGame(tailles);
				}
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

		// Champ de texte
		JTextField textField = new JTextField();
		textField.setMaximumSize(new Dimension(100, textField.getPreferredSize().height)); // Set maximum size
		textField.setAlignmentX(Component.CENTER_ALIGNMENT);
		textField.setAlignmentY(Component.CENTER_ALIGNMENT);

		// Boutton valider
		JButton validerButton = new JButton("Valider");
		validerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		validerButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		validerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				regle = textField.getText();
				type = 0;
				goToGame(tailles);
			}
		});


		initialPanel.add(Box.createVerticalGlue());
		initialPanel.add(startButton);
		initialPanel.add(sierpinskiButton);
		initialPanel.add(textField);
		initialPanel.add(validerButton);
		initialPanel.add(openButton);
		initialPanel.add(quitButton);
		initialPanel.add(Box.createVerticalGlue());


		// Interface Graphique du jeu
		gamePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (int i = 0; i < tabToDisplay.getTaille(); i++) {
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
                int cellSize = 8;
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;
				if(type == 1){
					row = 0;
				}

				tab.changeState(row, col);
                repaint();
            }
        });


		add(initialPanel);
		

		setVisible(true);

		// Zone de texte
		textField.setMaximumSize(new Dimension(initialPanel.getSize().width, textField.getMaximumSize().height));
		final int X = textField.getX();
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				textField.setSize(new Dimension(Integer.max(textField.getText().length()*8, 100), textField.getSize().height));
				textField.setLocation(X - (Integer.max(textField.getText().length()*8, 100) == 100 ? 0 : (textField.getText().length()*4)-50), textField.getY());
				
			}
		
			@Override
			public void removeUpdate(DocumentEvent e) {
				textField.setSize(new Dimension(Integer.max(textField.getText().length()*8, 100), textField.getSize().height));
				textField.setLocation(X - (Integer.max(textField.getText().length()*8, 100) == 100 ? 0 : (textField.getText().length()*4)-50), textField.getY());
			}
		
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

	}

	private void runGameOfLife() {
		if(type == 2){
			while (alive) {
				if (!running) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				TableauDynamiqueND tmp = tab.clone();
	
				ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
				for (int i = 0; i < tab.getTaille(); i++) {
					for (int j = 0; j < tab.getTab()[1].getTaille(); j++) {
						final int x = i;
						final int y = j;
						executor.submit(() -> {
							//Operateur oregle = new OU(new SI(new EQ(new COMPTER(new G8e(tab, x, y)), new CONST(3)), new CONST(1), new CONST(0)), new SI(new EQ(new COMPTER(new G8(tab, x, y)), new CONST(3)), new CONST(1), new CONST(0)));
							tmp.setState(new FormuleParser(regle, tab, ReadXML.dict, x, y).parse().evaluer(), x, y);
						});
					}
				}
	
				executor.shutdown();
				try {
					executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				tab = tmp.clone();
				gamePanel.paintImmediately(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
				validate();
			}
		}
		else if(type == 1){
			for(int n = 1; n < tab.getTaille(); n++) {
				for (int i = 0; i < tab.getTab()[n-1].getTaille(); i++) {
					tab.setState(new FormuleParser(regle, tab.getTab()[n-1], ReadXML.dict, i).parse().evaluer(), n, i);
				}
				gamePanel.paintImmediately(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
			}
			running = false;
			return;
		}
		else{
			while (alive) {
				if (!running) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				recGameOfLife(tab);
				tab = tabForCalculations.clone();
				gamePanel.paintImmediately(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
			}
		}
		tabToDisplay = tab.clone();
		int i = 0;
		while(tabToDisplay.getDimension() > tab.getDimension()-slices.length){
			tabToDisplay = tabToDisplay.slice(slices[i++]);
		}
	}



	private void recGameOfLife(TableauDynamiqueND t){
		if(t.getDimension() == 1){
			for(int i = 0; i < t.getTaille(); i++){
				tabForCalculations.setState(new FormuleParser(regle, tab, ReadXML.dict, t.getTab()[i].getCoords()).parse().evaluer(), t.getTab()[i].getCoords());
			}
		}
		else{
			for(int i = 0; i < t.getTaille(); i++){
				TableauDynamiqueND tmp = t.slice(i);
				recGameOfLife(tmp);
				TableauDynamiqueND[] tmp2 = t.getTab();
				tmp2[i] = tmp;
				t.setTab(tmp2);
			}
		}
	}
	



	public static void main(String[] args) {
		new Main();
	}
}
