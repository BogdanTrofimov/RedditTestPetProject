package com.example.reddittestpetproject.ui.detail

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.reddittestpetproject.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: DetailFragmentBinding
    private lateinit var url: String

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                Log.e(PERM_TAG, "onActivityResult: PERMISSION GRANTED")
            } else {
                Log.e(PERM_TAG, "onActivityResult: PERMISSION DENIED")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater)
        url = DetailFragmentArgs.fromBundle(requireArguments()).selected
        Glide.with(binding.image.context).load(url).into(binding.image)
        binding.button.setOnClickListener {
            checkForPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "storage",
            )
        }
        return binding.root
    }

    /**
     * Check for permission,
     * if permission granted - we will download image,
     * if not - show dialog
     */
    private fun checkForPermission(
        permission: String,
        name: String
    ) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(requireContext(), "$name permission is granted", Toast.LENGTH_LONG)
                    .show()
                viewModel.downloadImage(url)
            }
            shouldShowRequestPermissionRationale(permission) -> {
                showDialog(permission, name)
            }
            else -> permissionResult.launch(permission)
        }
    }

    /**
     * Show dialog if user declined permission first time
     * and load WRITE_EXTERNAL_STORAGE dialog one more time
     */
    private fun showDialog(permission: String, name: String) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { _, _ ->
                permissionResult.launch(permission)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        private const val PERM_TAG = "Permission"
    }
}