package saga.io.ecommerce_app_kelineyt.util

import android.util.Patterns

fun validateEmail( email: String): RegisterValidation {
    if(email.isEmpty())
        return RegisterValidation.Failed("Email cannot be empty")

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong email format")


    return RegisterValidation.Success
}

fun validatePassword(password: String ): RegisterValidation{
    if (password.isEmpty())
        return RegisterValidation.Failed("Password field is empty")

    if(password.length < 5)
        return RegisterValidation.Failed("Password should be between 6 character")

    return RegisterValidation.Success
}