package ma.tp.bank.dto;

import jakarta.validation.constraints.Positive;

public class AmountRequest {

    @Positive(message = "Le montant doit être strictement positif")
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
