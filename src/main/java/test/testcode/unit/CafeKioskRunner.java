package test.testcode.unit;

import test.testcode.unit.Beverage.Americano;
import test.testcode.unit.Beverage.Latte;

public class CafeKioskRunner  {
    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(),2);
        System.out.println(">>> 아메리카노 추가");

        cafeKiosk.add(new Latte(),1);
        System.out.println(">>> 라떼 추가");

        int totalPrice = cafeKiosk.calculateTotalPrice();
        System.out.println("총 주문 가격: " + totalPrice);
    }
}
