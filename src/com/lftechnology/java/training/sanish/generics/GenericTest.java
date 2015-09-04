package com.lftechnology.java.training.sanish.generics;

import com.lftechnology.java.training.sanish.generics.product.Computer;
import com.lftechnology.java.training.sanish.generics.product.Mouse;
import com.lftechnology.java.training.sanish.generics.product.Product;
import com.lftechnology.java.training.sanish.generics.vehicle.Car;
import com.lftechnology.java.training.sanish.generics.vehicle.VehicleList;
import com.lftechnology.java.training.sanish.generics.vehicle.VehicleListImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic class, interface & function test
 *
 * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
 */
public class GenericTest {
    private static final Logger LOGGER = Logger.getLogger(VehicleListImpl.class.getName());

    public static void main(String[] args) {
        // add two number using generic function
        addIntegerNumbers(2, 5);
        addDoubleNumbers(3.5, 7.75);

        // add / remove vehicle list of diffenrent type with generic class
        workOnCarList();
        workOnBikeList();

        //Instantiate an object of Product using different data types.
        Product computerProduct = new Product(new Computer("Dell", "Intel® Core™ i5-3337U CPU @ 1.80GHz × 4 ", "7.7 GiB", "500.0 GB"));
        computerProduct.displayProductInfo();

        Product mouseProduct = new Product(new Mouse("Delux", "Optical mouse"));
        mouseProduct.displayProductInfo();


    }

    /**
     * Add tow interger number using generic function
     *
     * @param num1 {@link Integer}
     * @param num2 {@link Integer}
     * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
     */
    public static void addIntegerNumbers(int num1, int num2) {
        NumberOperator<Integer> intNumOperator = new NumberOperator<Integer>();
        Number sumIntNumber = intNumOperator.addTwoNumbers(num1, num2);
        LOGGER.log(Level.INFO, "Sum of {0} + {1} = {2}", new Object[] { num1, num2, sumIntNumber });
    }

    /**
     * Add tow double number using generic function
     *
     * @param num1 {@link Double}
     * @param num2 {@link Double}
     * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
     */
    public static void addDoubleNumbers(double num1, double num2) {
        NumberOperator<Double> doubleNumOperator = new NumberOperator<Double>();
        Number sumDoubleNumber = doubleNumOperator.addTwoNumbers(num1, num2);
        LOGGER.log(Level.INFO, "Sum of {0} + {1} = {2}", new Object[] { num1, num2, sumDoubleNumber });
    }

    /**
     * Add / remove cars form car list using generic class
     *
     * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
     */
    public static void workOnCarList() {
        VehicleList<Car> carList = new VehicleListImpl<Car>();
        carList.addVehicle(new Car("BMW", "Black"));
        carList.addVehicle(new Car("Ferrari", "Red"));
        carList.addVehicle(new Car("Kia", "Black"));
        carList.addVehicle(new Car("Ford", "White"));
        carList.addVehicle(new Car("BMW", "Red"));

        carList.listVehicles();
        carList.removeVehicle(2);
        carList.removeVehicle(2);
        carList.listVehicles();
    }

    /**
     * Add / remove cars form car list using generic class
     *
     * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
     */
    public static void workOnBikeList() {
        VehicleList<Car> bikeList = new VehicleListImpl<Car>();
        bikeList.addVehicle(new Car("Yamaha", "Black"));
        bikeList.addVehicle(new Car("Honda", "Red"));
        bikeList.addVehicle(new Car("Kia", "Black"));
        bikeList.addVehicle(new Car("ATK", "White & Black"));
        bikeList.addVehicle(new Car("Bajaj", "Red"));

        bikeList.listVehicles();
        bikeList.removeVehicle(2);
        bikeList.removeVehicle(2);
        bikeList.listVehicles();
    }

}
