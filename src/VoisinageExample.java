import java.util.Iterator;

//Cette classe représente un voisinage... Le point x est le point étudié, 
//et pourra être déplacé sur une autre valeur avec la fonction reset
//Le voisinage de x sera x-2, x+1 et x+3 (uniquement les valeurs entre 0 et 10)

//Notre classe doit implémenter deux interfaces : 
//    . Iterator, qui possède les fonction hasNext et next,
//      qui représentent la façon dont on parcourt les voisins.
//      Chaque voisin étant un entier, ce sera un Iterator<Integer> car on renvoie des entiers
//
//    . Iterable, qui possède la fonction iterator qui doit renvoyer un objet Iterator
//      permettant de parcourir notre voisinage. Si on implémente cette interface, on pourra
//      utiliser un foreach pour parcourir notre voisinage.
//
//Notre voisinage implémentera les deux interfaces : il sera donc parcourable avec un foreach, 
//et proposera en même temps les fonctions permettant de le parcourir

public class VoisinageExample implements Iterator<Integer>, Iterable<Integer> {
	private int x; //Le point étudié
	private int index; //Le nombre de voisins de notre point déjà parcourus
	private int[] tab = {-2, 1, 3}; //Les coordonnées des voisins
	
	//Le constructeur qui permet d'initialiser le point
	public VoisinageExample(int a)
	{
		x=a;
		index=0;
	}

	//hasnext renvoie vrai s'il existe encore un voisin non parcouru de notre point entre 0 et 10
	@Override
	public boolean hasNext() {
		while(index<=2 && (tab[index]+x >10 || tab[index]+x<0))
				index+=1;
		return (index<=2);
	}

	//next renvoie la valeur du prochain voisin à parcourir (un entier)
	@Override
	public Integer next() {
		return tab[index++]+x;
	}
	
	//reset n'est pas dans les interface, mais permet de recentrer notre voisinage sur un nouveau point, sans passer par la construction
	//et l'allocation d'un nouvel objet... cela peut être pratique pour accélérer un peu le programme quand on doit parcourir le voisinage de nombreux points
	public void reset(int newa)
	{
		x=newa;
		index=0;
	}

	//iterator renvoie un objet permettant de parcourir notre voisinage. Cet objet doit suivre l'interface Iterator et avoir les 
	//fonctions next et hasnext... C'est donc notre objet en lui-même, qui possède ses propres règles pour être parcouru (souvent, 
	//on externalise ces methodes dans une autre classe, et on peut ainsi faire différentes classes de parcours qui parcourent un même
	//objet de différentes manières - genre un arbre, et des itérateurs pour le parcourir en largeur, profondeur, etc.
	//ici, pas utile d'externaliser, on a une seule façon de parcourir nos voisins.)
	@Override
	public Iterator<Integer> iterator() {
		return this;
	}
	
	

}
