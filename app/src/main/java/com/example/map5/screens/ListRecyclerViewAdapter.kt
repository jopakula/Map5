package com.example.map5.screens

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.map5.MainViewModel
import com.example.map5.R
import com.example.map5.data.models.PointModel

class ListRecyclerViewAdapter(private val mainViewModel: MainViewModel) : RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>() {

    private var points: List<PointModel> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val constraintLayout: ConstraintLayout
        val spacing: View
        val title: TextView
        val position: TextView
        val temperature: TextView

        init {
            constraintLayout = view.findViewById(R.id.itemPointConstraintLayout)
            spacing = view.findViewById(R.id.spacing)
            title = view.findViewById(R.id.itemPointTitleTv)
            position = view.findViewById(R.id.itemPointPositionTv)
            temperature = view.findViewById(R.id.itemPointTemperatureTv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_point, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val point = points[position]

        viewHolder.title.text = "ID: ${point.id}"
        viewHolder.position.text = "Position: (${point.latitude}, ${point.longitude})"
        viewHolder.temperature.text = "Temperature: ${point.temp}"

        if (position != 0) {
            viewHolder.spacing.visibility = View.VISIBLE
        } else {
            viewHolder.spacing.visibility = View.GONE
        }

        viewHolder.constraintLayout.setOnClickListener {
            mainViewModel.moveCameraToPoint(point)
        }
    }

    override fun getItemCount() = points.size

    @SuppressLint("NotifyDataSetChanged")
    fun updatePoints(newPoints: List<PointModel>) {
        points = newPoints
        notifyDataSetChanged()
    }
}