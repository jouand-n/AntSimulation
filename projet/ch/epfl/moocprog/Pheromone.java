package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.PHEROMONE_EVAPORATION_RATE;
import static ch.epfl.moocprog.config.Config.PHEROMONE_THRESHOLD;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.utils.Time;

/**
 * Classe finale représentant le concept d'une phéromone. Cette classe hérite de
 * la classe {@link Positionable}.
 */
public final class Pheromone extends Positionable{
	
	/**
	 * Quantité actuelle de phéromone
	 */
	private double quantity;
	
	/**
	 * Constructeur non vide initialisant une instance de {@link Pheromone} à partir
	 * d'une {@link ToricPosition} et d'une valeur initiale de {@code quantity}.
	 * 
	 * @param position : {@link ToricPosition}, position torique initiale de
	 *                 l'instance {@code this} de {@link Pheromone} dans
	 *                 l'environnement
	 * @param quantity : double, quantité initale de phéromones de l'instance
	 *                 {@code this} de {@link Pheromone}
	 */
	public Pheromone (ToricPosition position, double quantity) {
		super(position);
		this.quantity = quantity;
	}
	
	/**
	 * Getter
	 * 
	 * @return la quantité actuelle de phéromones de l'instance {@code this} de
	 *         {@link Pheromone}
	 */
	public double getQuantity() {
		return quantity;
	}
	
	/**
	 * Détermine si la quantité de phéromone est négligeable, c'est-à-dire
	 * inférieure au seuil de phéromone défini dans le fichier de configuration
	 * initial avec la valeur {@code PHEROMONE_THRESHOLD}
	 * 
	 * @return {@code true} si la quantité est inférieure au seuil, sinon
	 *         {@code false}.
	 */
	public boolean isNegligible() {
		return getQuantity() < Context.getConfig().getDouble(PHEROMONE_THRESHOLD);
	}
	
	/**
	 * Méthode en charge de faire évoluer l'instance {@code this} de
	 * {@link Pheromone} en fonction du {@code dt} de {@link Time} et du taux
	 * d'évaporation, en assurant l'évaporation de la quantité de phéromones au
	 * cours du temps. Le taux d'évaporation correspond à la valeur
	 * {@code PHEROMONE_EVAPORATION_RATE} du fichier de configuration initial.
	 * 
	 * @param dt : {@link Time}, pas de temps périodique de mise à jour de
	 *           l'instance {@code this} de {@link Pheromone}
	 */
	public void update  (Time dt) {
		double evaporationRate = Context.getConfig().getDouble(PHEROMONE_EVAPORATION_RATE);
		
		if (!isNegligible()) {
			quantity -= (dt.toSeconds() * evaporationRate);
			
			if (quantity < 0.0) {
				quantity = 0.0;
			}
		}
	}
}
