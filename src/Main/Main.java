package Main;
// import javax.swing.JFrame;

import Types.Coords;
import Types.RegleDuJeu;
import Types.TableauDynamiqueND;
import Types.Voisinage;

// import java.util.Random;


public class Main 
{
	public static void main(String[] args)
	{
		// int posx, posy;

		TableauDynamiqueND tab = new TableauDynamiqueND(new Coords(2), 5, 5);

		Coords[] alive = tab.getAlive();

		for(Coords a : alive)
		{
			if(a != null)
			{
				a.display();
			}
		}


		GrilleGraphique grid = new GrilleGraphique(tab.getTaille(), tab.getTab()[0].getTaille(), 8);
		
		// Random r = new Random();
		
		int i;
		//Trente fois...
		// for(i=0; i<5; i++)
		// {
		// 	//on tire une case au hasard dans la grille
		// 	posx = r.nextInt(grid.getLargeur());
		// 	posy = r.nextInt(grid.getHauteur());

		// 	//et on la colorie en rouge
		// 	tab.changeState(posx, posy);
		// 	grid.colorierCase(posx, posy);
		// }
		tab.changeState(1,1);
		grid.colorierCase(1,1);
		tab.changeState(2,1);
		grid.colorierCase(1,2);
		tab.changeState(3,1);
		grid.colorierCase(1,3);


		TableauDynamiqueND tmp = tab.clone();
        Coords[] regle = {new Coords(0, 0), new Coords(-1, -1), new Coords(-1, 0), new Coords(-1, 1), new Coords(0, -1), new Coords(0, 1), new Coords(1, -1), new Coords(1, 0), new Coords(1, 1)};
        RegleDuJeu regleDuJeu = new RegleDuJeu(new int[]{3}, new int[]{4, 3});

		grid.repaint();

		try{
			Thread.sleep(1000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}

		for(int n = 0; n<30; ++n){
			for(i = 0; i < 5; i++){
				for(int j = 0; j < 5; j++){
					Voisinage v = tab.voisinage(regle,j,i);
					if(tab.getState(j,i)){
						if(!regleDuJeu.estSurvie(v)){
							tmp.changeState(j,i);
							grid.effacerCase(i, j);
						}
						else{
							grid.colorierCase(i, j);
						}
					}
					else{
						if(regleDuJeu.estNee(v)){
							tmp.changeState(j,i);
							grid.colorierCase(i, j);
						}
						else{
							grid.effacerCase(i, j);
						}
					}
				}
			}
			grid.repaint();
			tab = tmp.clone();
			try{
				Thread.sleep(300);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
