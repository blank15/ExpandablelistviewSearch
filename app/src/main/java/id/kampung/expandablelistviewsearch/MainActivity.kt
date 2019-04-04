package id.kampung.expandablelistviewsearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import id.kampung.expandablelistviewsearch.model.ChildModel
import id.kampung.expandablelistviewsearch.model.TitleModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private  var listtitleModel: MutableList<TitleModel> = mutableListOf()
    private lateinit var adapter: ExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val child1 : MutableList<ChildModel>  = mutableListOf()
        child1.add(ChildModel("Solo"))
        child1.add(ChildModel("Wonogiri"))
        child1.add(ChildModel("Semarang"))
        child1.add(ChildModel("Wonosobo"))

        val child2 : MutableList<ChildModel>  = mutableListOf()
        child2.add(ChildModel("Bantul"))
        child2.add(ChildModel("Sleman"))
        child2.add(ChildModel("Gunung Kidul"))
        child2.add(ChildModel("Kulon Progo"))

        val child3 : MutableList<ChildModel>  = mutableListOf()
        child3.add(ChildModel("Pacitan"))
        child3.add(ChildModel("Ponorogo"))
        child3.add(ChildModel("Surabaya"))
        child3.add(ChildModel("Jember"))

        listtitleModel.add(TitleModel("Jawa Tengah",child1))
        listtitleModel.add(TitleModel("Yogyakarta",child2))
        listtitleModel.add(TitleModel("Jawa Timur",child3))


        adapter = ExpandableAdapter(this,listtitleModel)
        expandableList.setAdapter(adapter)

        searchView.setOnQueryTextListener( object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               adapter.filter.filter(newText)
                return false
            }

        })
    }


}
