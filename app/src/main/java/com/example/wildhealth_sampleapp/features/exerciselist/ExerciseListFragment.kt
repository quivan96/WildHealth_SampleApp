package com.example.wildhealth_sampleapp.features.exerciselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wildhealth_sampleapp.R
import com.example.wildhealth_sampleapp.adapter.WorkoutAdapter
import com.example.wildhealth_sampleapp.databinding.FragmentExerciseListBinding
import com.example.wildhealth_sampleapp.model.remote.dto.Workout
import com.example.wildhealth_sampleapp.utils.Resource
import com.example.wildhealth_sampleapp.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseListFragment : Fragment() {
    private var _binding: FragmentExerciseListBinding? = null
    private val binding: FragmentExerciseListBinding get() = _binding!!

    private val viewModel: WorkoutViewModel by activityViewModels()

    private lateinit var workoutAdapter: WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workoutAdapter = WorkoutAdapter(this::onWorkoutSelected)

        with(binding) {
            exerciseSearchEt.editText?.addTextChangedListener {
                if (it.toString().isBlank()) {
                    viewModel.searchExercise("")
                }
            }

            searchBtn.setOnClickListener {
                val input = exerciseSearchEt.editText?.text.toString()
                if (input.isNotBlank()) {
                    viewModel.searchExercise(input)
                }
            }

            exerciseRv.apply {
                adapter = workoutAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }

            viewModel.workouts.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        exerciseSearchEt.isEnabled = false
                        progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        exerciseSearchEt.isEnabled = true
                        progressBar.isVisible = false
                        workoutAdapter.submitList(resource.data)
                    }
                    is Resource.Error -> {
                        exerciseSearchEt.isEnabled = true
                        progressBar.isVisible = false
                    }
                }
            }
        }
    }

    private fun onWorkoutSelected(workout: Workout) {
        viewModel.onUserSelection(workout)
        findNavController().navigate(R.id.action_exerciseListFragment_to_exerciseDetailsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}