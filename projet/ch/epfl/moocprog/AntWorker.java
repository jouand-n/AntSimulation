package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.ANT_MAX_FOOD;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_ATTACK_DURATION;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_HP;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_LIFESPAN;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_MAX_STRENGTH;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_MIN_STRENGTH;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_SPEED;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.utils.Time;

/**
 * Classe finale représentant le concept d'une fourmi ouvrière. Cette classe
 * hérite de la classe {@link Ant}.
 */
public final class AntWorker extends Ant{

	/**
	 * Quantité de nourriture transportée par l'instance {@code this} de
	 * {@link AntWorker}
	 */
	private double foodQuantity;
	
	/**
	 * Constructeur non vide initialisant une instance de {@link AntWorker} à partir
	 * d'une {@link Animal#ToricPosition} et d'une {@link Ant#anthillID}. Ce
	 * constructeur fait appel en interne au constructeur
	 * {@link Ant#Ant(ToricPosition, int, Time, Uid)}. Il initialise respectivement
	 * les attributs {@link Animal#hitpoints} et {@link Animal#lifespan} avec les
	 * valeurs initiales constantes {@code ANT_WORKER_HP} et
	 * {@code ANT_WORKER_LIFESPAN} obtenues à partir du fichier de configuration
	 * initial, ainsi que l'attribut {@link AntWorker#foodQuantity} à zéro.
	 * 
	 * @param position  : {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link AntWorker} dans
	 *                  l'environnement
	 * @param anthillID : {@link Uid}, identifiant unique de l'instance {@code this}
	 *                  de {@link AntWorker}
	 */
	public AntWorker (ToricPosition position, Uid anthillID) {
		super(position, Context.getConfig().getInt(ANT_WORKER_HP), Context.getConfig().getTime(ANT_WORKER_LIFESPAN), anthillID);
		foodQuantity = 0.0;
	}
	
	/**
	 * Constructeur non vide initialisant une instance de {@link AntWorker} à partir
	 * d'une {@link Animal#ToricPosition} et de valeurs initiales pour les attributs
	 * {@link Ant#anthillID} et de {@link Ant#probModel}. Ce constructeur fait appel
	 * en interne au constructeur
	 * {@link Ant#Ant(ToricPosition, int, Time, Uid, AntRotationProbabilityModel)}.
	 * Il initialise respectivement les attributs {@link Animal#hitpoints} et
	 * {@link Animal#lifespan} avec les valeurs initiales constantes
	 * {@code ANT_WORKER_HP} et {@code ANT_WORKER_LIFESPAN} obtenues à partir du
	 * fichier de configuration initial, ainsi que l'attribut
	 * {@link AntWorker#foodQuantity} à zéro.
	 * 
	 * @param position  : {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link AntWorker} dans
	 *                  l'environnement
	 * @param anthillID : {@link Uid}, identifiant unique de l'instance {@code this}
	 *                  de {@link AntWorker}
	 * @param probModel : {@link AntRotationProbabilityModel} représentant la
	 *                  probabilité de rotation lors d'un déplacement de l'instance
	 *                  {@code this} de {@link AntWorker}
	 */
	public AntWorker (ToricPosition position, Uid anthillID, AntRotationProbabilityModel probModel) {
		super(position, Context.getConfig().getInt(ANT_WORKER_HP), Context.getConfig().getTime(ANT_WORKER_LIFESPAN), anthillID, probModel);
		foodQuantity = 0.0;
	}
	
	/**
	 * Méthode redéfinie permettant de gérer la représentation graphique par JavaFX
	 * d'une {@link AntWorker} en garantissant que le {@link RenderingMedia} est
	 * bien visité par l'instance {@code this} de {@link AntWorker} grâce à l'appel
	 * de la méthode {@link visit()}
	 * 
	 * @param visitor : {@link AnimalVisitor} permet de garantir le type dynamique
	 *                de l'instance de {@link Animal} pour {@code s}
	 * @param s       : {@link RenderingMedia} le support de rendu
	 */
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this,  s);
	}
	
	/**
	 * Getter
	 * 
	 * @return {@link AntWorker#foodQuantity}
	 */
	public double getFoodQuantity() {
		return foodQuantity;
	}
	
	/**
	 * Méthode rédéfinie pour obtenir la force minimale d'attaque de l'instance
	 * {@code this} de {@link AntWorker} définie d'après la valeur constante
	 * {@code ANT_WORKER_MIN_STRENGTH} du fichier de configuration initial
	 * 
	 * @return la force minimale d'attaque de l'instance {@code this} de
	 *         {@link AntWorker}
	 */
	@Override
	public int getMinAttackStrength() {
		return Context.getConfig().getInt(ANT_WORKER_MIN_STRENGTH);
	}
	
	/**
	 * Méthode rédéfinie pour obtenir la force maximale d'attaque de l'instance
	 * {@code this} de {@link AntWorker} définie d'après la valeur constante
	 * {@code ANT_WORKER_MAX_STRENGTH} du fichier de configuration initial
	 * 
	 * @return la force maximale d'attaque de l'instance {@code this} de
	 *         {@link AntWorker}
	 */
	@Override
	public int getMaxAttackStrength() {
		return Context.getConfig().getInt(ANT_WORKER_MAX_STRENGTH);
	}
	
	/**
	 * Méthode rédéfinie pour obtenir la durée maximale d'attaque de l'instance
	 * {@code this} de {@link AntWorker} définie d'après la valeur constante
	 * {@code ANT_WORKER_ATTACK_DURATION} du fichier de configuration initial
	 * 
	 * @return la durée maximale d'attaque de l'instance {@code this} de
	 *         {@link AntWorker}
	 */
	@Override
	public Time getMaxAttackDuration(){
		return Context.getConfig().getTime(ANT_WORKER_ATTACK_DURATION);
	}
	
	/**
	 * Méthode redéfinie renvoyant la vitesse de l'instance {code this} de
	 * {@link AntWorker},obtenue à partir du fichier de configuration initial au
	 * niveau de la valeur constante {@code ANT_WORKER_SPEED}
	 */
	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(ANT_WORKER_SPEED);
	}
	
	/**
	 * Setter
	 * 
	 * @param aFoodQuantity : double, nouvelle quantité de nourriture à injecter
	 *                      dans l'instance {@code this} de {@link AntWorker}
	 * 
	 */
	public void setFoodQuantity(double aFoodQuantity) {
		foodQuantity = aFoodQuantity;
	}
	
	/**
	 * Méthode permettant à l'instance {@code this} de {@link AntWorker} de bouger
	 * (via l'appel à {@link AntWorker#move(AnimalEnvironmentView, Time)}), puis de
	 * chercher si il existe de la nourriture proche de {@code this} (via l'appel à
	 * {@link AntWorkerEnvironmentView#getClosestFoodForAnt(AntWorker)}. Si
	 * l'instance {@code this} ne porte pas de nourriture et trouve une source de
	 * nourriture, alors elle se charge en nourriture à hauteur de la valeur
	 * constante {@code ANT_MAX_FOOD} provenant du fichier de configuration initial.
	 * Si l'instance {@code this} porte de la nourriture et se trouve à proximité de
	 * sa fourmilière de rattachement, alors elle y dépose la totalité de son stock
	 * de nourriture. Dans ces 2 cas - et uniquement ces 2 cas, l'instance
	 * {@code this} fera demi-tour via l'appel à {@link AntWorker#animalTurnBack()}.
	 * 
	 * @param env : instance de {@link AntWorkerEnvironmentView} en charge d'appeler
	 *            et d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link AntWorkerEnvironmentView}
	 */
	protected void seekForFood(AntWorkerEnvironmentView env, Time dt) {
		
		double maxFood = Context.getConfig().getDouble(ANT_MAX_FOOD);
		
		move(env, dt);
		
		Food nearestFood = env.getClosestFoodForAnt(this);
		
		if ((getFoodQuantity() == 0.0) && (nearestFood != null)) {
			double aQuantity = nearestFood.takeQuantity(maxFood);
			setFoodQuantity(aQuantity);
			animalTurnBack();
		}
		
		if ((getFoodQuantity() > 0.0) && (env.dropFood(this) == true)) {
			setFoodQuantity(0.0);
			animalTurnBack();
		}
	}
	
	/**
	 * Méthode permettant d'assurer un "double dispatch" - elle permet à
	 * l'environnement lors de sa mise à jour d'appeler spécifiquement des méthodes
	 * liées à l'instance {@code this} de {@link AntWorker}
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link AnimalEnvironmentView}
	 */
	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// A ce moment là, on sait que l'on à affaire à un AntWorker.
		// Grâce à l'appel suivant, on informe AnimalEnvironmentView de notre type !
		
		env.selectSpecificBehaviorDispatch(this /* ici le type de this est bien AntWorker ! */, dt);
	}
	
	/**
	 * @return une représentation en String de l'instance {@code this} de
	 *         {@link AntWorker}
	 */
	@Override
	public String toString() {
		String s = super.toString();
		s += String.format("Quantity : %.2f\n", this.getFoodQuantity());
		return s;
	}
}
