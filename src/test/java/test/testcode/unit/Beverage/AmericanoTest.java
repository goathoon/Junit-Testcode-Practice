package test.testcode.unit.Beverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {
    @Test
    void getName(){
        Americano americano = new Americano();
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }
    @Test
    void getPrice(){
        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}