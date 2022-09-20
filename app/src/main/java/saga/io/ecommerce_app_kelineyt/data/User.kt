package saga.io.ecommerce_app_kelineyt.data

data class User(
    val firstName:String,
    val lastName: String,
    val email: String,
    var imagePath:String = ""

){
    constructor(): this("","","","")
}
