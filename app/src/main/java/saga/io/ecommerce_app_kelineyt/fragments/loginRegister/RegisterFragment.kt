package saga.io.ecommerce_app_kelineyt.fragments.loginRegister


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import saga.io.ecommerce_app_kelineyt.R
import saga.io.ecommerce_app_kelineyt.data.User
import saga.io.ecommerce_app_kelineyt.databinding.FragmentRegisterBinding
import saga.io.ecommerce_app_kelineyt.viewmodel.RegisterViewModel

private val TAG ="RegisterFragment"

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonRegisterRegister.setOnClickListener{
                val user = User(
                    edFirstNameRegister.text.toString().trim(),
                    edLastNameRegister.text.toString().trim(),
                    edEmailRegister.text.toString().trim()
                )
                val password = edEdPasswordRegister.text.toString()
                viewModel.createAccountWithEmailAndPassword(user,password)
            }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect{
                when(it){
                    is saga.io.ecommerce_app_kelineyt.util.Resource.Loading ->{
                        binding.buttonRegisterRegister.startAnimation()
                    }
                    is saga.io.ecommerce_app_kelineyt.util.Resource.Success ->{
                        Log.d("test",it.data.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                    is saga.io.ecommerce_app_kelineyt.util.Resource.Error ->{
                        Log.e(TAG,it.message.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }
    }
}