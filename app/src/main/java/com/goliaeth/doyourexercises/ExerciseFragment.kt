package com.goliaeth.doyourexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import pl.droidsonroids.gif.GifImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    data class Exercise(
        val exerciseType: String,
        val exerciseCount: Int
    )

    private val exercises = mutableListOf<Exercise>(
        Exercise("exercise_one", 8),
        Exercise("exercise_two", 5),
        Exercise("exercise_three", 10),
        Exercise("exercise_four", 15),
        Exercise("exercise_five", 20),
    )

    lateinit var nextButton: Button
    lateinit var exitButton: Button
    lateinit var imageView: GifImageView
    lateinit var textView: TextView

    private lateinit var currentExercise: Exercise
    private var count: Int = 0
    private var exerciseIndex: Int = 0
    private var exerciseSize = Math.min((exercises.size + 1) / 2, 3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_exercise, container, false)

        nextButton = v.findViewById(R.id.next_button)
        exitButton = v.findViewById(R.id.exit_button)
        imageView = v.findViewById(R.id.exercise_image_view)
        textView = v.findViewById(R.id.exercise_text_view)

        randomizeExercises()

        nextButton.setOnClickListener { view:View ->

            exerciseIndex++

            if (exerciseIndex < exerciseSize) {
                currentExercise = exercises[exerciseIndex]
                setExercise()
            } else {
                view.findNavController().navigate(R.id.action_exerciseFragment_to_wellDoneFragment)
            }
        }

        exitButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_exerciseFragment_to_tryAgainFragment)
        }

        return v
    }

    private fun randomizeExercises() {
        exercises.shuffle()
        exerciseIndex = 0
        setExercise()
    }

    private fun setExercise() {
        currentExercise = exercises[exerciseIndex]
        count = currentExercise.exerciseCount
        textView.text = String.format(getString(R.string.exercise_text_view), count)
        imageView.setImageResource(
            resources.getIdentifier(
                currentExercise.exerciseType,
                "drawable",
                (activity as AppCompatActivity).packageName
            )
        )
        (activity as AppCompatActivity).supportActionBar?.title = String.format(
            getString(R.string.title_android_fitness_exercise),
            exerciseIndex + 1,
            exerciseSize
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}