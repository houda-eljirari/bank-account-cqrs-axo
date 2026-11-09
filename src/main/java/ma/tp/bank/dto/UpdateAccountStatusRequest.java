package ma.tp.bank.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateAccountStatusRequest {

    @NotBlank(message = "Le statut ne peut pas être vide")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
