package edu.fje.dam.mqtt_graph.Activities;


import android.content.res.Resources;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.franmontiel.fullscreendialog.FullScreenDialogContent;
import com.franmontiel.fullscreendialog.FullScreenDialogController;

import edu.fje.dam.mqtt_graph.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModalFragment extends Fragment implements FullScreenDialogContent {

    View v;
    private FullScreenDialogController dialogController;

    public ModalFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_modal, container, false);

        return v;
    }


    @Override
    public void onDialogCreated(FullScreenDialogController dialogController) {

        this.dialogController = dialogController;





    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Spinner spinner = (Spinner) getView().findViewById(R.id.chart_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext()
                , android.R.layout.simple_spinner_item,  new String[]{"Gaugage", "Linear", "Circle", "Toggle Button"});

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onConfirmClick(FullScreenDialogController dialogController) {
        return false;
    }

    @Override
    public boolean onDiscardClick(FullScreenDialogController dialogController) {
        return false;
    }
}
