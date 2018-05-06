package me.duzhi.demo.model;

public class ProxyModel {
    public interface RentOut {
        public void rentOut();
    }

    public static class HouseHolder implements RentOut {
        public void rentOut() {
            System.out.println("i'm in house");
        }
    }

    public static class Proxy implements RentOut {
        HouseHolder houseHolder;

        private void
        pre() {
            System.out.println("I need more money!");
        }

        private void
        after() {
            System.out.println("I will deduct some money!");
        }

        public void rentOut() {
            this.pre();
            if (null == houseHolder) {
                houseHolder = new HouseHolder();
            }
            houseHolder.rentOut();
            this.after();
        }
    }

    public static void main(String[] args) {
        RentOut rentOut = new Proxy();
        rentOut.rentOut();
        ;
    }
}
