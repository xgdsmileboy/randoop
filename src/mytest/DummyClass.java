package mytest;

public class DummyClass {
  private int a;
  private int b;

  public DummyClass(int _a, int _b) {
    a = _a;
    b = _b;
  }

  public void setSquare(int _a, int _b) {
    int t1 = _a, t2 = _b;
    int t = 0;
    int tmp[] = new int[5];
    tmp[4] = 10;
    System.out.println("$$t1 < 1 && t2 > 2");
    if (t1 < 1 && t2 > 2) {
      t = t1 + t2;
    }
    System.out.println("$$t1 < 3 || t2 < 4");
    if (t1 < 3 || t2 < 4) {
      t = -(t1 + t2);
    }
    System.out.println("$$t < 5");
    while (t < 5) {
      t++;
    }
    System.out.println("$$t > 6");
    do {
      t--;
    } while (t > 6);
    System.out.println("$$t1");
    switch (t1) {
      case 0:
        System.out.println(t1);
        break;
      case 1:
      default:
        System.out.println("error");
    }
    System.out.println("$$_a < 7");
    if (_a < 7) {
      _a = 0;
    }
    System.out.println("$$_b < 8");
    if (_b < 8) {
      _b = 0;
    }
    a = _a;
    b = _b;
  }

  public int getArea() {
    return a * b;
  }
}
