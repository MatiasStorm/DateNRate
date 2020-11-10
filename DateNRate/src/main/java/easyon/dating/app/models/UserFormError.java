package easyon.dating.app.models;

public class UserFormError {
    private boolean passwordError = false;
    private boolean usernameError = false;
    private boolean emailError = false;
    private boolean townError = false;
    private boolean dateOfBirthError = false;

    public boolean isPasswordError() {
        return passwordError;
    }

    public boolean containsErrors (){
        return (passwordError || usernameError || emailError || townError);
    }

    public boolean isDateOfBirthError() {
        return dateOfBirthError;
    }

    public void setDateOfBirthError(boolean dateOfBirthError) {
        this.dateOfBirthError = dateOfBirthError;
    }

    public void setPasswordError(boolean passwordError) {
        this.passwordError = passwordError;
    }

    public boolean isUsernameError() {
        return usernameError;
    }

    public void setUsernameError(boolean usernameError) {
        this.usernameError = usernameError;
    }

    public boolean isEmailError() {
        return emailError;
    }

    public void setEmailError(boolean emailError) {
        this.emailError = emailError;
    }

    public boolean isTownError() {
        return townError;
    }

    public void setTownError(boolean townError) {
        this.townError = townError;
    }
}
