package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.ALPHA;
import static ch.epfl.moocprog.config.Config.BETA_D;
import static ch.epfl.moocprog.config.Config.Q_ZERO;

import ch.epfl.moocprog.app.Context;

/**
 * Classe finale représentant le modèle que nous allons utiliser pour le
 * déplacement sensoriel d'instances de {@link Ant} tenant compte de la présence
 * de phéromone {@link Pheromone}. Elle implémente l'interface
 * {@link AntRotationProbabilityModel}.
 */
public final class PheromoneRotationProbabilityModel implements AntRotationProbabilityModel {

	/**
	 * Constructeur vide initialisant une instance de
	 * {@link PheromoneRotationProbabilityModel}
	 */
	public PheromoneRotationProbabilityModel() {
		super();
	};

	/**
	 * Méthode retournant une instance de {@link RotationProbability} calculée en
	 * fonction des quantités de phéromone {@link Pheromone} perceptibles par une
	 * instance de {@link Ant} de {@link ToricPosition} {@code position}.
	 * 
	 * @param movementMatrix : {@link RotationProbability}, instance embarquant les
	 *                       modèles d'angles de rotation et probabilités associées
	 *                       par défaut
	 * @param position       : {@link ToricPosition}, position de l'instance de
	 *                       {@link Ant} testée
	 * 
	 * @param directionAngle : {@code double}, angle de direction de l'instance de
	 *                       {@link Ant} testée
	 * @param env            : instance de {@link AntEnvironmentView} exploitée par
	 *                       {@code this}
	 * 
	 * @return une instance de {@link RotationProbability} comprenant la liste
	 *         d'angles extraits de {@code movementMatrix} et la liste nouvellement
	 *         calculée de probabilités associées
	 */
	public RotationProbability computeRotationProbs (RotationProbability movementMatrix, ToricPosition position, double directionAngle, AntEnvironmentView env) {

		int alpha = Context.getConfig().getInt(ALPHA);
		double beta = Context.getConfig().getDouble(BETA_D);
		double QZero = Context.getConfig().getDouble(Q_ZERO);
		
		double[] Q = env.getPheromoneQuantitiesPerIntervalForAnt(position, directionAngle, movementMatrix.getAngles());
		double[] P = movementMatrix.getProbabilities();
		
		double[] numerateur = new double [Q.length]; 
		double S = 0.0;
		
		for (int i = 0; i < Q.length; ++i) {
		
			double d = 1.0 / ( 1.0 + Math.exp(-beta * (Q[i] - QZero)));
			
			numerateur[i] = P[i] * Math.pow(d, alpha);
			S += numerateur[i];
		}
		
		double[] newP = new double [P.length];
		
		for (int j = 0; j < newP.length; ++j) {
			
			newP[j] = numerateur[j] / S;
		}
		
		RotationProbability result = new RotationProbability(movementMatrix.getAngles(), newP);
		
		return result;
	}
}
