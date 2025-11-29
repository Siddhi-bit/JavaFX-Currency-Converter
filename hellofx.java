import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class hellofx extends Application {

    // currency -> rate vs USD (fixed demo values)
    private Map<String, Double> rates = new HashMap<>();

    // "AI-ish" tracking: how often each currency pair is used, e.g. "USD->CAD" -> count
    private Map<String, Integer> pairUsage = new HashMap<>();

    @Override
    public void start(Stage stage) {
        setupRates();

        // ===== MAIN TITLE AREA =====
        Label title = new Label("Currency Converter");
        title.setStyle(
                "-fx-font-size: 26px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #F9FAFB;"
        );

        Label subtitle = new Label("Convert between multiple world currencies");
        subtitle.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-text-fill: #9CA3AF;"
        );

        VBox titleBox = new VBox(4, title, subtitle);
        titleBox.setAlignment(Pos.CENTER);

        // ===== INPUT CONTROLS =====
        Label amountLabel = new Label("Amount");
        amountLabel.setStyle("-fx-text-fill: #E5E7EB; -fx-font-size: 13px;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setStyle(
                "-fx-background-radius: 10; " +
                "-fx-background-color: #020617; " +
                "-fx-text-fill: #F9FAFB; " +
                "-fx-prompt-text-fill: #6B7280; " +
                "-fx-border-color: #1F2937; " +
                "-fx-border-radius: 10; " +
                "-fx-padding: 8 10 8 10;"
        );

        // Currency dropdowns with flags (emoji)
        String[] currencyItems = {
                "USD 🇺🇸", "CAD 🇨🇦", "EUR 🇪🇺", "INR 🇮🇳", "GBP 🇬🇧",
                "AUD 🇦🇺", "NZD 🇳🇿", "JPY 🇯🇵", "CNY 🇨🇳", "CHF 🇨🇭",
                "SEK 🇸🇪", "NOK 🇳🇴", "DKK 🇩🇰", "SGD 🇸🇬", "HKD 🇭🇰",
                "ZAR 🇿🇦", "MXN 🇲🇽", "BRL 🇧🇷", "AED 🇦🇪", "SAR 🇸🇦"
        };

        Label fromLabel = new Label("From");
        fromLabel.setStyle("-fx-text-fill: #E5E7EB; -fx-font-size: 13px;");
        ComboBox<String> fromCurrency = new ComboBox<>();
        fromCurrency.getItems().addAll(currencyItems);
        fromCurrency.setValue("USD 🇺🇸");
        fromCurrency.setStyle(
                "-fx-background-radius: 10; " +
                "-fx-background-color: #020617; " +
                "-fx-text-fill: #F9FAFB;"
        );

        Label toLabel = new Label("To");
        toLabel.setStyle("-fx-text-fill: #E5E7EB; -fx-font-size: 13px;");
        ComboBox<String> toCurrency = new ComboBox<>();
        toCurrency.getItems().addAll(currencyItems);
        toCurrency.setValue("CAD 🇨🇦");
        toCurrency.setStyle(
                "-fx-background-radius: 10; " +
                "-fx-background-color: #020617; " +
                "-fx-text-fill: #F9FAFB;"
        );

        // Swap button between them
        Button swapButton = new Button("⇄");
        styleSecondaryButton(swapButton);
        swapButton.setPrefWidth(44);

        HBox fromRow = new HBox(8, fromCurrency, swapButton, toCurrency);
        fromRow.setAlignment(Pos.CENTER_LEFT);

        // Convert button
        Button convertButton = new Button("Convert");
        stylePrimaryButton(convertButton);
        convertButton.setMaxWidth(Double.MAX_VALUE);

        HBox buttonsRow = new HBox(10, convertButton);
        buttonsRow.setAlignment(Pos.CENTER);
        buttonsRow.setFillHeight(true);

        // ===== RESULT + RATE LABEL =====
        Label resultLabel = new Label("Result: —");
        resultLabel.setStyle("-fx-text-fill: #F9FAFB; -fx-font-size: 14px;");

        Label rateLabel = new Label("Rate: —");
        rateLabel.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 12px;");

        // ===== "AI" SUGGESTION LABEL =====
        Label aiLabel = new Label("Smart Tip: Make a conversion to see suggestions.");
        aiLabel.setStyle("-fx-text-fill: #A5B4FC; -fx-font-size: 12px;");

        // ===== HISTORY LIST =====
        Label historyTitle = new Label("History");
        historyTitle.setStyle("-fx-text-fill: #E5E7EB; -fx-font-size: 13px;");

        ListView<String> historyList = new ListView<>();
        historyList.setPrefHeight(150);
        historyList.setStyle(
                "-fx-background-color: #020617; " +
                "-fx-control-inner-background: #020617; " +
                "-fx-text-fill: #F9FAFB; " +
                "-fx-border-color: #111827;"
        );

        // ===== FORM GRID =====
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);

        form.add(amountLabel, 0, 0);
        form.add(amountField, 0, 1, 3, 1);

        form.add(fromLabel, 0, 2);
        form.add(fromRow, 0, 3, 3, 1);

        form.add(buttonsRow, 0, 4, 3, 1);

        // ===== CARD CONTAINER =====
        VBox card = new VBox(14,
                form,
                resultLabel,
                rateLabel,
                aiLabel,       // <--- added AI label here
                historyTitle,
                historyList
        );
        card.setPadding(new Insets(16));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: rgba(15,23,42,0.95); " +
                "-fx-background-radius: 18; " +
                "-fx-border-color: rgba(148,163,184,0.25); " +
                "-fx-border-radius: 18; "
        );

        DropShadow shadow = new DropShadow();
        shadow.setRadius(18);
        shadow.setOffsetX(0);
        shadow.setOffsetY(8);
        shadow.setColor(Color.rgb(0, 0, 0, 0.6));
        card.setEffect(shadow);

        // ===== ROOT LAYOUT (BACKGROUND) =====
        VBox root = new VBox(18, titleBox, card);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(24));
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #020617, #0B1120, #020617);"
        );

        // ===== LOGIC =====

        // Swap From / To currencies
        swapButton.setOnAction(e -> {
            String temp = fromCurrency.getValue();
            fromCurrency.setValue(toCurrency.getValue());
            toCurrency.setValue(temp);
        });

        // Convert
        convertButton.setOnAction(e -> {
            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                resultLabel.setText("Result: Please enter an amount.");
                aiLabel.setText("Smart Tip: Enter an amount before converting.");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);

                String fromItem = fromCurrency.getValue();
                String toItem = toCurrency.getValue();

                String fromCode = fromItem.substring(0, 3);
                String toCode = toItem.substring(0, 3);

                double converted = convert(amount, fromCode, toCode);
                resultLabel.setText(String.format("Result: %.2f %s", converted, toCode));

                double singleRate = getRate(fromCode, toCode);
                rateLabel.setText(String.format("Rate: 1 %s = %.4f %s", fromCode, singleRate, toCode));

                String historyEntry = String.format("%.2f %s → %.2f %s",
                        amount, fromCode, converted, toCode);
                historyList.getItems().add(0, historyEntry);

                // ===== "AI-ish" smart suggestion logic =====
                updateSmartSuggestion(aiLabel, amount, fromCode, toCode);

            } catch (NumberFormatException ex) {
                resultLabel.setText("Result: Invalid number. Please enter a valid amount.");
                aiLabel.setText("Smart Tip: Use only digits and a decimal point for the amount.");
            } catch (Exception ex) {
                resultLabel.setText("Result: Error during conversion.");
                aiLabel.setText("Smart Tip: Something went wrong. Try a smaller amount or different pair.");
            }
        });

        // ===== SCENE & STAGE =====
        Scene scene = new Scene(root, 480, 560);
        stage.setTitle("Currency Converter");
        stage.setScene(scene);
        stage.show();
    }

    // ===== RATES (STATIC ONLY) =====
    private void setupRates() {
        rates.put("USD", 1.0);
        rates.put("CAD", 1.35);
        rates.put("EUR", 0.92);
        rates.put("INR", 83.0);
        rates.put("GBP", 0.79);
        rates.put("AUD", 1.50);
        rates.put("NZD", 1.65);
        rates.put("JPY", 152.0);
        rates.put("CNY", 7.2);
        rates.put("CHF", 0.90);
        rates.put("SEK", 10.3);
        rates.put("NOK", 10.9);
        rates.put("DKK", 6.9);
        rates.put("SGD", 1.34);
        rates.put("HKD", 7.8);
        rates.put("ZAR", 18.5);
        rates.put("MXN", 18.0);
        rates.put("BRL", 5.2);
        rates.put("AED", 3.67);
        rates.put("SAR", 3.75);
    }

    // ===== CONVERSION HELPERS =====
    private double convert(double amount, String from, String to) {
        double fromRate = getRateVsUsd(from);
        double toRate = getRateVsUsd(to);

        double inUsd = amount / fromRate;
        return inUsd * toRate;
    }

    private double getRate(String from, String to) {
        double fromRate = getRateVsUsd(from);
        double toRate = getRateVsUsd(to);
        return toRate / fromRate;
    }

    private double getRateVsUsd(String code) {
        Double rate = rates.get(code);
        if (rate == null) {
            throw new IllegalArgumentException("Unknown currency: " + code);
        }
        return rate;
    }

    // ===== "AI-ish" SMART SUGGESTION LOGIC =====
    private void updateSmartSuggestion(Label aiLabel, double amount, String fromCode, String toCode) {
        // track usage for this pair
        String pairKey = fromCode + "->" + toCode;
        pairUsage.put(pairKey, pairUsage.getOrDefault(pairKey, 0) + 1);

        StringBuilder tip = new StringBuilder();

        // Rule 1: Big amount warning
        if (amount > 10000) {
            tip.append("You’re converting a large amount. Double-check the currencies and rate. ");
        }

        // Rule 2: Same currency
        if (fromCode.equals(toCode)) {
            tip.append("You selected the same currency for both sides. You might not need to convert. ");
        }

        // Rule 3: Favorite pair based on usage
        String bestPair = null;
        int bestCount = 0;
        for (Map.Entry<String, Integer> entry : pairUsage.entrySet()) {
            if (entry.getValue() > bestCount) {
                bestCount = entry.getValue();
                bestPair = entry.getKey();
            }
        }

        if (bestPair != null && bestCount > 2) {
            tip.append("You often convert ").append(bestPair)
                    .append(". This seems to be your frequent pair. ");
        }

        // Rule 4: Some fun hints for common pairs
        if (fromCode.equals("USD") && toCode.equals("CAD")) {
            tip.append("USD → CAD is common for US/Canada travel and shopping. ");
        }
        if (fromCode.equals("CAD") && toCode.equals("INR")) {
            tip.append("CAD → INR is often used for sending money to India. ");
        }

        if (tip.length() == 0) {
            tip.append("Smart Tip: Try saving your favorite currency pair and reuse it to save time.");
        }

        aiLabel.setText("Smart Tip: " + tip.toString().trim());
    }

    // ===== STYLE HELPERS =====
    private void stylePrimaryButton(Button button) {
        button.setStyle(
                "-fx-background-color: linear-gradient(to right, #2563EB, #6366F1); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 999; " +
                "-fx-padding: 8 20 8 20;"
        );
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: linear-gradient(to right, #1D4ED8, #4F46E5); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 999; " +
                        "-fx-padding: 8 20 8 20;"
                )
        );
        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: linear-gradient(to right, #2563EB, #6366F1); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 999; " +
                        "-fx-padding: 8 20 8 20;"
                )
        );
    }

    private void styleSecondaryButton(Button button) {
        button.setStyle(
                "-fx-background-color: #111827; " +
                "-fx-text-fill: #E5E7EB; " +
                "-fx-background-radius: 999; " +
                "-fx-padding: 6 14 6 14; " +
                "-fx-border-color: #1F2937; " +
                "-fx-border-radius: 999;"
        );
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #1F2937; " +
                        "-fx-text-fill: #F9FAFB; " +
                        "-fx-background-radius: 999; " +
                        "-fx-padding: 6 14 6 14; " +
                        "-fx-border-color: #374151; " +
                        "-fx-border-radius: 999;"
                )
        );
        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: #111827; " +
                        "-fx-text-fill: #E5E7EB; " +
                        "-fx-background-radius: 999; " +
                        "-fx-padding: 6 14 6 14; " +
                        "-fx-border-color: #1F2937; " +
                        "-fx-border-radius: 999;"
                )
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
