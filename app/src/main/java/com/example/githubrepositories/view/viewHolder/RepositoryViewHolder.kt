package com.example.githubrepositories.view.viewHolder

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.data.model.Repository
import com.example.githubrepositories.utils.CommonHelper

/**
 * <h1>RepositoryViewHolder</h1>
 * <p>
 * GUI item of each individual list row
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val author: TextView = view.findViewById(R.id.repo_author)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val contributorUsername: TextView = view.findViewById(R.id.contributor_username_value)
    private val contributorUsernameProgress: ProgressBar = view.findViewById(R.id.username_progress)
    private val additions: TextView = view.findViewById(R.id.additions_value)
    private val additionsProgress: ProgressBar = view.findViewById(R.id.additions_progress)
    private val deletions: TextView = view.findViewById(R.id.deletions_value)
    private val deleteProgress: ProgressBar = view.findViewById(R.id.deletions_progress)
    private val commits: TextView = view.findViewById(R.id.commits_value)
    private val commitsProgress: ProgressBar = view.findViewById(R.id.commits_progress)
    private val updatedOn: TextView = view.findViewById(R.id.updated_on)

    private var repo: Repository? = null

    init {
        view.setOnClickListener {
            repo?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: Repository) {
        val resources = this.itemView.context.resources
        this.repo = repo
        name.text =  repo.name
        author.text =  CommonHelper.getRepoOwner(repo.fullName)
        if (!repo.description.isNullOrEmpty()) {
            description.text = repo.description
        }else{
            description.visibility = View.GONE
        }
        if (!repo.language.isNullOrEmpty()) {
            language.text = resources.getString(R.string.language, repo.language)
        }else{
            language.visibility = View.GONE
        }
        if (!repo.updatedAt.isNullOrEmpty()) {
            updatedOn.text = resources.getString(
                    R.string.updated_on,
                    CommonHelper.monthDayHourMin(CommonHelper.utcToLong(repo.updatedAt)))
        }else{
            updatedOn.visibility = View.GONE
        }

        if(repo.countribution != null){
            setProgressVisibility(false)
            additions.text = repo.countribution?.a.toString()
            deletions.text = repo.countribution?.d.toString()
            commits.text = repo.countribution?.c.toString()
            contributorUsername.text = resources.getString(R.string.unknown)
        }else if(repo.countributionNotAvailable){
            setProgressVisibility(false)
            additions.text = resources.getString(R.string.unknown)
            deletions.text = resources.getString(R.string.unknown)
            commits.text = resources.getString(R.string.unknown)
            contributorUsername.text = resources.getString(R.string.unknown)
        }else{
            setProgressVisibility(true)
        }
    }

    private fun setProgressVisibility(isVisible : Boolean){
        if(isVisible){
            commitsProgress.visibility = View.VISIBLE
            additionsProgress.visibility = View.VISIBLE
            deleteProgress.visibility = View.VISIBLE
            contributorUsernameProgress.visibility = View.VISIBLE
        }else{
            commitsProgress.visibility = View.INVISIBLE
            additionsProgress.visibility = View.INVISIBLE
            deleteProgress.visibility = View.INVISIBLE
            contributorUsernameProgress.visibility = View.INVISIBLE
        }
    }

    companion object {
        fun create(parent: ViewGroup): RepositoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repository_view_item, parent, false)
            return RepositoryViewHolder(view)
        }
    }
}