package com.afrinaldi.beber.presentation.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.afrinaldi.beber.core.ui.BreakingNewsAdapter
import com.afrinaldi.beber.core.utils.DATA
import com.afrinaldi.beber.core.utils.Helper
import com.afrinaldi.beber.databinding.FragmentBookmarkBinding
import com.afrinaldi.beber.presentation.detail.DetailActivity
import com.afrinaldi.beber.presentation.detail.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val bookmarkViewModel : BookmarkViewModel by viewModel()
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            val newsAdapter = BreakingNewsAdapter()
            newsAdapter.onIntentClicked = { news ->
                Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(DATA, news)
                    startActivity(it)
                }
            }

            newsAdapter.onBookmarkClicked = { news, item ->
                var statusBookmark = news.isBookmark
                statusBookmark = !statusBookmark
                detailViewModel.setBookmarkTourism(news, statusBookmark)
                item.ivBookmark.setImageDrawable(ContextCompat.getDrawable(requireContext(), Helper.setStatusBookmark(statusBookmark)))
            }

            bookmarkViewModel.bookmarkNews.observe(viewLifecycleOwner) {
                newsAdapter.setData(it)
            }

            binding.rvBreakingNews.apply {
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}