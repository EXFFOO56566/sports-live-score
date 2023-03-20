package com.aroniez.futaa.ui.competitions.countries

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aroniez.futaa.R
import com.aroniez.futaa.models.competitions.Competition
import com.aroniez.futaa.models.country.Country
import com.aroniez.futaa.ui.season.LeagueDetailActivity
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.country_row_item.view.*
import kotlinx.android.synthetic.main.league_row_item1.view.*


class CountriesAdapter(private val countries: ArrayList<Country>, private val context: Context)
    : ExpandableRecyclerAdapter<Country, Competition, CountriesAdapter.CountryViewHolder, CountriesAdapter.LeagueViewHolder>(countries) {
    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parentViewGroup.context!!).inflate(R.layout.country_row_item, parentViewGroup, false))
    }

    override fun onBindChildViewHolder(childViewHolder: LeagueViewHolder, parentPosition: Int, childPosition: Int, child: Competition) {
        childViewHolder.bindData(child, countries[parentPosition])
    }

    override fun onBindParentViewHolder(parentViewHolder: CountryViewHolder, parentPosition: Int, parent: Country) {
        parentViewHolder.bindData(parent)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(LayoutInflater.from(childViewGroup.context!!).inflate(R.layout.league_row_item1, childViewGroup, false))
    }

    inner class CountryViewHolder(itemView: View) : ParentViewHolder<Country, Competition>(itemView) {

        fun bindData(country: Country) {
            Picasso.get().load(country.image_path).placeholder(R.drawable.ic_shipping_method_world_normal).into(itemView.countryFlag)
            itemView.countryName.text = country.name
            itemView.competitionsCount.text = country.leagues.data.size.toString() + " competitions"
            //itemView.position.text = (position + 1).toString()
            //itemView.totals.text = playerRanking.assists.toString() + " assists"

        }
    }

    inner class LeagueViewHolder(itemView: View) : ChildViewHolder<Competition>(itemView) {

        fun bindData(league: Competition, country: Country) {
            Picasso.get().load(league.logo_path).placeholder(R.drawable.ic_shipping_method_world_normal).into(itemView.leagueFlag)
            itemView.leagueName.text = league.name
            itemView.leagueLayout.setOnClickListener {
                val intent = Intent(context, LeagueDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("season_id", league.current_season_id)
                intent.putExtra("live_standings", league.live_standings)
                intent.putExtra("logo_path", league.logo_path)
                intent.putExtra("country_name", country.name)
                intent.putExtra("league_name", league.name)
                intent.putExtra("coverage", league.coverage)
                context.startActivity(intent)
            }

        }
    }
}