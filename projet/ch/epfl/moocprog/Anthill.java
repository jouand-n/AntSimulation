package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.ANTHILL_SPAWN_DELAY;
import static ch.epfl.moocprog.config.Config.ANTHILL_WORKER_PROB_DEFAULT;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

/**
 * Classe finale représentant le concept d'une fourmilière. Cette classe hérite
 * de la classe {@link Positionable}.
 */
public final class Anthill extends Positionable {

	/**
	 * Quantité de nourriture actuellement stockée dans l'instance {@code this} de
	 * {@link Anthill}
	 */
	private double foodQuantity;
	/**
	 * {@link Uid} unique de l'instance {@code this} de {@link Anthill}
	 */
	private Uid anthillID;

	/**
	 * Probabilité de génération d'une nouvelle instance de fourmi ouvrière
	 * {@link AntWorker} par l'instance {@code this} de fourmilière {@link Anthill}
	 */
	private double antWorkerExitProb;

	/**
	 * Temps écoulé entre deux générations d'une nouvelle instance de fourmi
	 * (ouvrière {@link AntWorker} ou soldate {@link AntSoldier}) par l'instance
	 * {@code this} de fourmilière {@link Anthill}
	 */
	private Time generationAntDelay ;
	
	/**
	 * Constructeur non vide initialisant une instance de {@link Anthill} à partir
	 * d'une {@link ToricPosition}. Il permet aussi de déclarer des valeurs
	 * initiales pour les attributs {@code foodQuantity} (0.0), {@code anthillID}
	 * (créé par {@link Uid#createUid()}), {@code antWorkerExitProb} (extrait de la
	 * valeur {@code ANTHILL_WORKER_PROB_DEFAULT} présente dans la configuration) et
	 * {@code generationAntDelay} ({@link Time#ZERO}).
	 * 
	 * @param position: {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de la fourmilière {@link Anthill}
	 */
	public Anthill(ToricPosition position) {
		super(position);
		foodQuantity = 0.0;
		anthillID = Uid.createUid();
		antWorkerExitProb = Context.getConfig().getDouble(ANTHILL_WORKER_PROB_DEFAULT);
		generationAntDelay = Time.ZERO;
	}
	
	/**
	 * Constructeur non vide initialisant une instance de {@link Anthill} à partir
	 * d'une {@link ToricPosition} et d'une valeur initiale de
	 * {@code antWorkerExitProb}. Il appelle le constructeur non vide
	 * {@link Anthill#Anthill(ToricPosition)}. Il permet ainsi de déclarer une
	 * valeur initiale de {@code antWorkerExitProb} au lieu de la valeur
	 * {@code ANTHILL_WORKER_PROB_DEFAULT} présente dans la configuration).
	 * 
	 * @param position:          {@link ToricPosition}, position torique initiale de
	 *                           l'instance {@code this} de la fourmilière
	 *                           {@link Anthill} {@link Anthill} dans
	 *                           l'environnement
	 * @param antWorkerExitProb: {@code double}, probabilité de génération d'une
	 *                           nouvelle instance de fourmi ouvrière
	 *                           {@link AntWorker} par l'instance {@code this} de
	 *                           fourmilière {@link Anthill}
	 */
	public Anthill(ToricPosition position, double antWorkerExitProb) {
		this(position);
		this.antWorkerExitProb = antWorkerExitProb;
	}

	/**
	 * Getter
	 * 
	 * @return l'identifiant de la fourmilière {@link Anthill#anthillID}
	 */
	public Uid getAnthillId() {
		return anthillID;
	}

	/**
	 * Getter - Obtient la probabilité de génération d'une nouvelle instance de
	 * fourmi ouvrière par la fourmilière.
	 * 
	 * @return la probabilité de génération d'une fourmi ouvrière
	 *         {@link Anthill#antWorkerExitProb}
	 */
	public double getAntWorkerExitProb() {
		return antWorkerExitProb;
	}
	
	/**
	 * Getter
	 * 
	 * @return la quantité de nourriture actuellement stockée
	 *         {@link Anthill#foodQuantity}
	 */
	public double getFoodQuantity() {
		return foodQuantity;
	}
	
	/**
	 * Méthode permettant à l'instance {@code this} de {@link Anthill} de recevoir
	 * et stocker dans {@link Anthill#foodQuantity} une quantité de nourriture
	 * laissée par une {@link AntWorker}
	 * 
	 * @param toDrop : {@code double}, quantité de nourriture récupérée depuis une
	 *               {@link AntWorker}
	 * @throws IllegalArgumentException : exception lancée si {@code toDrop} est
	 *                                  strictement inférieure à zéro
	 */
	public void dropFood(double toDrop) throws IllegalArgumentException {
		Utils.require(toDrop >= 0.0);
		foodQuantity += toDrop;
	}
	
	/**
	 * @return une représentation en String de l'instance {@code this} de
	 *         {@link Anthill}
	 */
	public String toString() {
		String s = super.toString();
		s += "\n";
		s += String.format("Quantity : %.2f\n", getFoodQuantity());
		return s;
	}
	
	/**
	 * Méthode en charge d'ajouter une ou plusieurs instances de {@link Ant} dans
	 * l'instance {@code env} de {@link AnthillEnvironmentView} à chaque pas de
	 * temps {@code dt} de {@link Time}. Elle s'appuie sur l'usage d'une valeur
	 * constante {@code ANTHILL_SPAWN_DELAY} issue du fichier de configuration
	 * initial. Elle repose aussi sur {@link Anthill#antWorkerExitProb} pour
	 * déterminer si elle va générer une instance de {@link AntWorker} ou plutôt de
	 * {@link AntSoldier}.
	 * 
	 * @param env : instance de {@link AnthillEnvironmentView} en charge d'appeler
	 *            et d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link AnthillEnvironmentView}
	 */
	public void update(AnthillEnvironmentView env, Time dt) {
		
		Time delay = Context.getConfig().getTime(ANTHILL_SPAWN_DELAY);
		
		generationAntDelay = generationAntDelay.plus(dt);
		
		while (generationAntDelay.compareTo(delay) >= 0) {
			
			generationAntDelay = generationAntDelay.minus(delay);
			
			double randomValue = UniformDistribution.getValue(0.0,  1.0);
			
			if (randomValue <= this.getAntWorkerExitProb()) {
				env.addAnt(new AntWorker(this.getPosition(), this.getAnthillId()));
			} else {
				env.addAnt(new AntSoldier(this.getPosition(), this.getAnthillId()));
			}
		} 
	}
}
