package ma.tp.bank.dto;

import jakarta.validation.constraints.PositiveOrZero;

public class CreateAccountRequest {

    @PositiveOrZero(message = "Le solde initial doit être positif ou nul")
    private double initialBalance;

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
