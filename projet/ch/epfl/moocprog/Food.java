package ch.epfl.moocprog;

/**
 * Classe finale modélisant la nourriture consommable par les fourmis. Elle
 * hérite de la classe {@link Positionable}.
 */
public final class Food extends Positionable {

	/**
	 * Quantité de nourriture disponible
	 */
	private double quantity;

	/**
	 * Constructeur non vide initialisant une instance de {@link Food} à l'origine
	 * de l'environnement torique(ToricPosition (0.0, 0.0) et avec une quantité
	 * initiale de nourriture disponible
	 * 
	 * @param quantity : double, quantité de nourriture initiale
	 */
	public Food(double quantity) {
		super();
		initQuantity(quantity);
	}
	
	/**
	 * Constructeur non vide initialisant une instance de {@link Food} à une
	 * certaine position torique et avec une quantité initiale de nourriture
	 * disponible
	 * 
	 * @param position : {@link ToricPosition}, position de dépôt de la nourriture
	 * @param quantity : double, quantité de nourriture initiale
	 */
	public Food(ToricPosition position, double quantity) {
		super(position);
		initQuantity(quantity);
	}	
	
	/**
	 * Initialise la quantité de nourriture disponible lors de l'instanciation d'une
	 * nouvelle {@link Food} en controlant que cette quantité soit toujours
	 * supérieure ou égale à zéro
	 * 
	 * @param quantity: double, quantité de nourriture initialement disponible
	 */
	private void initQuantity(double quantity) {
		if (quantity < 0.0) {
			this.quantity = 0.0;
			return;
		}
		this.quantity = quantity;
	}
	
	/**
	 * Getter
	 * 
	 * @return l'attribut quantity représentant la quantité de nourriture disponible
	 */
	public double getQuantity() {
		return quantity;
	}
	
	/**
	 * Retourne une représentation en String de l'instance de
	 * {@link Food}{@code this}
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		String s = super.toString();
		s += "\n";
		s += String.format("Quantity : %.2f", getQuantity());
		return s;
	}

	/**
	 * Prélève de manière contrôlée une quantité de nourriture dans le stock de
	 * nourriture disponible au sein de l'instance de {@link Food}{@code this}
	 *
	 * @param aQuantity : double, quantité désirée de nourriture à prélever
	 * @return une quantité de nourriture réellement prélevable
	 * @throws IllegalArgumentException en cas de @param aQuantity négatif
	 */
	public double takeQuantity(double aQuantity) throws IllegalArgumentException {
		if (aQuantity < 0.0) {
			throw new IllegalArgumentException ("Le takeQuantity d'une quantité de nourriture négative n'a pas de sens.");
		}

		if (aQuantity <= getQuantity()) {
			quantity -= aQuantity;
		} else {
			aQuantity = quantity;
			quantity = 0.0;
		}

		return aQuantity;
	}
}