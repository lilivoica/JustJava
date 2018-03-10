package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_LILI";
    int quantity = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This method is called when the order button is clicked.
    public void submitOrder(View view) {
        // Find the user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //Figure out if the user whants whipped cream topping
        CheckBox whippedDreamCheckBox = (CheckBox) findViewById(R.id.wipped_cream_checkbox);
        boolean hasWhippedCream = whippedDreamCheckBox.isChecked();

        //Figure out if the user whants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_for_user) + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     * @param addWippedCream is whether or not user whants wipped cream topping
     * @param addChocolate   is whether or not user whants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWippedCream, boolean addChocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //Add $1 if the user wants whipped cream
        if (addWippedCream) {
            basePrice = basePrice + 1;
        }

        //Add $2 if the user wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    //This method is called when the order button is clicked.
    public void increment(View view) {
        if (quantity == 100) {
            //Show an erroe message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    //This method is called when the order button is clicked.
    public void decrement(View view) {
        if (quantity == 1) {
            //Show an erroe message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    // Create summary of the order.
    private String createOrderSummary(String name, int price, boolean addWippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.name_user) + name;
        priceMessage += "\n" + getString(R.string.add_wipped_cream) + addWippedCream;
        priceMessage += "\n" + getString(R.string.add_chocolate) + addChocolate;
        priceMessage += "\n" + getString(R.string.quantity) + quantity;
        priceMessage += "\n" + getString(R.string.total_price) + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    //This method displays the given quantity value on the screen.
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    //This method displays the given text on the screen.
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}