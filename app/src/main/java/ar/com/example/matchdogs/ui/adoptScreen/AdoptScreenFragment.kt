package ar.com.example.matchdogs.ui.adoptScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.core.RetrofitClient
import ar.com.example.matchdogs.data.models.Dogs
import ar.com.example.matchdogs.data.remote.DogDataSource
import ar.com.example.matchdogs.databinding.FragmentAdoptScreenBinding
import ar.com.example.matchdogs.domain.remote.DogRepositoryImplements
import ar.com.example.matchdogs.presentation.DogViewModel
import ar.com.example.matchdogs.presentation.DogViewModelFactory
import ar.com.example.matchdogs.ui.adoptScreen.adapters.DogAdapter
import java.util.*

class AdoptScreenFragment : Fragment(R.layout.fragment_adopt_screen), AdapterView.OnItemSelectedListener {

    private lateinit var binding : FragmentAdoptScreenBinding
    private lateinit var adapter : DogAdapter
    private lateinit var aaDogs:ArrayAdapter<String>
    private val dogImages = mutableListOf<String>()
    private val viewModel by viewModels<DogViewModel> {
        DogViewModelFactory(DogRepositoryImplements(DogDataSource(RetrofitClient.webService))) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdoptScreenBinding.bind(view)
        spinner()

    }

    private fun spinner() {
        /*Aca vamos a crear la logica del spinner, cuando se elija una opcion el spinner le manda
        el valor de la raza a la funcion en la cual hacemos el fetch de los perros
        Ademas, cuando el spinner no este abierto y no haya nada queremos mostrar un msj
        que indique que hay que seleccionar un elemento del spinner.
         */
        aaDogs = ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_spinner_dropdown_item)

        aaDogs.addAll(
            Arrays.asList("","Affenpinscher", "African", "Airedale","Akita",
            "Appenzeller","Australian","Basenji","Beagle","Bluetick","Borzoi","Bouvier",
            "Boxer","Brabancon","Briard","Buhund","Bulldog","Bullterrier","Cairn","Cattledog",
            "Chihuahua","Chow","Clumber","Cockapoo","Collie","Coonhound","Corgi","Cotondetulear",
            "Dachshund","Dalmatian","Dane","Deerhound","Dhole","Dingo","Doberman","Elkhound",
            "Entlebucher","Eskimo","Finnish","Frise","Germanshepherd","Greyhound","Groenendael",
            "Havanese","Hound","Husky","Keeshond","Kelpie","Komondor","Kuvasz","Labradoodle",
            "Labrador","Leonberg","Lhasa","Malamute","Malinois","Maltese","Mastiff",
            "Mexicanhairless","Mix","Mountain","Newfoundland", "Otterhound","Ovcharka",
            "Papillon","Pekinese","Pembroke","pinscher","Pitbull","Pointer","Pomeranian",
            "Poodle","Pug","Puggle","Pyrenees","Redbone","Retriever","Ridgeback","Rottweiler",
            "Saluki","Samoyed","Schipperke","Schnauzer","Setter","Sheepdog","Shiba",
            "Shihtzuspaniel","Springer","Stbernard","Terrier","Vizsla","Waterdog",
            "Weimaraner","Whippet","Wolfhound"))
        binding.spinnerBreeds.onItemSelectedListener = this
        binding.spinnerBreeds.adapter = aaDogs

    }

    private fun obtainDogs(breed:String) {
        viewModel.fetchDogs(breed).observe(viewLifecycleOwner, Observer {
            when(it){
                is Response.Loading -> {
                    binding.alertView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.progressBar.visibility = View.GONE
                    //Cargamos el adaptador con los perros
                    binding.rvContainer.visibility = View.VISIBLE
                    adapter = DogAdapter(dogImages)
                    binding.rvContainer.adapter = adapter
                    val images: List<String> = it.data.imageUrl
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }
                is Response.Failure -> {
                    Toast.makeText(requireContext(), "Error: ${it.exception}", Toast.LENGTH_LONG).show()
                    //Mostramos toast con el error
                }
            }
        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val dogSelected = aaDogs.getItem(position)
        if (!dogSelected.isNullOrEmpty()){
            obtainDogs(dogSelected.toLowerCase())
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }


}