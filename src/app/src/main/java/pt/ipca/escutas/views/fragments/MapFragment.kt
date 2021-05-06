package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.MapController
import pt.ipca.escutas.models.Location

/**
 * Defines the map fragment.
 *
 */
class MapFragment : Fragment(), OnMapReadyCallback {
    /**
     * The map controller.
     */
    private val mapController: MapController by lazy { MapController() }

    /**
     * Invoked when the fragment instantiates his view.
     *
     * @param inflater The inflater.
     * @param container The container.
     * @param savedInstanceState The saved instance state.
     * @return The fragment view.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_map, container, false)
        val map: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        map.getMapAsync(this)

        return view
    }

    /**
     * Applies the desired behavior when the map is ready.
     *
     * @param map The map.
     */
    override fun onMapReady(map: GoogleMap?) {
        map?.apply {
            val locations: List<Location> = mapController.getLocations()

            for (location in locations) {
                val coords = LatLng(location.latitude, location.longitude)

                addMarker(
                    MarkerOptions()
                        .position(coords)
                        .title(location.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
            }
        }
    }

    companion object {
        /**
         * Gets a instance of the current fragment.
         *
         * @return A instance of the current fragment.
         */
        fun getInstance(): MapFragment = MapFragment()
    }
}
