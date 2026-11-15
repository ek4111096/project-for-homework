package ui.pages;

import lombok.Getter;

@Getter
public enum BankAlert {
    USER_CREATED_SUCCESSFULLY("✅ User created successfully!"),
    USERNAME_MUST_BE_BETWEEN_3_AND_15_CHARACTERS("Username must be between 3 and 15 characters"),
    NEW_ACCOUNT_CREATED("✅ New Account Created! Account Number: "),
    DEPOSIT_MADE_SUCCESSFULLY("✅ Successfully deposited $5000 to account ACC"),
    DEPOSIT_UNSUCCESSFUL("❌ Please deposit less or equal to 5000$."),
    TRANSFER_MADE_SUCCESSFULLY("✅ Successfully transferred $100 to account ACC"),
    TRANSFER_UNSUCCESSFUL("❌ No user found with this account number."),
    TRANSFER_NOT_CONFIRMED("❌ Please fill all fields and confirm."),
    TRANSFER_REPEAT_SUCCESSFULLY("✅ Transfer of $505 successful from Account "),
    WRONG_USERNAME("❌ No matching users found."),
    NAME_UPDATED_SUCCESSFULLY("✅ Name updated successfully!"),
    NAME_UNSUCCESSFUL_UPDATE("Name must contain two words with letters only");


    private final String message;

    BankAlert(String message) {
        this.message = message;
    }
}