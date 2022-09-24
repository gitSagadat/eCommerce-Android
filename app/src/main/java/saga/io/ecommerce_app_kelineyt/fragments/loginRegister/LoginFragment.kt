package saga.io.ecommerce_app_kelineyt.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import saga.io.ecommerce_app_kelineyt.R
import saga.io.ecommerce_app_kelineyt.activities.ShoppingActivity
import saga.io.ecommerce_app_kelineyt.databinding.FragmentLoginBinding
import saga.io.ecommerce_app_kelineyt.util.Resource
import saga.io.ecommerce_app_kelineyt.viewmodel.LoginViewModel
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonLoginLogin.setOnClickListener{
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()
                viewModel.login(email,password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.buttonLoginLogin.startAnimation()
                    }
                    is Resource.Success ->{
                        binding.buttonLoginLogin.revertAnimation()
                        Intent(requireActivity(),ShoppingActivity::class.java).also {
                            intent -> intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error ->{
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}