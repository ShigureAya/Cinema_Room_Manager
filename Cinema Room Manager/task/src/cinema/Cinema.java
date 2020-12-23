package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    private final Scanner scanner = new Scanner(System.in);
    private final String[][] seats;

    public Cinema() {
        System.out.println("Enter the number of rows:");
        int row = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int column = scanner.nextInt();
        seats = new String[row][column];
        for (String[] strings : seats) {
            Arrays.fill(strings, "S");
        }
    }

    public static void main(String[] args) {

        Cinema cinema = new Cinema();
        while (true) {
            if (!cinema.action()) break;
        }
    }

    public boolean action() {
        System.out.println("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");
        int actionNumber = scanner.nextInt();

        switch (actionNumber) {
            case 1:
                showSeat();
                break;
            case 2:
                buyTicket();
                break;
            case 3:
                statistics();
                break;
            case 0:
            default:
                return false;
        }
        return true;
    }

    private void statistics() {
        int row = getRow();
        int column = getColumn();
        final int seatNumber = row * column;
        long purchasedTickets = 0;
        int income = 0;
        for (int i = 0; i < seats.length; i++) {
            long count = Arrays.stream(seats[i]).filter("B"::equals).count();
            purchasedTickets += count;
            if (count > 0) {

                income += getPrice(i + 1) * count;
            }
        }
        double purchasedPercentage = (double) purchasedTickets / seatNumber * 100;
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.println("Percentage: " + String.format("%.2f", purchasedPercentage) + "%");
        System.out.println("Current income: $" + income);
        System.out.println("Total income: $" + getTotalIncome());
    }

    private void buyTicket() {
        while (true) {

            System.out.println("Enter a row number:");
            int selectRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int selectColumn = scanner.nextInt();

            if (selectRow <= 0 || selectRow > getRow() || selectColumn <= 0 || selectColumn > getColumn()) {
                System.out.println("Wrong input!");
                continue;
            }

            if ("B".equals(seats[selectRow - 1][selectColumn - 1])) {
                System.out.println("That ticket has already been purchased!");
                continue;
            }
            int price = getPrice(selectRow);

            System.out.printf("Ticket price: $%d\n", price);
            seats[selectRow - 1][selectColumn - 1] = "B";
            break;
        }

    }

    private int getTotalIncome() {
        int row = getRow();
        int column = getColumn();
        final int seatNumber = row * column;
        int total;
        if (seatNumber <= 60) {
            total = seatNumber * 10;
        } else {
            int front = row / 2;
            total = front * column * 10;
            total += (row - front) * column * 8;
        }
        return total;
    }

    private int getPrice(int selectRow) {
        int row = getRow();
        int column = getColumn();
        final int seatNumber = row * column;
        int price;
        if (seatNumber <= 60) {
            price = 10;
        } else {
            int front = row / 2;
            if (selectRow <= front) {
                price = 10;
            } else {
                price = 8;
            }
        }
        return price;
    }

    private int getColumn() {
        return seats[0].length;
    }

    private int getRow() {
        return seats.length;
    }

    private void showSeat() {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 1; i <= getColumn(); i++) {

            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < getRow(); i++) {
            System.out.println((i + 1) + " " + String.join(" ", seats[i]));
        }
    }
}
