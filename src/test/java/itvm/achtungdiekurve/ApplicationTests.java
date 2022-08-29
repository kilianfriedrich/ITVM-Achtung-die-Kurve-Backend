package itvm.achtungdiekurve;

import itvm.achtungdiekurve.model.Kurve;
import itvm.achtungdiekurve.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void collision_between_two_curves(){
		Kurve k1 = new Kurve(null,1, Color.red);
		Kurve k2 = new Kurve(null,2,Color.CYAN);

		ArrayList<Kurve> kurven = new ArrayList<Kurve>();

		kurven.add(k1);
		kurven.add(k2);

		Kurve k_dec = null;


		for(int i = 1; i < 999; i++) {
			k1.addPoint(new Point(250, 999 - i));
		}

		for(int i = 0; i < 999 ;i++) {
			Point p = new Point(999 - i, i);

			k2.addPoint(p);

			if (i > 2) k_dec = Utils.detectCollsion(kurven);
		}

		assertEquals(k2,k_dec);
	}

	@Test
	void collision_sides(){
		Kurve k2 = new Kurve(null,2,Color.CYAN);

		ArrayList<Kurve> kurven = new ArrayList<Kurve>();

		kurven.add(k2);

		Kurve k_dec = null;

		for(int i = 0; i < 1005 ;i++) {
			Point p = new Point(999 - i, 250);

			k2.addPoint(p);

			if (i > 2) k_dec = Utils.detectCollsion(kurven);
		}

		assertEquals(k2,k_dec);
	}

	@Test
	void collision_top(){
		Kurve k2 = new Kurve(null,2,Color.CYAN);

		ArrayList<Kurve> kurven = new ArrayList<Kurve>();

		kurven.add(k2);

		Kurve k_dec = null;

		for(int i = 0; i < 1005 ;i++) {
			Point p = new Point(250, 999 - i);

			k2.addPoint(p);

			if (i > 2) k_dec = Utils.detectCollsion(kurven);
		}

		assertEquals(k2,k_dec);
	}
}
