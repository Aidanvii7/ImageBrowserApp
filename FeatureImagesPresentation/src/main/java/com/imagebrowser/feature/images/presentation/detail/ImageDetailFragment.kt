package com.imagebrowser.feature.images.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.imagebrowser.feature.common.databinding.bindLifecycleOwner
import com.imagebrowser.feature.images.presentation.databinding.FragmentImageDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ImageDetailFragment : Fragment() {

    private val imageDetailViewModel by viewModel<ImageDetailViewModel> {
        val imageInfo = ImageDetailFragmentArgs.fromBundle(requireArguments()).imageInfo
        parametersOf(imageInfo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentImageDetailBinding.inflate(inflater, container, false).run {
        this.lifecycleOwner = this@ImageDetailFragment
        viewModel = imageDetailViewModel
        root.apply { bindLifecycleOwner(lifecycleOwner) }
    }
}