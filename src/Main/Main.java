package Main;

import Operateurs.COMPTER;
import Operateurs.CONST;
import Operateurs.EQ;
import Operateurs.OU;
import Operateurs.Operateur;
import Operateurs.SI;
import Types.Coords;
import Types.G8;
import Types.G8e;
import Types.TableauDynamiqueND;


public class Main 
{
	public static void main(String[] args)
	{

		TableauDynamiqueND tab = new TableauDynamiqueND(new Coords(true, 2), 50, 50);

		Coords[] alive = tab.getAlive();

		for(Coords a : alive)
		{
			if(a != null)
			{
				a.display();
			}
		}


		GrilleGraphique grid = new GrilleGraphique(tab.getTaille(), tab.getTab()[0].getTaille(), 8);

		
		int i;
		tab.changeState(1,1);
		grid.colorierCase(1,1);
		tab.changeState(2,1);
		grid.colorierCase(1,2);
		tab.changeState(3,1);
		grid.colorierCase(1,3);

		// changement de cases random
		// for(i = 0; i < 100; i++){
		// 	int x = (int)(Math.random()*50);
		// 	int y = (int)(Math.random()*50);
		// 	tab.changeState(x, y);
		// 	if(tab.getState(x,y))
		// 	{
		// 		grid.colorierCase(y,x);
		// 	}
		// 	else
		// 	{
		// 		grid.effacerCase(y,x);
		// 	}
		// }

		// Vaisseau glider
		tab.changeState(10,10);
		grid.colorierCase(10,10);
		tab.changeState(11,11);
		grid.colorierCase(11,11);
		tab.changeState(11,12);
		grid.colorierCase(12,11);
		tab.changeState(10,12);
		grid.colorierCase(12,10);
		tab.changeState(9,12);
		grid.colorierCase(12,9);



		TableauDynamiqueND tmp = tab.clone();

		grid.repaint();

		try{
			Thread.sleep(100);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}

		while(true){
			for(i = 0; i < 50; i++){
				for(int j = 0; j < 50; j++){
					Operateur regle = new OU(new SI(new EQ(new COMPTER(new G8e(tab, i,j)),  new CONST(3)),  new CONST(1),  new CONST(0)), new SI(new EQ(new COMPTER(new G8(tab, i,j)),  new CONST(3)), new CONST(1), new CONST(0)));
					tmp.setState(regle.evaluer(), i,j);
				}
			}
			for(i = 0; i < 50; i++){
				for(int j = 0; j < 50; j++){
					if(tmp.getState(i,j)){
						grid.colorierCase(j,i);
					}
					else{
						grid.effacerCase(j,i);
					}
					try{
						Thread.sleep(1);
					}
					catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
			grid.repaint();
			tab = tmp.clone();
			try{
				Thread.sleep(30);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
