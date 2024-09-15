package ch.epfl.moocprog.tests;

import ch.epfl.moocprog.Animal;
import ch.epfl.moocprog.Animal.State;

class MyPersonalTests{

	public static void main (String[] args) {
		double[] angleDegres = { -180, -100, -55, -25, -10, 0, 10, 25, 55, 100, 180 };
		double[] angleRadian = new double[angleDegres.length];
		double[] directAngleRadian = { Math.toRadians(-180), Math.toRadians(-100), Math.toRadians(-55),
				Math.toRadians(-25), Math.toRadians(-10), Math.toRadians(0), Math.toRadians(10), Math.toRadians(25),
				Math.toRadians(55), Math.toRadians(100), Math.toRadians(180) };

		for (int i = 0; i < angleDegres.length; ++i) {
			angleRadian[i] = Math.toRadians(angleDegres[i]);
			System.out.print(angleDegres[i] + ", " + angleRadian[i] + " // " + directAngleRadian[i] + " ");

			if (angleRadian[i] == directAngleRadian[i]) {
				System.out.println(true);
			} else {
				System.out.println(false);
			}
		}
		
		Animal.State etat = State.ESCAPING;
		
		System.out.println(etat);
		
		etat = State.ATTACK;
		
		System.out.println(etat);
		
	}
}