import javax.swing.JFrame;
import java.util.Random;


public class Main 
{
	public static void main(String[] args)
	{
		int posx, posy;
		//On crée un objet ChampGraphique de 50 cases de large, et 60 de haut
		GrilleGraphique grid = new GrilleGraphique(80, 80, 8);
		
		Random r = new Random();
		
		int i;
		//Trente fois...
		for(i=0; i<30; i++)
		{
			//on tire une case au hasard dans la grille
			posx = r.nextInt(grid.getLargeur());
			posy = r.nextInt(grid.getHauteur());
			
			//et on la colorie en rouge
			grid.colorierCase(posx, posy);
		}
		
		
        
	}
}
