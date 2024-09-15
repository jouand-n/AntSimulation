package ch.epfl.moocprog;

/**
 * Interface implémentée par {@link Environment} représentant la vue qu'ont les
 * générateurs de nourriture {@link FoodGenerator} de l'environnement
 * {@link Environment}
 */
public interface FoodGeneratorEnvironmentView {
	/**
	 * Méthode publique abstraite par défaut servant à ajouter une instance de
	 * {@link Food} dans une instance d'{@link Environment}
	 *
	 * @param food : {@link Food} nourriture à injecter dans l'environnement
	 */
	void addFood(Food food) throws IllegalArgumentException;
}
