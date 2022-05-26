package ru.alexeyk2021.taskplanner.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ru.alexeyk2021.taskplanner.R


class SortViewOrder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val chooseButton: Button = itemView.findViewById(R.id.button)
}

class ChooseTaskSortingAdapter(private val sortingTypes: List<String>) :
    RecyclerView.Adapter<SortViewOrder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewOrder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.sort_tasks_button_item, parent, false)
        return SortViewOrder(itemView)
    }

    override fun onBindViewHolder(holder: SortViewOrder, position: Int) {
        holder.chooseButton.text = sortingTypes[position]
    }

    override fun getItemCount(): Int = sortingTypes.size

}