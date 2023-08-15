package test.testcode.unit;

import org.junit.jupiter.api.Test;
import test.testcode.unit.Beverage.Americano;
import test.testcode.unit.Beverage.Latte;
import test.testcode.unit.Order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {

    @Test
    void addManualTest(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(),2);

        System.out.println(">>> 담긴 음료 수:"+cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료:"+cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    void addAutomaticTest(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(),2);

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }
    @Test
    void addSeveralBeverages(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano,2);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }
    @Test
    void addZeroBeverages(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        assertThatThrownBy(()-> cafeKiosk.add(americano,0))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 구매할 수 있습니다.");
    }
    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano,2);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafeKiosk.add(americano,2);
        cafeKiosk.add(latte,2);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }
//    @Test
//    void createOrder(){
//        //cafe kiosk 인스턴스 생성후 해당 인스턴스 변수인 beverages에 저장되어있음.
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        Americano americano = new Americano();
//        cafeKiosk.add(americano,1);
//
//        Order order = cafeKiosk.createOrder();
//        assertThat(order.getBeverages()).hasSize(1);
//        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
//
//    }

    @Test
    void createOrderWithCurrentTime(){
        //cafe kiosk 인스턴스 생성후 해당 인스턴스 변수인 beverages에 저장되어있음.
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano,1);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,8,15,10,0));
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

    /*
     현재의 주문 일시는 테스트하기 어려운 영역임.
     전체가 테스트 불가능한 상황 -> 테스트 수행할 때마다 달라지므로,
     주문 일시를 외부에서 파라미터로 분리.
     */
    @Test
    void createOrderOutsideOpenTime(){
        //cafe kiosk 인스턴스 생성후 해당 인스턴스 변수인 beverages에 저장되어있음.
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano,1);

        assertThatThrownBy(()->cafeKiosk.createOrder(LocalDateTime.of(2023,8,15,9,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요");

    }

}