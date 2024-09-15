package ch.epfl.moocprog;

/**
 * Classe représentant un objet quelconque ayant une position dans un
 * environnement géré de manière torique, limité à 2 dimensions (x, y). Cette
 * classe est héritée par plusieurs autres classes d'objets visant à intégrer
 * l'environnement: {@link Animal}, {@link Anthill}, {@link Pheromone}.
 */
public class Positionable {

	/**
	 * {@link ToricPosition} d'un objet quelconque dans l'environnement
	 */
	private ToricPosition position;

	/**
	 * Constructeur vide, initialisant {@link Positionable} avec une
	 * {@link ToricPosition} vide
	 */
	public Positionable () {
		position = new ToricPosition();
	}
	
	/**
	 * Constructeur non vide, initialisant {@link Positionable} avec une
	 * {@link ToricPosition} non vide
	 * 
	 * @param other: {@link ToricPosition} position torique de l'instance de
	 *               {@link Positionable}
	 */
	public Positionable (ToricPosition other) {
		position = new ToricPosition(other.toVec2d());
	}
	
	/**
	 * Getter
	 * 
	 * @return l'attribut {@link ToricPosition} représentant une position dans
	 *         l'environnement torique limité à 2 dimensions (x, y)
	 */
	public ToricPosition getPosition() {
		return position;
	}
	
	/**
	 * Setter de l'attribut {@link ToricPosition}
	 * 
	 * @param position: une {@link ToricPosition}
	 */
	protected final void setPosition(ToricPosition position) {
		this.position = position;
	}
	
	/**
	 * Retourne une représentation en String de l'attribut {@link ToricPosition} de
	 * la {@link Positionable} {@code this}
	 * 
	 * @return
	 */
	public String toString() {
		return position.toString();
	}
}
