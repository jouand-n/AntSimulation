package ch.epfl.moocprog;

/**
 * Interface implémentée par {@link Environment} représentant la vue spécifique
 * qu'ont les instances des fourmis {@link AntWorker} (et non
 * {@link AntSoldier}) de l'environnement {@link Environment}. Elle hérite de
 * l'interface {@link AntEnvironmentView}.
 */
public interface AntWorkerEnvironmentView extends AntEnvironmentView {

	public boolean dropFood(AntWorker antWorker) throws IllegalArgumentException;

	public Food getClosestFoodForAnt(AntWorker antWorker);
}
