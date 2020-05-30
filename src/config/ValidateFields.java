package config;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;

public class ValidateFields {

    public static void makeFieldsInteger(List<TextField> intFields) {

        ArrayList<TextField> arrFields = new ArrayList<>();
        arrFields.addAll(intFields);

        for (TextField textField : intFields) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {

                if (!newValue.matches("\\d{0,9}")) {
                    textField.setText(oldValue);
                }
            });
        }

    }

    public static void makeFieldsDecimal(List<TextField> numericFields) {

        ArrayList<TextField> arrFields = new ArrayList<>(numericFields);

        for (TextField textField : arrFields) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {

                if (!newValue.matches("\\d{0,15}([.]\\d{0,4})?")) {
                    textField.setText(oldValue);
                }
            });
        }
    }

    public static void restrictTextFields(List<TextField> fields_list, int max_length) {

        for (TextField text_field : fields_list) {
            text_field.textProperty().addListener((ov, oldValue, newValue) -> {
                if (text_field.getText().length() > max_length) {
                    String s = text_field.getText().substring(0, max_length);
                    text_field.setText(s);
                }
            });
        }

    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}