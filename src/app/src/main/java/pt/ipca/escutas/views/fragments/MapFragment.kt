package pt.ipca.escutas.views.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.MapController
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.services.callbacks.GenericCallback

/**
 * Defines the map fragment.
 *
 */
class MapFragment : Fragment(), OnMapReadyCallback {
    /**
     * The map controller.
     */
    private val mapController: MapController = MapController()

    /**
     * The map.
     */
    private var groups: List<Group>? = null

    /**
     * The map.
     */
    private var map: GoogleMap? = null

    /**
     * The location provider.
     */
    private lateinit var locationProvider: FusedLocationProviderClient

    /**
     * Whether the device location permission is granted.
     */
    private var locationPermissionGranted: Boolean = false

    /**
     * Invoked when the fragment instantiates his view.
     *
     * @param inflater The inflater.
     * @param container The container.
     * @param savedInstanceState The saved instance state.
     * @return The fragment view.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_map, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val fragmap: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        this.locationProvider = LocationServices.getFusedLocationProviderClient(this.context!!)
        fragmap.getMapAsync(this)
        val applicationContext = activity!!.applicationContext

        mapController.getStoredGroupsList(
            applicationContext,
            object : GenericCallback {
                override fun onCallback(value: Any?) {
                    if (value != null) {
                        var list = value as ArrayList<Group>
                        groups = list
                        onMapReady(map)
                    }
                }
            }
        )

        return view
    }

    /**
     * Invoked when the location permission request returns a result.
     *
     * @param requestCode The request code.
     * @param permissions The permissions.
     * @param grantResults The grant results.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.locationPermissionGranted = true

                    setLocationUiSettings()
                    moveCameraToCurrentLocation()
                }
            }
        }
    }

    /**
     * Applies the desired behavior when the map is ready.
     *
     * @param map The map.
     */
    override fun onMapReady(map: GoogleMap?) {
        this.map = map

        if (groups != null) {
            map?.apply {
                for (group in groups!!) {
                    val coords = LatLng(group.latitude, group.longitude)

                    addMarker(
                        MarkerOptions()
                            .position(coords)
                            .title(group.name)
                            .snippet(group.description)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    )
                }
            }
        }

        getLocationPermission()
        setLocationUiSettings()
        moveCameraToCurrentLocation()
    }

    /**
     * Gets the device location permission from the user, if such permission is not granted.
     *
     */
    private fun getLocationPermission() {
        val locationPermission: Int = ContextCompat.checkSelfPermission(
            this.context!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (locationPermission == PackageManager.PERMISSION_GRANTED) {
            this.locationPermissionGranted = true
        } else {
            val permissions: Array<String> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            this.requestPermissions(permissions, 1)
        }
    }

    /**
     * Sets the map location settings according to the device location permissions.
     *
     */
    private fun setLocationUiSettings() {
        try {
            if (this.locationPermissionGranted) {
                this.map?.isMyLocationEnabled = true
                this.map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                this.map?.isMyLocationEnabled = false
                this.map?.uiSettings?.isMyLocationButtonEnabled = false
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Moves the map camera to the current device location, if possible.
     *
     */
    private fun moveCameraToCurrentLocation() {
        try {
            if (this.locationPermissionGranted) {
                this.locationProvider.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        this.map?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    task.result.latitude,
                                    task.result.longitude
                                ),
                                16.0F
                            )
                        )
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
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
