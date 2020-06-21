package com.imagebrowser.feature.images.presentation.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.imagebrowser.feature.common.databinding.bindLifecycleOwner
import com.imagebrowser.feature.images.presentation.databinding.FragmentImageBrowserBinding
import org.koin.android.viewmodel.ext.android.viewModel

class BrowseImagesFragment : Fragment() {

    private val imagesViewModel by viewModel<ImagesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentImageBrowserBinding.inflate(inflater, container, false).run {
        this.lifecycleOwner = this@BrowseImagesFragment
        viewModel = imagesViewModel
        root.apply { bindLifecycleOwner(lifecycleOwner) }
    }
}