package pe.edu.upc.agenda.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.agenda.R
import pe.edu.upc.agenda.models.Contact
import pe.edu.upc.agenda.ui.ContactActivity

class ContactAdapter(private val contacts: ArrayList<Contact>, private val context: Context) :
    RecyclerView.Adapter<ContactAdapter.ContactPrototype>() {

    inner class ContactPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var cvContact: CardView
        private lateinit var tvName: TextView
        private lateinit var btDelete: ImageButton

        fun bindTo(contact: Contact) {
            tvName = itemView.findViewById(R.id.tvName)
            btDelete = itemView.findViewById(R.id.btDelete)
            cvContact = itemView.findViewById(R.id.cvContact)

            tvName.text = contact.name

            btDelete.setOnClickListener {
                contacts.remove(contact)
                this@ContactAdapter.notifyDataSetChanged()
            }

            cvContact.setOnClickListener {
                val intent = Intent(context, ContactActivity::class.java)
                intent.putExtra("name", contact.name)
                (context as Activity).startActivityForResult(intent, 0)
            }
        }
    }

    fun onActivityResult(name: String?){
        contacts[0].name = name!!
        notifyDataSetChanged()
    }

    // Vista por cada fila de acuerdo al diseño del prototipo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactPrototype {
        val view = LayoutInflater.from(context).inflate(R.layout.prototype_contact, parent, false)
        return ContactPrototype(view)
    }

    // Cantidad de elementos del recyclerview
    override fun getItemCount(): Int {
        return contacts.size
    }

    // Información a mostrar en cada vista
    override fun onBindViewHolder(holder: ContactPrototype, position: Int) {
        holder.bindTo(contacts[position])
    }
}