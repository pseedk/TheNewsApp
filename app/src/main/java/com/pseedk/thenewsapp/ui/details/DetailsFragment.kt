package com.pseedk.thenewsapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pseedk.thenewsapp.R
import com.pseedk.thenewsapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val bundleArgs: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleArg = bundleArgs.article

        articleArg.let { article ->
            article.urlToImage.let {
                Glide.with(this).load(article.urlToImage).into(binding.headerImage)
            }
            binding.apply {
                headerImage.clipToOutline = true
                articleDetailsTitle.text = article.title
                articleDetailsDescriptionText.text = article.description
                articleDetailsButton.setOnClickListener {
                    try {
                        Intent()
                            .setAction(Intent.ACTION_VIEW)
                            .addCategory(Intent.CATEGORY_BROWSABLE)
                            .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url) }
                                ?.let {
                                    article.url
                                } ?: "https://google.com"))
                            .let {
                                ContextCompat.startActivity(requireContext(), it, null)
                            }
                    } catch (e: Exception) {
                        Snackbar.make(view, "Doesn't have any browser to view the document!", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}