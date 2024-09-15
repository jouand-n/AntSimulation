package ch.epfl.moocprog;

/**
 * Interface implémentée par {@link Environment} représentant la vue qu'ont les
 * instances des fourmis {@link AntWorker} et {@link AntSoldier} de
 * l'environnement {@link Environment}
 */
public interface AntEnvironmentView extends AnimalEnvironmentView {

	public void addPheromone(Pheromone pheromone) throws IllegalArgumentException;
	
	double[] getPheromoneQuantitiesPerIntervalForAnt(ToricPosition position, double directionAngleRad, double[] angles);
}
