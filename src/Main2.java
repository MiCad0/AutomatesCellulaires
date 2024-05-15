
public class Main2 {
	public static void main(String[] args)
	{
		//Dans cet exemple, on construit un voisinage d'entiers... Les voisins d'un entiers x sont, dans notre exemple, les 
		//valeurs x-2, x+1, et x+3 qui sont situées entre 0 et 10
		
		//On créée le voisinage autour du point 5
		VoisinageExample a = new VoisinageExample(5);
		
		
		System.out.println("On parcourt les 'voisins' de 5 entre 0 et 10:");
		//On peut utiliser le foreach sur notre classe de voisinage
		for(Integer voisin : a)
		{
			System.out.println(voisin);
		}
		
		//On fait le parcours d'un autre point en recentrant notre voisinage sur 8
		a.reset(8);
		System.out.println("On parcourt les 'voisins' de 8 entre 0 et 10:");
		for(Integer voisin : a)
		{
			System.out.println(voisin);
		}
	}
}
