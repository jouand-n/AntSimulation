package ch.epfl.moocprog;

/**
 * Interface implémentée par {@link Environment} représentant la vue qu'ont les
 * instances des fourmilières {@link Anthill} de l'environnement
 * {@link Environment}
 */
public interface AnthillEnvironmentView {

	public void addAnt(Ant ant) throws IllegalArgumentException;
}
