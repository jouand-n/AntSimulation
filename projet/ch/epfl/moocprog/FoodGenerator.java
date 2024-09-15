package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.random.NormalDistribution;
import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;

/**
 * Classe finale ayant pour but de générer périodiquement de la nourriture
 * {@link Food} dans l’environnement {@link Environment}
 */
public final class FoodGenerator {

	/**
	 * {@link Time} servant de compteur de temps afin de connaître le temps écoulé
	 * depuis la dernière instanciation d'une Food par cette instance {@code this}
	 * de {@link FoodGenerator}
	 */
	private Time timeCounter;
	
	/**
	 * Constructeur vide initialisant une instance de {@link FoodGenerator} avec son
	 * attribut {@link Time} démarré à zéro.
	 */
	public FoodGenerator() {
		timeCounter = Time.ZERO;
	}

	/**
	 * Permet à l'instance {@code this} de {@link FoodGenerator} de mettre à jour
	 * l'instance de {@link FoodGeneratorEnvironmentView} à chaque pas de temps
	 * {@link Time} en employant la méthode {@link addFood()}
	 * 
	 * @param env : {@link FoodGeneratorEnvironmentView}, instance avec vue limitée
	 *            de {@link Environment}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link FoodGenerator}
	 */
	public void update(FoodGeneratorEnvironmentView env, Time dt) {
		final Time FOOD_GENERATOR_DELAY = Context.getConfig().getTime(Config.FOOD_GENERATOR_DELAY);
		
		final double NEW_FOOD_QUANTITY_MIN = Context.getConfig().getDouble(Config.NEW_FOOD_QUANTITY_MIN);
		final double NEW_FOOD_QUANTITY_MAX = Context.getConfig().getDouble(Config.NEW_FOOD_QUANTITY_MAX);
		
		final double WORLD_WIDTH = Context.getConfig().getInt(Config.WORLD_WIDTH);
		final double WORLD_HEIGHT = Context.getConfig().getInt(Config.WORLD_HEIGHT);
		
		timeCounter = timeCounter.plus(dt);
		
		while (timeCounter.compareTo(FOOD_GENERATOR_DELAY) >= 0) {
			
			timeCounter = timeCounter.minus(FOOD_GENERATOR_DELAY);
			
			// tirage aléatoire selon une distribution normale des coordonnées de la
			// position où placer la nourriture
			double x = NormalDistribution.getValue(WORLD_WIDTH/2.0, WORLD_WIDTH*WORLD_WIDTH/16.0);
			double y = NormalDistribution.getValue(WORLD_HEIGHT/2.0, WORLD_HEIGHT*WORLD_HEIGHT/16.0);
			ToricPosition aRandomPosition= new ToricPosition (x, y);
			
			// tirage aléatoire selon une distribution uniforme de la quantité de nourriture
			// à placer
			double quantity = UniformDistribution.getValue(NEW_FOOD_QUANTITY_MIN, NEW_FOOD_QUANTITY_MAX);
			
			Food aRandomFood = new Food (aRandomPosition, quantity);
			
			env.addFood(aRandomFood);
			
		} 
	}
}
