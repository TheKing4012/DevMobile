package com.example.tp3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.R;
import com.example.utils.CommonHelper;

public class Exo2_SummaryFragment extends Fragment {
    TextView tvName, tvSurname, tvPhoneNumber, tvEmail, tvDay, tvMonth, tvYear, tvInterests;
    private Bundle bundle;

    public Exo2_SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tp_exo2_summary, container, false);
        CommonHelper.createReturnBtn(this,(ConstraintLayout) myView.findViewById(R.id.tp3_exo2_summary));
        passArguments(myView);
        return myView;
    }

    public void passArguments(View myView) {
        Bundle bundle = getArguments();
        this.setArguments(bundle);

        //CommonHelper.createReturnBtn((Activity) requireView().getContext(), (ConstraintLayout) requireView().findViewById(R.id.tp1_process_menu3));
        //CommonHelper.changeActionbarColor((Activity) requireView().getContext(), getResources().getColor(R.color.ruby));

        tvName = myView.findViewById(R.id.textViewName);
        tvSurname = myView.findViewById(R.id.textViewSurname);
        tvPhoneNumber = myView.findViewById(R.id.textViewPhoneNumber);
        tvEmail = myView.findViewById(R.id.textViewEmail);

        tvInterests = myView.findViewById(R.id.textViewInterests);

        tvDay = myView.findViewById(R.id.textViewDay);
        tvMonth = myView.findViewById(R.id.textViewMonth);
        tvYear = myView.findViewById(R.id.textViewYear);

        assert bundle != null;
        tvName.setText(getResources().getString(R.string.text_name) + ": " + bundle.getString("Name"));
        tvSurname.setText(getResources().getString(R.string.text_surname) +": "+ bundle.getString("Surname"));
        tvPhoneNumber.setText(getResources().getString(R.string.text_phoneNumber) +": "+ bundle.getString("Phone"));
        tvEmail.setText(getResources().getString(R.string.text_email) +": "+ bundle.getString("Email"));

        String chaine_interets = "";
        if(bundle.getBoolean("Sport")){chaine_interets += (getResources().getString(R.string.text_sport));}
        if(bundle.getBoolean("Music")){
            if(chaine_interets != ""){
                chaine_interets += ", ";
            }
            chaine_interets += (getResources().getString(R.string.text_music));
        }
        if(bundle.getBoolean("Reading")){
            if(chaine_interets != ""){
                chaine_interets += ", ";
            }
            chaine_interets += (getResources().getString(R.string.text_reading));
        }
        tvInterests.setText(getResources().getString(R.string.text_interests) +": "+ chaine_interets);

        tvDay.setText(bundle.getString("Day"));
        tvMonth.setText(bundle.getString("Month"));
        tvYear.setText(bundle.getString("Year"));
    }
}