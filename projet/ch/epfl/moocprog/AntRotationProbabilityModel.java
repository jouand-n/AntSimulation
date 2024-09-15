package ch.epfl.moocprog;

/**
 * Interface implémentée par {@link PheromoneRotationProbabilityModel}
 * représentant le modèle de probabilité de rotation de déplacement des
 * instances de {@link Ant} par rapport à leur position {@link ToricPosition} et
 * la détection des phéromones {@link Pheromone} perceptibles dans leur
 * environnement visible {@link AntEnvironmentView}
 */
public interface AntRotationProbabilityModel {

	RotationProbability computeRotationProbs (RotationProbability movementMatrix, ToricPosition position, double directionAngle, AntEnvironmentView env);
}
