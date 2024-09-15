package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.ANIMAL_LIFESPAN_DECREASE_FACTOR;
import static ch.epfl.moocprog.config.Config.ANIMAL_NEXT_ROTATION_DELAY;

import java.util.List;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Vec2d;

/**
 * Classe abstraite représentant le concept d'un animal. Cette classe hérite de
 * la classe {@link Positionable} et est héritée par les classes enfants
 * {@link Ant} et {@link Termite}.
 */
public abstract class Animal extends Positionable {
	/**
	 * Angle établi entre le vecteur Direction de l’animal (un vecteur l’orientant
	 * droit devant lui) par rapport à l’axe des x dans l'environnent. Cet angle est
	 * exprimé en radian car cette unité est utilisée par défaut par les fonctions
	 * trigonométriques de Java.
	 */
	private double angleDirection;
	/**
	 * Durée d'une attaque en cours. La valeur est initialisée à {@link Time#ZERO}
	 * lors de l'initialisation d'une instance de {@link Animal}
	 */
	private Time attackDuration;
	/**
	 * Temps écoulé avant la prochaine rotation de l'animal. La valeur est
	 * initialisée à {@link Time#ZERO} lors de l'initialisation d'une instance de
	 * {@link Animal}.
	 */
	private Time rotationDelay;
	/**
	 * L'état courant de l'animal provenant de {@link State} : IDLE (inactif),
	 * ESCAPING (en fuite) ou ATTACK (en attaque).
	 */
	private State state;
	/**
	 * Points de vie actuels de l'animal.
	 */
	private int hitPoints;
	/**
	 * Durée de vie restante de l'animal.
	 */
	private Time lifespan;
	
	/**
	 * État possible de l'animal : IDLE (inactif), ESCAPING (en fuite) ou ATTACK (en
	 * attaque).
	 */
	public enum State {IDLE, ESCAPING, ATTACK};
	
	/**
	 * Constructeur non vide initialisant une instance de {@link Animal} à partir
	 * d'une {@link ToricPosition}. Elle permet aussi de déclarer des valeurs
	 * initiales pour les attributs {@link Animal#angleDirection} initialisé à une
	 * valeur aléatoire tirée de manière uniforme entre 0 et 2*{@link Math#PI};
	 * {@link Animal#attackDuration} et {@link Animal#rotationDelay} initialisés à
	 * {@link Time#ZERO}; et {@link Animal#etat} initialisé à {@link State#IDLE}. Ce
	 * constructeur fait appel à {@link Positionable#Positionable(ToricPosition)}.
	 * 
	 * 
	 * @param position: {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link Animal} dans
	 *                  l'environnement
	 */
	public Animal(ToricPosition position) {
		super(position);
		angleDirection = UniformDistribution.getValue(0.0, (2 * Math.PI));
		attackDuration = Time.ZERO;
		rotationDelay = Time.ZERO;
		setState(State.IDLE);
	}

	/**
	 * Constructeur non vide initialisant une instance de {@link Animal} à partir
	 * d'une {@link ToricPosition} et de valeurs initiales pour les attributs
	 * {@link Animal#hitPoints} et {@link Animal#lifespan}. Ce constructeur fait
	 * appel en interne au constructeur
	 * {@link Animal#Animal(ToricPosition position)}.
	 * 
	 * @param position: {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link Animal} dans
	 *                  l'environnement
	 * @param hitPoints : int, points de vie initiaux de l'animal
	 * @param lifespan  : {@link Time}, durée de vie fixe de l'instance {@code this}
	 *                  de {@link Animal}
	 */
	public Animal(ToricPosition position, int hitPoints, Time lifespan) {
		this(position);
		this.lifespan = lifespan;
		this.hitPoints = hitPoints;
	}
	
	/**
	 * Getter final
	 * 
	 * @return angle en radian* établi entre le vecteur Direction de l’animal (un
	 *         vecteur l’orientant droit devant lui) par rapport à l’axe des x dans
	 *         l'environnent (* cet angle est exprimé en radian car cette unité est
	 *         utilisée par défaut par les fonctions trigonométriques de Java)
	 */
	public final double getDirection() {
		return angleDirection;
	}
	
	/**
	 * Setter final
	 * 
	 * @param angle : double, angle en radian* établi entre le vecteur Direction de
	 *              l’animal (un vecteur l’orientant droit devant lui) par rapport à
	 *              l’axe des x dans l'environnent (* cet angle est exprimé en
	 *              radian car cette unité est utilisée par défaut par les fonctions
	 *              trigonométriques de Java)
	 */
	public final void setDirection(double angle) {
		this.angleDirection = angle;
	}

	/**
	 * Getter final
	 * 
	 * @return points de vie actuels de l'instance {@code this} de {@link Animal}
	 * 
	 */
	public final int getHitpoints() {
		return hitPoints;
	}
	
	/**
	 * Setter
	 * 
	 * @param hitPoints : int, nouveaux points de vie à injecter dans l'instance
	 *                  {@code this} de {@link Animal}
	 * 
	 */
	public void setHitpoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}
	
	/**
	 * Getter final
	 * 
	 * @return {@link Time} représentant la durée de vie restante de l'instance
	 *         {@code this} de {@link Animal}
	 * 
	 */
	public final Time getLifespan() {
		return lifespan;
	}
	
	/**
	 * Getter final
	 * 
	 * @return l'état {@link State} de l'instance {@code this} de {@link Animal}
	 * 
	 */
	public final State getState() {
		return state;
	}
	
	/**
	 * Setter
	 * 
	 * @param newState : nouvel état {@link State} de l'instance {@code this} de
	 *                 {@link Animal}, qui ne peut prendre que 3 valeurs
	 *                 ({@link State#IDLE}, {@link State#ATTACK} et
	 *                 {@link State#ESCAPING}
	 */
	public void setState(State newState) {
		state = newState;
	}
	
	/**
	 * Méthode abstraite pour=r le rendu graphique d'une instance de {@link Animal}
	 * par JavaFX
	 * 
	 * @param visitor : {@link AnimalVisitor}, en charge du rendu
	 * @param s       : {@link RenderingMedia}, média de rendu utilisé par JavaFX
	 */
	public abstract void accept (AnimalVisitor visitor, RenderingMedia s);
	
	/**
	 * Fait pivoter l'instance {@code this} de {@link Animal} de 180 degrés.
	 */
	protected void animalTurnBack() {
		double angle = getDirection();

		angle += Math.PI;
		
		if (angle > (2 * Math.PI)) {
			angle -= (2 * Math.PI);
		}

		setDirection(angle);
	}
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir par
	 * "double dispatch" le comportement de l'instance {@code this} de
	 * {@link Animal} après un déplacement
	 *
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code env} de {@link AnimalEnvironmentView}
	 */
	protected abstract void afterMoveDispatch(AnimalEnvironmentView env, Time dt);

	/**
	 * Vérifie si l'animal peut encore attaquer.
	 *
	 * @return {@code true} si l'animal peut attaquer, {@code false} sinon.
	 */
	protected final boolean canAttack() {
		return (!(getState().equals(State.ESCAPING)) && (attackDuration.compareTo(getMaxAttackDuration()) <= 0));
	}
	
	/**
	 * Méthode retournant les tableaux par défaut des angles de rotation (en radian)
	 * et les probabilités inertielles associées, concernant le changement de
	 * direction de l'instance {@code this} de {@link Animal}. Les valeurs par
	 * défaut sont de {-180, -100, -55, -25, -10, 0, 10, 25, 55, 100, 180} pour les
	 * angles en degrés et de {0.0000, 0.0000, 0.0005, 0.0010, 0.0050, 0.9870,
	 * 0.0050, 0.0010, 0.0005, 0.0000, 0.0000} pour les probabilités intertielles
	 * associées.
	 * 
	 * @return une instance de {@link RotationProbability} intégrant les deux
	 *         tableaux définis des angles de rotation par défaut et des
	 *         probabilités associées
	 */
	protected final RotationProbability computeDefaultRotationProbs() {
		double [] angleDegres = {-180, -100, -55, -25, -10, 0, 10, 25, 55, 100, 180};
		double [] angleRadian = new double [angleDegres.length];
		
		// Conversion des angles de degrés en radians
		for (int i = 0; i < angleDegres.length; ++i) {
			angleRadian[i] = Math.toRadians(angleDegres[i]);
		}
		
		double [] probabilites = {0.0000, 0.0000, 0.0005, 0.0010, 0.0050, 0.9870, 0.0050, 0.0010, 0.0005, 0.0000, 0.0000};
		
		RotationProbability result = new RotationProbability(angleRadian, probabilites);
		
		return result;
	}
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir les
	 * probabilités de rotation spécifiques de l'instance {@code this} de
	 * {@link Animal}.
	 *
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @return {@link RotationProbability} contenant les probabilités de rotation
	 *         spécifiques.
	 */
	protected abstract RotationProbability computeRotationProbsDispatch (AnimalEnvironmentView env);
	
	/**
	 * Gère le comportement de fuite de l'instance {@code this} de {@link Animal}.
	 *
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code env} de {@link AnimalEnvironmentView}
	 */
	protected void escape (AnimalEnvironmentView env, Time dt) {
		move(env, dt);

			
		if(!env.isVisibleFromEnemies(this)) {
			setState(State.IDLE);
		}
	}
	
	/**
	 * Gère le comportement de combat de l'instance {@code this} de {@link Animal}.
	 *
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code env} de {@link AnimalEnvironmentView}
	 */
	protected void fight (AnimalEnvironmentView env, Time dt) {
		List <Animal> ennemies;
		
		ennemies = env.getVisibleEnemiesForAnimal(this);
		
		if (!ennemies.isEmpty()) {
			Animal nearestEnnemy = Utils.closestFromPoint(this, ennemies);
			
			nearestEnnemy.setState(State.ATTACK);
			
			if (!(getState().equals(State.ATTACK))) {
				setState(State.ATTACK);
			}
			
			int hit = (int) Math.round(UniformDistribution.getValue(getMinAttackStrength(), getMaxAttackStrength()));
			
			if (!(nearestEnnemy.isDead())){
				nearestEnnemy.setHitpoints(nearestEnnemy.getHitpoints() - hit);
			}
				
			attackDuration = attackDuration.plus(dt);
			
		} else {
			attackDuration = Time.ZERO;
					
			if (getState().equals(State.ATTACK)) {
				setState(State.ESCAPING);
			}
			
		}
	}
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir la
	 * force minimale d'attaque de l'instance {@code this} de {@link Animal}
	 * 
	 * @return la force minimale d'attaque de l'instance {@code this} de
	 *         {@link Animal}
	 */
	public abstract int getMinAttackStrength();
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir la
	 * force maximale d'attaque de l'instance {@code this} de {@link Animal}
	 * 
	 * @return la force maximale d'attaque de l'instance {@code this} de
	 *         {@link Animal}
	 */
	public abstract int getMaxAttackStrength();
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir la
	 * durée maximale d'attaque de l'instance {@code this} de {@link Animal}
	 * 
	 * @return la durée maximale d'attaque de l'instance {@code this} de
	 *         {@link Animal}
	 */
	public abstract Time getMaxAttackDuration();
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir la
	 * vitesse de déplacement de l'instance {@code this} de {@link Animal}
	 * 
	 * @return la vitesse de déplacement de l'instance {@code this} de
	 *         {@link Animal}
	 */
	public abstract double getSpeed();

	/**
	 * @return un booléen en fonction de si l'instance {@code this} de
	 *         {@link Animal} est en "vie" ({@code false}) ou "morte"
	 *         ({@code true}). L'instance est jugée "morte" si la quantité de vie
	 *         représentée par {@link Animal#hitPoints}, ou, si la durée de vie
	 *         représentée par {@link Animal#lifespan}, sont inférieures ou égales à
	 *         zéro.
	 */
	public final boolean isDead() {
		return (hitPoints <= 0 || lifespan.compareTo(Time.ZERO) <= 0);
	}
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir par
	 * "double dispatch" qui sont les ennemis de l'instance {@code this} de
	 * {@link Animal}
	 * 
	 * @param other : autre instance de {@link Animal} dont on doit vérifier si elle
	 *              est ennemie de l'instance {@code this}
	 * @return {@code true} si {@code other} est ennemie de {@code this}, ou
	 *         {@code false} dans le cas inverse
	 */
	public abstract boolean isEnemy (Animal other);
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir par
	 * "double dispatch" qui sont les ennemis de l'instance {@code this} de
	 * {@link Animal}
	 * 
	 * @param other : instance de {@link Ant} dont on doit vérifier si elle est
	 *              ennemie de l'instance {@code this}
	 * @return {@code true} si {@code other} est ennemie de {@code this}, ou
	 *         {@code false} dans le cas inverse
	 */
	protected abstract boolean isEnemyDispatch (Ant other);
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir par
	 * "double dispatch" qui sont les ennemis de l'instance {@code this} de
	 * {@link Animal}
	 * 
	 * @param other : instance de {@link Termite} dont on doit vérifier si elle est
	 *              ennemie de l'instance {@code this}
	 * @return {@code true} si {@code other} est ennemie de {@code this}, ou
	 *         {@code false} dans le cas inverse
	 */
	protected abstract boolean isEnemyDispatch (Termite other);
	
	/**
	 * Méthode permettant à l'instance {@code this} de {@link Animal} de changer sa
	 * {@link ToricPosition} de {@code dt} fois sa vitesse obtenue via
	 * {@link Animal#getSpeed()}. Elle prendra en compte et intégrera la notion de
	 * rotation de vue de direction, conduisant à la conservation ou non de la
	 * direction de déplacement de l'instance {@code this} de {@link Animal}.
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code env} de {@link AnimalEnvironmentView}
	 */
	protected final void move (AnimalEnvironmentView env,Time dt) {
		
		double temps = dt.toSeconds();
		double vitesse = this.getSpeed();
		
		// Calcul d'un changement éventuel de direction de déplacement
		rotate(env, dt);
		
		// Calcul du vecteur Vec2d correspondant au déplacement de l'animal
		Vec2d vecteurAngle = Vec2d.fromAngle(this.getDirection());
		vecteurAngle = vecteurAngle.scalarProduct(temps * vitesse);
		
		// Calcul d'une nouvelle position correspondant à l'ajout de ce vecteur Vec2d
		// "déplacement" à la position actuelle de l'animal
		ToricPosition newPosition = (this.getPosition()).add(vecteurAngle);
		
		// Mise à jour de la position de l'animal
		this.setPosition(newPosition);
		
		afterMoveDispatch(env, dt);
	}
	
	/**
	 * Méthode en charge de faire évoluer {@link Animal#angleDirection} de
	 * l'instance {@code this} de {@link Animal} à chaque pas de temps {@code dt} de
	 * {@link Time}. Elle s'appuie sur une instance de {@link RotationProbability}
	 * afin de sélectionner un angle de rotation lié à une probabilité inertielle,
	 * pour au final l'ajouter à {@link Animal#angleDirection}.
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link Environment}
	 */
	private void rotate(AnimalEnvironmentView env, Time dt) {
		Time delay = Context.getConfig().getTime(ANIMAL_NEXT_ROTATION_DELAY);
		
		rotationDelay = rotationDelay.plus(dt);
		
		while (rotationDelay.compareTo(delay) >= 0) {
			
			rotationDelay = rotationDelay.minus(delay);
			
			RotationProbability reference = this.computeRotationProbsDispatch(env);
			
			double newAngleDirection = this.getDirection();
			newAngleDirection += Utils.pickValue(reference.getAngles(), reference.getProbabilities());
			this.setDirection(newAngleDirection);
		}
	}
	
	/**
	 * Méthode abstraite à implémenter par les classes dérivées pour définir le
	 * comportement spécifique par "double dispatch" de l'instance {@code this} de
	 * {@link Animal}
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link AnimalEnvironmentView}
	 */
	protected abstract void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt);
	
	/**
	 * @return une représentation en String de l'instance {@code this} de
	 *         {@link Animal}
	 */
	@Override
	public String toString() {
		String s = super.toString();
		s += "\n";
		s += String.format("Speed :  %.1f\n", getSpeed()); 
		s += "HitPoints : " + hitPoints + "\n";
		s += "LifeSpan : " + lifespan + "\n";
		s += "State : " + state + "\n";
		return s;
		
	}
	
	/**
	 * Méthode en charge de faire évoluer l'instance {@code this} de {@link Animal}
	 * dans l'instance {@code env} de {@link AnimalEnvironmentView} à chaque pas de
	 * temps {@code dt} de {@link Time}. Elle assure ainsi 1) la diminution de la
	 * durée de vie de {@code this} via son attribut {@link Animal#lifespan} et 2)
	 * la gestion de son comportement en fonction de la valeur de son attribut
	 * {@link Animal#state}.
	 * 
	 * @param env : instance de {@link Environment} en charge d'appeler et d'être
	 *            exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link Environment}
	 */
	public final void update(AnimalEnvironmentView env, Time dt) {
		double factor = Context.getConfig().getDouble(ANIMAL_LIFESPAN_DECREASE_FACTOR);
		
		lifespan = lifespan.minus(dt.times(factor));
		
		if (isDead()) {
			return;
		}
		
		if (getState().equals(State.ATTACK)) {
			if (canAttack()) {
				fight(env, dt);
			} else {
				setState(State.ESCAPING);
				attackDuration = Time.ZERO;
			}
		} else if (getState().equals(State.ESCAPING)) {
			escape(env, dt);
		} else {
			specificBehaviorDispatch(env, dt);
		}
		
	}
}