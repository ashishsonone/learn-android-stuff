package com.example.ashish.fragmentlearn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Singup.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Singup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Singup extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView descriptionTV;
    private Button googleSigninButton;


    public Singup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Singup newInstance(String param1, String param2) {
        Singup fragment = new Singup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_signup, container, false);
        descriptionTV = (TextView) view.findViewById(R.id.descriptionTV);
        googleSigninButton = (Button) view.findViewById(R.id.googleSignin);
        googleSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Singup.this.getContext(), "Taking to google signup", Toast.LENGTH_SHORT).show();
                handleLogin();
            }
        });
        return view;
    }


    public void handleLogin(){
        Intent i = new Intent(this.getContext(), TokenActivity.class);
        i.putExtra("name", "ashish");
        startActivityForResult(i, 100);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", requestCode + " " + resultCode);
        if(requestCode == 100) {
            Toast.makeText(Singup.this.getContext(), "Received result " + requestCode + "|" + resultCode, Toast.LENGTH_SHORT).show();
            if(resultCode == Activity.RESULT_OK) {//RESULT_OK = -1
                descriptionTV.setText("Signed up with token=\n" + data.getStringExtra("token"));
            }
            else if(resultCode == Activity.RESULT_CANCELED){ //RESULT_CANCELED = 0
                descriptionTV.setText("Signup cancelled by user");
            }
            else{
                descriptionTV.setText("Failed to get token");
            }
        }
    }
}
