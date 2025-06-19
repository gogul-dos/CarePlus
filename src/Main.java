import base.Base;
import careplus.CarePlus;

public class Main {
    public static void main(String[] args) {
        Base.fetchDetails();
        new CarePlus().init();
    }
}