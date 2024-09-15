package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.WORLD_HEIGHT;
import static ch.epfl.moocprog.config.Config.WORLD_WIDTH;

import ch.epfl.moocprog.utils.Vec2d;

/**
 * Classe finale représentant la position d'un objet placé dans un environnement
 * géré de manière torique, limité à 2 dimensions (x, y).
 */
public final class ToricPosition {
	
	/**
	 * {@link Vec2d} d'un objet quelconque de l'environnement
	 */
	private final Vec2d position;
	
	/**
	 * Constructeur vide, initialisant {@link ToricPosition} aux origines de
	 * l'environnement limité à 2 dimensions. Ce constructeur fait appel à
	 * {@link ToricPosition#ToricPosition(double, double)}.
	 */
	public ToricPosition () {
		this(0.0, 0.0);
	}
	
	/**
	 * Constructeur non vide, initialisant une {@link ToricPosition} dans
	 * l'environnement limité à 2 dimensions à partir de coordonnées x et y.
	 * 
	 * @param x : double, coordonnée sur l'axe des abscisses
	 * @param y : double, coordonnée sur l'axe des ordonnées
	 */

	public ToricPosition (double x, double y) {
		super();
		position = clampedPosition(x, y);
	}
	
	/**
	 * Constructeur non vide, initialisant une {@link ToricPosition} dans
	 * l'environnement limité à 2 dimensions à partir d'un {@link Vec2d}. Ce
	 * constructeur fait appel à
	 * {@link ToricPosition#ToricPosition(double, double)}.
	 * 
	 * @param autreVecteur : {@link Vec2d}
	 */

	public ToricPosition (Vec2d autreVecteur) {
		this(autreVecteur.getX(), autreVecteur.getY());
	}
	
	/**
	 * Getter
	 * 
	 * @return l'attribut constant {@link Vec2d}
	 */

	public Vec2d toVec2d() {
		return position;
	}

	/**
	 * Retourne une représentation en String de l'attribut {@link Vec2d} de
	 * l'instance {@code this} de {@link ToricPosition}
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return toVec2d().toString();
	}

	/**
	 * Fonction ayant pour but de prendre un {@link Vec2d} afin de le projeter dans
	 * un environnement géré de manière torique limité à 2 dimensions. Cet
	 * environnement aura pour largeur maximale la constante {@code WORLD_WIDTH} et
	 * pour hauteur maximale la constante {@code WORLD_HEIGHT}.
	 * 
	 * @param x : double, coordonnée non torique sur l'axe des abscisses
	 * @param y : double, coordonnée non torique sur l'axe des ordonnées
	 * @return une nouvelle instance de Vec2d ayant pour attributs les variables x
	 *         et y projetées dans l'environnement torique
	 */
	private static Vec2d clampedPosition (double x, double y) {
		final int WIDTH = getConfig().getInt(WORLD_WIDTH);
		final int HEIGHT = getConfig().getInt(WORLD_HEIGHT);
		
		
		while (x < 0 || x >= WIDTH) {
			if (x < 0) {
				x += WIDTH;
			} else if (x >= WIDTH) {
				x -= WIDTH;
			}
		}
		
		while (y < 0 || y >= HEIGHT) {
			if (y < 0) {
				y += HEIGHT;
			} else if (y >= HEIGHT) {
				y -= HEIGHT;
			}
		}
		
		return new Vec2d(x, y);
	}
	
	/**
	 * Construit une nouvelle {@link ToricPosition} correspondant à l'adition
	 * composant par composant entre {@code this} et {@code that}
	 * 
	 * @param that : la {@link ToricPosition} à aditionner avec {@code this}
	 * @return une nouvelle instance de {@link ToricPosition} correspondant à
	 *         l'adition de {@code this} et {@code that}
	 */
	public ToricPosition add (ToricPosition that) {
				
		return new ToricPosition((this.toVec2d()).add(that.toVec2d()));
	}
	
	/**
	 * Construit une nouvelle {@link ToricPosition} correspondant à l'adition
	 * composant par composant entre {@code this} et {@code that}
	 * 
	 * @param that : le {@link Vec2d} à aditionner avec {@code this}
	 * @return une nouvelle instance de {@link ToricPosition} correspondant à
	 *         l'adition de {@code this} et {@code that}
	 */
	public ToricPosition add (Vec2d vec) {
		
		return new ToricPosition((this.toVec2d()).add(vec));
	}
	
	/**
	 * Renvoie un {@link Vec2d} représentant la plus petite distance entre les
	 * {@link ToricPosition} {@code this} et {@code that}
	 *
	 * @param that : une autre {@link ToricPosition}
	 * @return une nouvelle instance de {@link Vec2d} correpondant à la plus petite
	 *         distance torique entre la {@link ToricPosition} {@code this} et la
	 *         {@link ToricPosition} {@code that}
	 */
	public Vec2d toricVector(ToricPosition that) {
		final int WIDTH = getConfig().getInt(WORLD_WIDTH);
		final int HEIGHT = getConfig().getInt(WORLD_HEIGHT);
		
		Vec2d reponse = new Vec2d(0.0, 0.0);
		
		
		// Calcul de la plus grande distance possible dans un environnement non torrique
		// de dimensions x=WIDTH et y=HEIGHT
		Vec2d min = new Vec2d (0.0, 0.0);
		Vec2d max = new Vec2d (WIDTH-1, HEIGHT-1);
		double distanceMin = min.distance(max);
		
		double distanceEnCours;
		
		
		// Constitution d'une liste de 9 candidats Vec2d dérivés de la ToricPosition that, 
		// prenant en compte les dimensions x=WIDTH et y=HEIGHT de l'environnement
		// torique,
		// et comprenant :
		// - that lui même ;
		// - that augmenté ou décrémenté de Vec2d (0 , HEIGHT) ;
		// - that augmenté ou décrémenté de Vec2d (WIDTH, 0) ;
		// - that augmenté de de Vec2d (+/- WIDTH, +/- HEIGHT), dans chacune des 4
		// combinaisons possibles
		Vec2d[] candidats = new Vec2d[9];
		int index = 0;
		
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				double x = (that.toVec2d().getX() + (i * WIDTH));
				double y = (that.toVec2d().getY() + (j * HEIGHT));
				candidats[index] = new Vec2d (x, y);
				++index;
			}
		}
		
		// Détermination du candidat Vec2d qui permet d'avoir la plus petite distance
		// entre lui et le Vec2d issu de la ToricPosition this
		
		for (Vec2d candidat : candidats) {
			distanceEnCours = this.toVec2d().distance(candidat);
			if (distanceEnCours < distanceMin) {
				distanceMin = distanceEnCours;
				reponse = candidat;
			}
		}

		// Retourne le calcul du Vec2d correspondant à la différence entre le Vec2d issu
		// de la ToricPosition this et le Vec2d reponse
		return reponse.minus(this.toVec2d());
	}
	
	/**
	 * Calcule la distance réelle entre les {@link ToricPosition} {@code this} et
	 * {@code that}, à partir du {@link Vec2d} retourné par la méthode
	 * {@link toricVector}
	 *
	 * @param that : une autre {@link ToricPosition}
	 * @return un double représentant la distance réelle entre les
	 *         {@link ToricPosition} {@code this} et {@code that}
	 */
	public double toricDistance(ToricPosition that) {
		Vec2d autreVecteur = this.toricVector(that);
		return autreVecteur.length();
		
	}
	
}