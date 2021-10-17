package com.example.wildhealth_sampleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wildhealth_sampleapp.databinding.ListItemWorkoutBinding
import com.example.wildhealth_sampleapp.model.remote.dto.Workout

class WorkoutAdapter(
    private val listener: (workout: Workout) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private val workoutList = mutableListOf<Workout>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        return WorkoutViewHolder(
            ListItemWorkoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(workoutList[position])
    }

    override fun getItemCount() = workoutList.size

    fun submitList(workouts: List<Workout>) {
        workoutList.clear()
        workoutList.addAll(workouts)
        notifyDataSetChanged()
    }

    class WorkoutViewHolder(
        private val binding: ListItemWorkoutBinding,
        private val listener: (workout: Workout) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            with(binding) {
                root.setOnClickListener { listener.invoke(workout) }
                nameTv.text = workout.name
                bodyPartTv.text = workout.bodyPart
            }
        }
    }
}