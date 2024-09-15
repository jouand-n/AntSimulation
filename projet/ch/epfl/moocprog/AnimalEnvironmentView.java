package ch.epfl.moocprog;

import java.util.List;

import ch.epfl.moocprog.utils.Time;

/**
 * Interface implémentée par {@link Environment} représentant la vue qu'ont les
 * instances des animaux {@link AntWorker}, {@link AntSoldier} et
 * {@link Termite} de l'environnement {@link Environment}
 */
public interface AnimalEnvironmentView {

	public List<Animal> getVisibleEnemiesForAnimal(Animal from);
	
	public boolean isVisibleFromEnemies(Animal from);
	
	public void selectAfterMoveDispatch(Ant ant, Time dt);
	
	public void selectAfterMoveDispatch(Termite termite, Time dt);
	
	public RotationProbability selectComputeRotationProbsDispatch(Ant ant);
	
	public RotationProbability selectComputeRotationProbsDispatch(Termite termite);
	
	public void selectSpecificBehaviorDispatch (AntWorker antWorker, Time dt);
	
	public void selectSpecificBehaviorDispatch (AntSoldier antSoldier, Time dt);
	
	public void selectSpecificBehaviorDispatch (Termite termite, Time dt);
}
