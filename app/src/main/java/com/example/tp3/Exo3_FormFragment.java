package com.example.tp3;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.R;
import com.example.utils.Dialog;
import com.example.utils.FirebaseJsonDownloader;
import com.example.utils.JsonReader;
import com.example.utils.LambaExpr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Exo3_FormFragment extends Fragment {

    Button btnSend, btnDownload;
    EditText editTextName, editTextSurname, editTextPhoneNumber, editTextEmail;
    Spinner spinnerDay, spinnerMonth, spinnerYear;
    CheckBox checkBoxSport, checkBoxMusique, checkBoxLecture;
    Switch switchSynchronisation;
    Bundle bundle = new Bundle();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Exo3_FormFragment() {
        // Required empty public constructor
    }

    public static Exo3_FormFragment newInstance(String param1, String param2) {
        Exo3_FormFragment fragment = new Exo3_FormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onResume() {
        fillFormFromJSON(requireView());
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_tp3_exo3_form, container, false);
        btnSend = (Button) myView.findViewById(R.id.button_send);
        btnDownload = (Button) myView.findViewById(R.id.button_download);
        //fillFormFromJSON(myView);

        // Récupération des vues
        editTextName = myView.findViewById(R.id.editTextName);
        editTextSurname = myView.findViewById(R.id.editTextSurname);
        editTextPhoneNumber = myView.findViewById(R.id.editTextPhoneNumber);
        editTextEmail = myView.findViewById(R.id.editTextEmail);
        spinnerDay = myView.findViewById(R.id.spinnerDay);
        spinnerMonth = myView.findViewById(R.id.spinnerMonth);
        spinnerYear = myView.findViewById(R.id.spinnerYear);
        checkBoxSport = myView.findViewById(R.id.checkBoxSport);
        checkBoxMusique = myView.findViewById(R.id.checkBoxMusique);
        checkBoxLecture = myView.findViewById(R.id.checkBoxLecture);
        switchSynchronisation = myView.findViewById(R.id.switchSynchronisation);

        // Configuration des Spinners
        setupSpinners(myView, spinnerDay, spinnerMonth, spinnerYear);

        btnSend.setOnClickListener(view -> {
            bundle.putString("Name", String.valueOf(editTextName.getText()));
            bundle.putString("Surname", String.valueOf(editTextSurname.getText()));
            bundle.putString("Phone", String.valueOf(editTextPhoneNumber.getText()));
            bundle.putString("Email", String.valueOf(editTextEmail.getText()));

            bundle.putInt("DayPosition", spinnerDay.getSelectedItemPosition());
            bundle.putInt("MonthPosition", spinnerMonth.getSelectedItemPosition());
            bundle.putInt("YearPosition", spinnerYear.getSelectedItemPosition());
            bundle.putString("Day", String.valueOf(spinnerDay.getSelectedItem()));
            bundle.putString("Month", String.valueOf(spinnerMonth.getSelectedItem()));
            bundle.putString("Year", String.valueOf(spinnerYear.getSelectedItem()));

            bundle.putBoolean("Sport", checkBoxSport.isChecked());
            bundle.putBoolean("Music", checkBoxMusique.isChecked());
            bundle.putBoolean("Reading", checkBoxLecture.isChecked());
            bundle.putBoolean("Synchronise", switchSynchronisation.isChecked());

            LambaExpr lambaExprNoSyncYes = () -> {
                JsonReader.deleteJSONFile(myView.getContext());

                // Créez une nouvelle instance de summary
                Exo3_SummaryFragment summary = new Exo3_SummaryFragment();
                summary.setArguments(bundle);

                // Remplacez le fragment actuel par le nouveau fragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, summary);
                transaction.addToBackStack(null); // Si vous souhaitez ajouter la transaction à la pile de retour
                transaction.commit();
            };

            LambaExpr lambaExprSyncYes = () -> {
                JsonReader.createJSONFile(myView.getContext(), bundle);

                // Créez une nouvelle instance de summary
                Exo3_SummaryFragment summary = new Exo3_SummaryFragment();
                summary.setArguments(bundle);

                // Remplacez le fragment actuel par le nouveau fragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, summary);
                transaction.addToBackStack(null); // Si vous souhaitez ajouter la transaction à la pile de retour
                transaction.commit();
            };

            LambaExpr lambaExprNo = () -> {
                btnSend.setBackgroundColor(Color.RED);
            };

            if (switchSynchronisation.isChecked()) {
                Dialog.showInterractDialog(myView.getContext(), getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation_yes), getResources().getString(R.string.text_confirmation_no), lambaExprSyncYes, lambaExprNo);
            } else {
                Dialog.showInterractDialog(myView.getContext(), getResources().getString(R.string.text_missing_sync), getResources().getString(R.string.text_missing_sync), getResources().getString(R.string.text_confirmation_yes), getResources().getString(R.string.text_confirmation_no), lambaExprNoSyncYes,  lambaExprNo);
            }
        });

        btnDownload.setOnClickListener(view -> {
            startDownload();

            bundle.putString("Name", String.valueOf(editTextName.getText()));
            bundle.putString("Surname", String.valueOf(editTextSurname.getText()));
            bundle.putString("Phone", String.valueOf(editTextPhoneNumber.getText()));
            bundle.putString("Email", String.valueOf(editTextEmail.getText()));

            bundle.putInt("DayPosition", spinnerDay.getSelectedItemPosition());
            bundle.putInt("MonthPosition", spinnerMonth.getSelectedItemPosition());
            bundle.putInt("YearPosition", spinnerYear.getSelectedItemPosition());
            bundle.putString("Day", String.valueOf(spinnerDay.getSelectedItem()));
            bundle.putString("Month", String.valueOf(spinnerMonth.getSelectedItem()));
            bundle.putString("Year", String.valueOf(spinnerYear.getSelectedItem()));

            bundle.putBoolean("Sport", checkBoxSport.isChecked());
            bundle.putBoolean("Music", checkBoxMusique.isChecked());
            bundle.putBoolean("Reading", checkBoxLecture.isChecked());
            bundle.putBoolean("Synchronise", switchSynchronisation.isChecked());


        });

        return myView;
    }

    private void startDownload() {
        FirebaseJsonDownloader firebaseJsonDownloader = new FirebaseJsonDownloader("https://rgrpo-af967-default-rtdb.firebaseio.com/", "users");
        firebaseJsonDownloader.downloadJsonFile();
    }
    private void startDownloadService() {
        // Démarrer le service DownloadAndParseService
        // Notez que vous devrez ajuster l'Intent pour correspondre à votre configuration
        //Intent intent = new Intent(getActivity(), DownloadAndParseService.class);
        //getActivity().startService(intent);
    }

    private void fillFormFromJSON(View myView) {
        // Récupération des vues
        editTextName = myView.findViewById(R.id.editTextName);
        editTextSurname = myView.findViewById(R.id.editTextSurname);
        editTextPhoneNumber = myView.findViewById(R.id.editTextPhoneNumber);
        editTextEmail = myView.findViewById(R.id.editTextEmail);
        spinnerDay = myView.findViewById(R.id.spinnerDay);
        spinnerMonth = myView.findViewById(R.id.spinnerMonth);
        spinnerYear = myView.findViewById(R.id.spinnerYear);
        checkBoxSport = myView.findViewById(R.id.checkBoxSport);
        checkBoxMusique = myView.findViewById(R.id.checkBoxMusique);
        checkBoxLecture = myView.findViewById(R.id.checkBoxLecture);
        switchSynchronisation = myView.findViewById(R.id.switchSynchronisation);
        try{
            JSONObject jsonObject = JsonReader.loadJSON(myView.getContext());

            editTextName.setText((CharSequence) jsonObject.get("Name"));
            editTextSurname.setText((CharSequence) jsonObject.get("Surname"));
            editTextPhoneNumber.setText("");
            editTextEmail.setText("");
            spinnerDay.setSelection((Integer) jsonObject.get("DayPosition"));
            spinnerMonth.setSelection((Integer) jsonObject.get("MonthPosition"));
            spinnerYear.setSelection((Integer) jsonObject.get("YearPosition"));
            checkBoxSport.setChecked(false);
            checkBoxMusique.setChecked(false);
            checkBoxLecture.setChecked(false);
            switchSynchronisation.setChecked(false);
            System.out.println(spinnerMonth.getSelectedItem());

            JsonReader.deleteJSONFile(myView.getContext());
        } catch (FileNotFoundException | JSONException e){
            System.out.println(e);
            System.out.println("Fichier introuvable");
            editTextName.setText("");
            editTextSurname.setText("");
            editTextPhoneNumber.setText("");
            editTextEmail.setText("");
            spinnerDay.setSelection(0);
            spinnerMonth.setSelection(0);
            spinnerYear.setSelection(0);
            checkBoxSport.setChecked(false);
            checkBoxMusique.setChecked(false);
            checkBoxLecture.setChecked(false);
            switchSynchronisation.setChecked(false);
        }
    }

    private void setupSpinners(View myView, Spinner spinnerDay, Spinner spinnerMonth, Spinner spinnerYear) {
        // Configuration du Spinner pour le jour (1-31)
        List<String> dayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add(String.valueOf(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(myView.getContext(), android.R.layout.simple_spinner_item, dayList);
        spinnerDay.setAdapter(dayAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(myView.getContext(), R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        // Configuration du Spinner pour l'année (1900 - année actuelle)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> yearList = new ArrayList<>();
        for (int i = 1900; i <= currentYear; i++) {
            yearList.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(myView.getContext(), android.R.layout.simple_spinner_item, yearList);
        spinnerYear.setAdapter(yearAdapter);
    }
}