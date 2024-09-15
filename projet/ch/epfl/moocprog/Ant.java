package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Vec2d;

/**
 * Classe abstraite représentant le concept d'une fourmi. Cette classe hérite de
 * la classe {@link Animal} et est héritée par les classes enfants
 * {@link AntWorker} et {@link AntSoldier}.
 */
public abstract class Ant extends Animal {

	/**
	 * Identifiant unique de la fourmillère d'ou provient l'instance {@code this} de
	 * {@link Ant}
	 */
	private Uid anthillID;

	/**
	 * Instance de {@link AntRotationProbabilityModel} représentant la probabilité
	 * de rotation lors d'un déplacement de l'instance {@code this} de {@link Ant}
	 */
	private AntRotationProbabilityModel probModel;
	
	/**
	 * Instance de {@link ToricPosition} représentant la dernière position connue de
	 * l'instance {@code this} de {@link Ant}; attribut exploité par la méthode
	 * {@link Ant#spreadPheromones(AntEnvironmentView)}
	 */
	private ToricPosition lastPos;

	/**
	 * Constructeur non vide initialisant une instance de {@link Ant} à partir d'une
	 * {@link Animal#ToricPosition} et de valeurs initiales pour les attributs
	 * {@link Animal#hitPoints} et {@link Animal#lifespan}. Ce constructeur fait
	 * appel en interne au constructeur
	 * {@link Animal#Animal(ToricPosition, int, Time)}. Il initialise également
	 * l'attribut {@link Ant#anthillID}. Le {@link Ant#probModel} est quant à lui
	 * instancié avec le constructeur vide
	 * {@link PheromoneRotationProbabilityModel#PheromoneRotationProbabilityModel()}.
	 * 
	 * @param position  : {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link Ant} dans l'environnement
	 * @param hitPoints : {@code int}, points de vie initiaux de la fourmie
	 * @param lifespan  : {@link Time}, durée de vie initiale de l'instance
	 *                  {@code this} de {@link Ant}
	 * @param anthillID : {@link Uid}, identifiant unique de l'instance {@code this}
	 *                  de {@link Ant}
	 */
	public Ant(ToricPosition position, int hitpoints, Time lifespan, Uid anthillID) {
		super(position, hitpoints, lifespan);
		this.anthillID = anthillID;
		lastPos = position;
		probModel = new PheromoneRotationProbabilityModel();
		
	}

	/**
	 * Constructeur non vide initialisant une instance de {@link Ant} à partir d'une
	 * {@link Animal#ToricPosition} et de valeurs initiales pour les attributs
	 * {@link Animal#hitPoints} et {@link Animal#lifespan}. Ce constructeur fait
	 * appel en interne au constructeur
	 * {@link Animal#Animal(ToricPosition, int, Time)}. Il initialise également les
	 * attributs {@link Ant#anthillID} et de {@link Ant#probModel}.
	 * 
	 * @param position: {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link Ant} dans l'environnement
	 * @param hitPoints : {@code int}, points de vie initiaux de la fourmie
	 * @param lifespan  : {@link Time}, durée de vie initiale de l'instance
	 *                  {@code this} de {@link Ant}
	 * @param anthillID : {@link Uid}, identifiant unique de l'instance {@code this}
	 *                  de {@link Ant}
	 * @param probModel : {@link AntRotationProbabilityModel}, probabilité de
	 *                  rotation de déplacement de l'instance {@code this} de
	 *                  {@link Ant}
	 */
	public Ant(ToricPosition position, int hitpoints, Time lifespan, Uid anthillID, AntRotationProbabilityModel probModel) {
		super(position, hitpoints, lifespan);
		this.anthillID = anthillID;
		this.probModel = probModel;
		lastPos = position;
		
	}
	
	/**
	 * Getter
	 * 
	 * @return {@link Ant#anthillID}, identifiant unique de la fourmillère d'où
	 *         provient l'instance {@code this} de {@link Ant}
	 */
	public final Uid getAnthillId() {
		return anthillID;
	}
	
	/**
	 * Méthode appelée après le déplacement de l'instance {@code this} de
	 * {@link Ant} pour propager les phéromones dans l'environnement via l'appel à
	 * {@link Ant#spreadPheromones(AntEnvironmentView)}
	 * 
	 * @param env : instance de {@link AntEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code env} de {@link AntEnvironmentView}
	 */
	protected final void afterMoveAnt (AntEnvironmentView env, Time dt) {
		spreadPheromones(env);
	}
	
	/**
	 * Méthode du "double-dispatch" appelée après le déplacement de l'instance
	 * {@code this} de {@link Ant}. Appelle la méthode appropriée dans
	 * l'environnement {@code env} en fonction du type de l'animal.
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code env} de {@link AnimalEnvironmentView}
	 */
	protected final void afterMoveDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectAfterMoveDispatch(this, dt);
	}
	
	/**
	 * Calcule les probabilités de rotation pour l'instance {@code this} de
	 * {@link Ant} en utilisant le modèle de probabilité de rotation.
	 * 
	 * @param env : instance de {@link AntEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * 
	 * @return les probabilités de rotation calculées, de type
	 *         {@link RotationProbability}.
	 */
	protected final RotationProbability computeRotationProbs (AntEnvironmentView env) {
		return probModel.computeRotationProbs(computeDefaultRotationProbs(), getPosition(), getDirection(), env);
	}
	
	/**
	 * Calcule les probabilités de rotation en utilisant le dispatch sur
	 * l'environnement pour obtenir les résultats appropriés pour le type spécifique
	 * d'animal (ici, l'instance {@code this} de {@link Ant})
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @return les probabilités de rotation calculées, de type
	 *         {@link RotationProbability}.
	 */
	protected final RotationProbability computeRotationProbsDispatch (AnimalEnvironmentView env) {
		// A ce moment là, on sait que l'on à affaire à un Ant
		// Grâce à l'appel suivant, on informe AnimalEnvironmentView de notre type !
				
		return env.selectComputeRotationProbsDispatch(this);/* ici le type de this est bien Ant*/
	}
	
	/**
	 * Méthode finale pour définir qui sont les ennemis vivants de l'instance
	 * {@code this} de {@link Ant} vivante, par l'appel à
	 * {@link Ant#isEnemyDispatch(Ant)} et {@link Ant#isEnemyDispatch(Termite))}
	 * 
	 * @param other : autre instance de {@link Animal} dont on doit vérifier si elle
	 *              est ennemie de l'instance {@code this}
	 * 
	 * @return {@code true} si {@code other} est ennemie de {@code this}, ou
	 *         {@code false} dans le cas inverse
	 * 
	 * @throws IllegalArgumentException si {@code other} est {@code null}
	 */
	@Override
	public final boolean isEnemy(Animal other) throws IllegalArgumentException {
		Utils.requireNonNull(other);
		return !this.isDead() && !other.isDead() && other.isEnemyDispatch(this);
	}
	
	/**
	 * Méthode définissant si une autre instance {code ant} de {@link Ant} est
	 * ennemie de l'instance {@code this} de {@link Ant}
	 * 
	 * @param: ant, instance de {@link Ant}
	 * 
	 * @return : {@code false} si les deux instances de {@link Ant} ont une
	 *         {@link Ant#anthillID} identique, et {@code true} dans le cas
	 *         contraire
	 */
	@Override
	protected boolean isEnemyDispatch(Ant ant) {
		return !this.getAnthillId().equals(ant.getAnthillId());
	}
	
	/**
	 * Méthode définissant si une instance {code termite} de {@link Termite} est
	 * ennemie de l'instance {@code this} de {@link Ant}
	 * 
	 * @param: termite, instance de {@link Termite}
	 * 
	 * @return : {@code true} systématiquement
	 */
	@Override
	protected boolean isEnemyDispatch(Termite termite) {
		return true;
	}
	
	/**
	 * Méthode finale qui propage les phéromones dans l'instance {@code env} de
	 * {@link AntEnvironmentView} en fonction du déplacement de l'instance
	 * {@code this} de {@link Ant}
	 * 
	 * @param env : instance de {@link AntEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 */
	private final void spreadPheromones(AntEnvironmentView env) {

		double densityPheromone = Context.getConfig().getDouble(Config.ANT_PHEROMONE_DENSITY);
		double initQuantityPheromones = Context.getConfig().getDouble(Config.ANT_PHEROMONE_ENERGY);

		double toricDistance = getPosition().toricDistance(lastPos);

		int numberOfPheromones = (int) Math.round(toricDistance * densityPheromone);

		// Vecteur de déplacement normalisé
		Vec2d direction = lastPos.toricVector(getPosition()).normalized();

		// Déplacement à chaque étape
		Vec2d step = direction.scalarProduct(toricDistance / numberOfPheromones);

		// Déposer les phéromones à intervalles réguliers
		for (int i = 0; i < numberOfPheromones; ++i) {
			lastPos = lastPos.add(step);
			env.addPheromone(new Pheromone(lastPos, initQuantityPheromones));
		}
	}
}
