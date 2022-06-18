package model;

import exceptions.EmailException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String regExp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private final Pattern pattern = Pattern.compile(regExp,Pattern.CASE_INSENSITIVE);

    public Customer(String firstName, String lastName, String email){
        if(!pattern.matcher(email).matches()){
            throw new EmailException("Invalid Email");
        }
        this.firstName =firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof final Customer other)) return false;
        if (!other.canEqual((Object) this)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (!Objects.equals(this$firstName, other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (!Objects.equals(this$lastName, other$lastName)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (!Objects.equals(this$email, other$email)) return false;
        final Object this$regExp = this.getRegExp();
        final Object other$regExp = other.getRegExp();
        if (!Objects.equals(this$regExp, other$regExp)) return false;
        final Object this$pattern = this.getPattern();
        final Object other$pattern = other.getPattern();
        return Objects.equals(this$pattern, other$pattern);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Customer;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $regExp = this.getRegExp();
        result = result * PRIME + ($regExp == null ? 43 : $regExp.hashCode());
        final Object $pattern = this.getPattern();
        result = result * PRIME + ($pattern == null ? 43 : $pattern.hashCode());
        return result;
    }
}
