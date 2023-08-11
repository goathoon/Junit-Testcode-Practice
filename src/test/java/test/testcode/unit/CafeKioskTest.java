package test.testcode.unit;

import org.junit.jupiter.api.Test;
import test.testcode.unit.Beverage.Americano;
import test.testcode.unit.Beverage.Latte;

import static org.assertj.core.api.Assertions.assertThat;
class CafeKioskTest {

    @Test
    void addManualTest(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수:"+cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료:"+cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    void addAutomaticTest(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }
    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }
}