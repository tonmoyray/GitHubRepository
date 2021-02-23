package com.example.githubrepositories.view.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.githubrepositories.data.model.Repository
import com.example.githubrepositories.view.viewHolder.RepositoryViewHolder

/**
 * <h1>RepositoryAdapter</h1>
 * <p>
 *  Adapter for bridging between repository data & GUI
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class RepositoryAdapter(private val onClickListener: AdapterListener)
    : PagingDataAdapter<Repository, RepositoryViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)
            if (repoItem.countribution == null && !repoItem.countributionNotAvailable) {
                repoItem.position = position
                onClickListener.fetchMaxContributor(repoItem)
            }
        }
    }



    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem == newItem
        }
    }

    interface AdapterListener{
        fun fetchMaxContributor(repo : Repository)
    }
}
