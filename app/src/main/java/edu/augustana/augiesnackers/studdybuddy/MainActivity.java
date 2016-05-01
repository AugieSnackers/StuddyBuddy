package edu.augustana.augiesnackers.studdybuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private Firebase firebase;
    private EditText sendText;
    private ImageView sendbtn;
    private Query mStatusRef;
    FirebaseRecyclerAdapter<Status, View_Holder> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        List<Data> data = fill_with_data();
        sendText = (EditText) findViewById(R.id.etStatus);
        //SEND DATA OUT, WORKS
        sendbtn = (ImageView) findViewById(R.id.ivSend);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Status status = new Status("Nelly Cheboi", "uid", sendText.getText().toString(), "#testing");
                firebase.push().setValue(status, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Log.e("Error", firebaseError.toString());
                        }
                    }
                });
                sendText.setText("");
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(data, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebase = new Firebase("https://studdy-buddy.firebaseio.com/Status");
        mStatusRef = firebase.limitToLast(50);
        //TODO FIX THIS, DATASHOT BOUNCE ERROR
//        firebaseAdapter = new FirebaseRecyclerAdapter<Status, View_Holder>(Status.class, R.layout.card_view, View_Holder.class, mStatusRef) {
//            @Override
//            public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
//                View_Holder holder = new View_Holder(v);
//                return holder;
//            }
//
//            @Override
//            public void populateViewHolder(View_Holder holder, Status status, int position) {
//                holder.setName(status.getName());
//                holder.setDescription(status.getDescription());
//                holder.setImage(R.drawable.ic_facebook);
//            }
//        };
//
//        recyclerView.setAdapter(firebaseAdapter);


        //TODO Firebase Stuff https://www.firebase.com/docs/android/guide/

    }

    //Create a list of Data objects
    public List<Data> fill_with_data() {
        //dummy for testing replace with firebase data
        List<Data> data = new ArrayList<>();

        data.add(new Data("Diffusion Monte Carlo", "Diffusion Monte Carlo (DMC) is one of the variations of Monte Carlo processes, a class of numerical techniques that uses stochastic calculus to solve mathematical problems that are either too difficult or impossible to solve analytically. Although the specifics of Monte Carlo processes vary from application to application, the basic structure of such a process is as follows: A simulation involving large iterations of an algorithm manipulates a set of data in a random Following the destruction of Metropolis. ", R.mipmap.ic_launcher));
        data.add(new Data("Schrödinger equation", "The Schrödinger equation (eq.1) is a second order partial differential equation very essential equation to quantum mechanics as it provides an accepted description of the microscopic phenomenon at non-relativistic energies", R.mipmap.ic_launcher));
        data.add(new Data("Eigen_Value Problem ", "Schrödinger equation Eigenvalue problem with the Eigenfunction ψ and the eigenvalue E. The limitation of solving this problem lies in the algebraic idea of having two sets of unknowns and only one equation", R.mipmap.ic_launcher));
        data.add(new Data("Yay DMC", "The most attractive quality of the DMC method is its polynomial scaling with system size in contrast to traditional numerical methods that require exponentially more points to maintain accuracy with growth in the system size  ", R.mipmap.ic_launcher));
        data.add(new Data("Probability", "The wave function represents the probability amplitude for finding a particle at a given point in space at a given time. The actually probability of finding the particle is given by the product of the wave function with its complex conjugate.", R.mipmap.ic_launcher));
        data.add(new Data("Ambiguity", "To sufficiently guarantee an unambiguous value of probability of finding a particle at a given position and time, the wave function is assumed to be single-valued. The wave-function may also be complex therefore associating the product of the wave-function with its complex conjugate ensures that the probability is a real value ", R.mipmap.ic_launcher));


        return data;
    }


}
