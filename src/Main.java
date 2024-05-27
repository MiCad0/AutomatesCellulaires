import javax.swing.JFrame;
import java.util.Random;


public class Main 
{
	public static void main(String[] args)
	{
		int posx, posy;

		TableauDynamiqueND tab = new TableauDynamiqueND(new Coords(3), 300, 300);

		Coords[] alive = tab.getAlive();

		for(Coords a : alive)
		{
			if(a != null)
			{
				a.display();
			}
		}


		GrilleGraphique grid = new GrilleGraphique(tab.getTaille(), tab.getTab()[0].getTaille(), 8);
		
		Random r = new Random();
		
		int i;
		//Trente fois...
		for(i=0; i<30; i++)
		{
			//on tire une case au hasard dans la grille
			posx = r.nextInt(grid.getLargeur());
			posy = r.nextInt(grid.getHauteur());
			
			//et on la colorie en rouge
			tab.changeState(posx, posy);
			grid.colorierCase(posx, posy);
		}

		grid.repaint();
		
		
        
	}
}
