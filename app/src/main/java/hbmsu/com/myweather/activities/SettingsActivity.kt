package hbmsu.com.myweather.activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.johnhiott.darkskyandroidlib.models.Request
import hbmsu.com.myweather.R
import hbmsu.com.myweather.connect.Connect
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private var originalUnit: Request.Units? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        originalUnit = Connect.getInstance().selectedUnit

        if (originalUnit!! == Connect.unitSi) {
            radioButtonC.isChecked = true
            radioButtonF.isChecked = false
        } else {
            radioButtonC.isChecked = false
            radioButtonF.isChecked = true
        }

        radioGroupUnits.setOnCheckedChangeListener { radioGroup, id ->
            if (id == radioButtonC.id) {
                Connect.getInstance().selectedUnit = Connect.unitSi
            } else if (id == radioButtonF.id) {
                Connect.getInstance().selectedUnit = Connect.unitFr
            }

        }

    }

    override fun onBackPressed() {
        if (Connect.getInstance().selectedUnit != originalUnit)
            setResult(Activity.RESULT_OK)
        super.onBackPressed()

    }
}
