package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pt.ipca.escutas.R

/**
 * Defines the profile fragment.
 *
 */
class ProfileFragment : Fragment() {
    /**
     * Invoked when the fragment instantiates his view.
     *
     * @param inflater The inflater.
     * @param container The container.
     * @param savedInstanceState The saved instance state.
     * @return The fragment view.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_profile, container, false)

    /**
     * Gets a instance of the current fragment.
     *
     * @return A instance of the current fragment.
     */
    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }
}
