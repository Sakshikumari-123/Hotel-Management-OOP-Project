import java.io.*;
import java.util.*;

class Food implements Serializable {
    int itemNo;
    int quantity;
    double price;

    Food(int itemNo, int quantity) {
        this.itemNo = itemNo;
        this.quantity = quantity;

        switch (itemNo) {
            case 1: price = quantity * 50; break;
            case 2: price = quantity * 60; break;
            case 3: price = quantity * 70; break;
            case 4: price = quantity * 30; break;
            default: price = 0;
        }
    }
}

class SingleRoom implements Serializable {
    String name, contact, gender;
    ArrayList<Food> foodList = new ArrayList<>();

    SingleRoom(String name, String contact, String gender) {
        this.name = name;
        this.contact = contact;
        this.gender = gender;
    }
}

class DoubleRoom extends SingleRoom implements Serializable {
    String name2, contact2, gender2;

    DoubleRoom(String name, String contact, String gender,
               String name2, String contact2, String gender2) {
        super(name, contact, gender);
        this.name2 = name2;
        this.contact2 = contact2;
        this.gender2 = gender2;
    }
}

class RoomNotAvailable extends Exception {
    public String toString() {
        return "Room Not Available!";
    }
}

class HotelData implements Serializable {
    DoubleRoom luxuryDouble[] = new DoubleRoom[10];
    DoubleRoom deluxeDouble[] = new DoubleRoom[20];
    SingleRoom luxurySingle[] = new SingleRoom[10];
    SingleRoom deluxeSingle[] = new SingleRoom[20];
}

class Hotel {

    static HotelData data = new HotelData();
    static Scanner sc = new Scanner(System.in);

    static void bookRoom(int type, int roomNo) {
        try {
            switch (type) {
                case 1:
                    if (data.luxuryDouble[roomNo] != null)
                        throw new RoomNotAvailable();
                    data.luxuryDouble[roomNo] = createDoubleRoom();
                    break;

                case 2:
                    if (data.deluxeDouble[roomNo] != null)
                        throw new RoomNotAvailable();
                    data.deluxeDouble[roomNo] = createDoubleRoom();
                    break;

                case 3:
                    if (data.luxurySingle[roomNo] != null)
                        throw new RoomNotAvailable();
                    data.luxurySingle[roomNo] = createSingleRoom();
                    break;

                case 4:
                    if (data.deluxeSingle[roomNo] != null)
                        throw new RoomNotAvailable();
                    data.deluxeSingle[roomNo] = createSingleRoom();
                    break;
            }

            System.out.println("Room booked successfully!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static SingleRoom createSingleRoom() {
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Contact: ");
        String contact = sc.next();
        System.out.print("Enter Gender: ");
        String gender = sc.next();

        return new SingleRoom(name, contact, gender);
    }

    static DoubleRoom createDoubleRoom() {
        System.out.print("Enter First Guest Name: ");
        String name = sc.next();
        System.out.print("Enter Contact: ");
        String contact = sc.next();
        System.out.print("Enter Gender: ");
        String gender = sc.next();

        System.out.print("Enter Second Guest Name: ");
        String name2 = sc.next();
        System.out.print("Enter Contact: ");
        String contact2 = sc.next();
        System.out.print("Enter Gender: ");
        String gender2 = sc.next();

        return new DoubleRoom(name, contact, gender, name2, contact2, gender2);
    }

    static void orderFood(int type, int roomNo) {
        try {
            System.out.println("1.Sandwich 50");
            System.out.println("2.Pasta 60");
            System.out.println("3.Noodles 70");
            System.out.println("4.Coke 30");

            int item = sc.nextInt();
            System.out.print("Quantity: ");
            int qty = sc.nextInt();

            Food food = new Food(item, qty);

            switch (type) {
                case 1: data.luxuryDouble[roomNo].foodList.add(food); break;
                case 2: data.deluxeDouble[roomNo].foodList.add(food); break;
                case 3: data.luxurySingle[roomNo].foodList.add(food); break;
                case 4: data.deluxeSingle[roomNo].foodList.add(food); break;
            }

            System.out.println("Food ordered successfully!");

        } catch (Exception e) {
            System.out.println("Room not booked!");
        }
    }

    static void generateBill(int type, int roomNo) {
        double total = 0;

        switch (type) {
            case 1:
                total += 4000;
                for (Food f : data.luxuryDouble[roomNo].foodList)
                    total += f.price;
                break;

            case 2:
                total += 3000;
                for (Food f : data.deluxeDouble[roomNo].foodList)
                    total += f.price;
                break;

            case 3:
                total += 2200;
                for (Food f : data.luxurySingle[roomNo].foodList)
                    total += f.price;
                break;

            case 4:
                total += 1200;
                for (Food f : data.deluxeSingle[roomNo].foodList)
                    total += f.price;
                break;
        }

        System.out.println("Total Bill: " + total);
    }

    static void saveData() {
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream("hotel_backup.dat"));
            oos.writeObject(data);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    static void loadData() {
        try {
            File file = new File("hotel_backup.dat");
            if (file.exists()) {
                ObjectInputStream ois =
                        new ObjectInputStream(new FileInputStream(file));
                data = (HotelData) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }
}

public class HotelManagementSystem {

    public static void main(String[] args) {

        Hotel.loadData();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n1.Book Room");
            System.out.println("2.Order Food");
            System.out.println("3.Generate Bill");
            System.out.println("4.Exit");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Room Type (1-4): ");
                    int type = sc.nextInt();
                    System.out.print("Room Number (0-19): ");
                    int roomNo = sc.nextInt();
                    Hotel.bookRoom(type, roomNo);
                    break;

                case 2:
                    System.out.print("Room Type (1-4): ");
                    type = sc.nextInt();
                    System.out.print("Room Number: ");
                    roomNo = sc.nextInt();
                    Hotel.orderFood(type, roomNo);
                    break;

                case 3:
                    System.out.print("Room Type (1-4): ");
                    type = sc.nextInt();
                    System.out.print("Room Number: ");
                    roomNo = sc.nextInt();
                    Hotel.generateBill(type, roomNo);
                    break;

                case 4:
                    Hotel.saveData();
                    System.out.println("Data saved. Exiting...");
                    break;
            }

        } while (choice != 4);
    }
}
