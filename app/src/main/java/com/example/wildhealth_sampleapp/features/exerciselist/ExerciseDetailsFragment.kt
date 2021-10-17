package com.example.wildhealth_sampleapp.features.exerciselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wildhealth_sampleapp.R
import com.example.wildhealth_sampleapp.databinding.FragmentExerciseDetailsBinding
import com.example.wildhealth_sampleapp.utils.loadImage
import com.example.wildhealth_sampleapp.utils.showToast
import com.example.wildhealth_sampleapp.viewmodel.WorkoutViewModel

class ExerciseDetailsFragment: Fragment() {

    private var _binding: FragmentExerciseDetailsBinding? = null
    private val binding: FragmentExerciseDetailsBinding get() = _binding!!

    private val viewModel: WorkoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.selectedWorkout?.let { workout ->
                exerciseIv.loadImage(workout.gifUrl)
                bodyPartTv.text = workout.bodyPart
                equipmentTv.text = workout.equipment
                nameTv.text = workout.name
                targetTv.text = workout.target
            } ?: showToast(getString(R.string.details_fragment_error_message))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}