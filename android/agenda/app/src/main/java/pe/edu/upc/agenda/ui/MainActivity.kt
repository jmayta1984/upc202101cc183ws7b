package pe.edu.upc.agenda.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.agenda.R
import pe.edu.upc.agenda.adapters.ContactAdapter
import pe.edu.upc.agenda.models.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var btAdd: Button
    private lateinit var etName: EditText
    private lateinit var rvContacts: RecyclerView

    private var contacts = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListeners()
    }

    private fun initListeners() {
        btAdd.setOnClickListener {
            val name = etName.text.toString()
            val contact = Contact(name)
            if (name.isEmpty()) {
                Toast.makeText(this, "Contact's name is missing", Toast.LENGTH_SHORT).show()
            } else {
                contacts.add(contact)
                rvContacts.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun initViews() {
        btAdd = findViewById(R.id.btAdd)
        etName = findViewById(R.id.etName)
        rvContacts = findViewById(R.id.rvContacts)

        val contactAdapter = ContactAdapter(contacts, this)
        rvContacts.adapter = contactAdapter
        rvContacts.layoutManager = LinearLayoutManager(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 0) {
                val name = data?.getStringExtra("name")
                (rvContacts.adapter as ContactAdapter).onActivityResult(name)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}